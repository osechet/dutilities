/*
 * DemoManager.java
 *
 * Copyright (C) 2009 Olivier Sechet
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.druppi.demo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.druppi.util.ClassPathUtil;

/**
 * The demo manager lists existing demos. The {@link #loadDemos()} method loads all the
 * demos that are in the <code>demos</code> directory. The demos can be stored in a Jar
 * file or in a sub-directory. In each case, a <code>demo.properties</code> file must be
 * present. The valid properties are:
 * <ul>
 * <li>demosList - The list of defined demos, separated by coma.
 * <li>&lt;DemoName%gt;.demoClass - defines the class that implements the Demo interface.
 * </ul>
 * Example:
 *
 * <pre>
 * demosList=Example1Demo,Example2Demo
 * Example1Demo.demoClass=net.druppi.demo.Example1Demo
 * Example2Demo.demoClass=net.druppi.demo.Example2Demo
 * </pre>
 *
 * @author Olivier Sechet
 * @version 1.1 - Nov 26, 2009
 */
public class DemoManager {

    /** The name of the properties file of a demo. */
    private static final String DEMO_PROPERTIES_FILE = "demo.properties"; //$NON-NLS-1$

    /** The name of the property that defines the list of demos. */
    private static final String DEMO_LIST_PROPERTY = "demosList"; //$NON-NLS-1$

    /** The name of the property that defines the class to load. */
    private static final String DEMO_CLASS_PROPERTY = "demoClass"; //$NON-NLS-1$

    /** The class logger. */
    private static Logger LOGGER = Logger.getLogger(DemoManager.class.getName());

    /**
     * The name of the directory that contains all the demos. This directory must be in
     * the classpath.
     */
    private String demosDirName = "demos"; //$NON-NLS-1$

    /** The list used to store the registered demos. */
    private DemoList demoList;

    /**
     * Creates a new DemoManager.
     */
    public DemoManager() {
        String demosDirName = System.getProperty("demos.directory"); //$NON-NLS-1$
        if (demosDirName != null) {
            this.demosDirName = demosDirName;
        }

        // Add the demos containers to the classpath
        File[] files = getDemoContainers();

        for (File file : files) {
            try {
                ClassPathUtil.addToClassPath(file.toURI().toURL());
                LOGGER.log(Level.INFO, file.getAbsolutePath() + " added to classpath"); //$NON-NLS-1$
            } catch (final MalformedURLException ex) {
                LOGGER.log(Level.WARNING, "Skip the demo container " + file.getName()  //$NON-NLS-1$
                        + ": It cannot be added to the classpath.", ex); //$NON-NLS-1$
            }
        }
    }

    /**
     * Sets the demo list.
     *
     * @param demoList the new demo list.
     */
    public void setDemoList(final DemoList demoList) {
        DemoList oldList = this.demoList;
        this.demoList = demoList;

        if (oldList != null) {
            // Transfer the registered demos to the new list.
            Demo[] demos = oldList.getDemos();
            this.demoList.add(demos);
        }
    }

    /**
     * Loads the existing demos. The demos must be in the demos directory.
     */
    public void loadDemos() {
        File[] files = getDemoContainers();

        for (File file : files) {
            if (file.isDirectory()) {
                try {
                    loadDemo(file, new FileInputStream(new File(file,
                            DEMO_PROPERTIES_FILE)));
                } catch (final FileNotFoundException ex) {
                    LOGGER.log(Level.INFO, "Skip the directory " + file.getName() //$NON-NLS-1$
                            + ": it does not contain a demo.properties."); //$NON-NLS-1$
                }
            } else {
                // Jar file
                try {
                    JarInputStream jar = new JarInputStream(new FileInputStream(file));
                    JarEntry entry = jar.getNextJarEntry();
                    while (entry != null) {
                        if (entry.getName().equals(DEMO_PROPERTIES_FILE)) {
                            loadDemo(file, jar);
                        }
                        entry = jar.getNextJarEntry();
                    }
                } catch (final IOException ex) {
                    LOGGER.log(Level.INFO, "Skip jar file " + file.getName() //$NON-NLS-1$
                            + ": an error occured when reading the file.", ex); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * @param container the file containing the demo (a directory or a jar file).
     * @param in an input stream on the demo.properties file.
     */
    private void loadDemo(final File container, final InputStream in) {
        Properties props = new Properties();
        try {
            props.load(in);

            String demosList = props.getProperty(DEMO_LIST_PROPERTY);
            if (demosList != null) {
                StringTokenizer st = new StringTokenizer(demosList, ","); //$NON-NLS-1$
                while (st.hasMoreTokens()) {
                    String demoName = st.nextToken().trim();
                    String demoClassName = props.getProperty(demoName + "." + DEMO_CLASS_PROPERTY); //$NON-NLS-1$
                    Class<?> demoClass = Class.forName(demoClassName);
                    Object demo = demoClass.newInstance();
                    register((Demo) demo);
                }
            } else {
                LOGGER.log(Level.WARNING, "Skip the demo " + container.getName() //$NON-NLS-1$
                        + ": the properties file does not define the list of demos."); //$NON-NLS-1$
            }
        } catch (final IOException ex) {
            LOGGER.log(Level.WARNING, "Skip the demo " + container.getName() //$NON-NLS-1$
                    + ": the properties file cannot be read.", ex); //$NON-NLS-1$
        } catch (final ClassNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Skip the demo " + container.getName() //$NON-NLS-1$
                    + ": the demo class cannot be loaded.", ex); //$NON-NLS-1$
        } catch (final InstantiationException ex) {
            LOGGER.log(Level.WARNING, "Skip the demo " + container.getName() //$NON-NLS-1$
                    + ": the demo class cannot be instanciated.", ex); //$NON-NLS-1$
        } catch (final IllegalAccessException ex) {
            LOGGER.log(Level.WARNING, "Skip the demo " + container.getName() //$NON-NLS-1$
                    + ": the demo class cannot be instanciated.", ex); //$NON-NLS-1$
        } catch (final ClassCastException ex) {
            LOGGER.log(Level.WARNING, "Skip the demo " + container.getName() //$NON-NLS-1$
                    + ": the demo class does not implement the Demo interface.", ex); //$NON-NLS-1$
        }
    }

    /**
     * Registers a new demo.
     *
     * @param demo the new demo.
     */
    public void register(final Demo demo) {
        if (demoList == null) {
            throw new IllegalStateException("No DemoList has been defined."); //$NON-NLS-1$
        }
        if (!demoList.contains(demo)) {
            demoList.add(demo);
        }
    }

    /**
     * Returns the list of available demo containers.
     *
     * @return the list of available demo containers.
     */
    private File[] getDemoContainers() {
        File demosDir = new File(demosDirName);
        if (!demosDir.exists()) {
            LOGGER.log(Level.SEVERE,
                    "The demos directory does not exist. No demo will be loaded."); //$NON-NLS-1$
            return new File[0];
        }
        return demosDir.listFiles(new DemoContainerFileFilter());
    }

    /**
     * Describes a demo container file. It can be a directory or a jar file.
     *
     * @author Olivier Sechet
     * @version 1.0 - Nov 26, 2009
     */
    private static final class DemoContainerFileFilter implements FileFilter {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean accept(final File pathname) {
            return pathname.isDirectory() || pathname.getName().endsWith(".jar"); //$NON-NLS-1$
        }
    }
}
