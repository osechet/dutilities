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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The demo manager lists existing demos. The {@link #loadDemos()} method loads all the
 * demos that are in the <code>demos</code> directory. The demos can be stored in a Jar
 * file or in a sub-directory. In each case, a <code>demo.properties</code> file must be
 * present. The valid properties are:
 * <ul>
 * <li>demoClass - defines the class that implements the Demo interface.
 * </ul>
 * Example:
 * <pre>
 * demoClass = net.druppi.demo.ExampleDemo
 * </pre>
 *
 * @author Olivier Sechet
 * @version 1.0 - Sep 3, 2009
 */
public class DemoManager {

    /**
     * The name of the directory that contains all the demos. This directory must be in
     * the classpath.
     */
    private static final String DEMOS_DIR = "demos"; //$NON-NLS-1$

    /** The name of the properties file of a demo. */
    private static final String DEMO_PROPERTIES_FILE = "demo.properties"; //$NON-NLS-1$

    /** The name of the property that defines the class to load. */
    private static final String DEMO_CLASS_PROPERTY = "demoClass"; //$NON-NLS-1$

    /** The class logger. */
    private static Logger LOGGER = Logger.getLogger(DemoManager.class.getName());

    /** The list of registered demos. */
    private List<Demo> demos = new ArrayList<Demo>();

    /**
     * Loads the existing demos. The demos must be in the demos directory.
     */
    public void loadDemos() {
        File demosDir = new File(DEMOS_DIR);
        if (!demosDir.exists()) {
            LOGGER.log(Level.SEVERE,
                    "The demos directory does not exist. No demo will be loaded."); //$NON-NLS-1$
            return;
        }
        File[] files = demosDir.listFiles(new FileFilter() {

            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory() || pathname.getName().endsWith(".jar"); //$NON-NLS-1$
            }
        });

        for (File file : files) {
            if (file.isDirectory()) {
                try {
                    loadDemo(file, new FileInputStream(new File(file,
                            DEMO_PROPERTIES_FILE)));
                } catch (final FileNotFoundException ex) {
                    LOGGER
                            .log(
                                    Level.INFO,
                                    "Skip the directory " + file.getName() + ": it does not contain a demo.properties."); //$NON-NLS-1$ //$NON-NLS-2$
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
                    }
                } catch (final IOException ex) {
                    LOGGER
                            .log(
                                    Level.INFO,
                                    "Skip jar file " + file.getName() + ": an error occured when reading the file.", ex); //$NON-NLS-1$ //$NON-NLS-2$
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

            String demoClassName = props.getProperty(DEMO_CLASS_PROPERTY);
            Class<?> demoClass = Class.forName(demoClassName);
            Object demo = demoClass.newInstance();
            register((Demo) demo);
        } catch (final IOException ex) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Skip the demo " + container.getName() + ": the properties file cannot be read.", ex); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (final ClassNotFoundException ex) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Skip the demo " + container.getName() + ": the demo class cannot be loaded.", ex); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (final InstantiationException ex) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Skip the demo " + container.getName() + ": the demo class cannot be instanciated.", ex); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (final IllegalAccessException ex) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Skip the demo " + container.getName() + ": the demo class cannot be instanciated.", ex); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (final ClassCastException ex) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Skip the demo " + container.getName() + ": the demo class does not implement the Demo interface.", ex); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Registers a new demo.
     *
     * @param demo the new demo.
     */
    public void register(final Demo demo) {
        demos.add(demo);
    }

    /**
     * Returns the registered demos.
     *
     * @return the registered demos.
     */
    public Demo[] getDemos() {
        return demos.toArray(new Demo[demos.size()]);
    }

    /**
     * Returns the number of registered demos.
     *
     * @return the number of registered demos.
     */
    public int getDemosCount() {
        return demos.size();
    }

    /**
     * Returns the demo at the specified index.
     *
     * @param index the index of the demo.
     * @return the demo.
     */
    public Demo getDemo(int index) {
        return demos.get(index);
    }
}
