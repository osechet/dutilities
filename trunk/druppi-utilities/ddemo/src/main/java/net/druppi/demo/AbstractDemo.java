/*
 * AbstractDemo.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A default implementation of a Demo panel. It automatically loads the source code from a
 * given resource name.
 *
 * @author Olivier Sechet
 * @version 1.1 - Nov 26, 2009
 */
public abstract class AbstractDemo implements Demo {

    /** The demo's name. */
    private final String name;

    /** The path to the source code file. */
    private final String resourceName;

    /** The demo's source code. */
    private String source;

    /**
     * Creates a new AbstractDemo. The resource name is automatically build from the class
     * name.
     *
     * @param name the demo's name.
     */
    public AbstractDemo(final String name) {
        this.name = name;
        this.resourceName = "/" + getClass().getName().replace('.', '/'); //$NON-NLS-1$
    }

    /**
     * Creates a new AbstractDemo.
     *
     * @param name the demo's name.
     * @param resourceName the path to the resource that contains the source code of the
     *        demo.
     */
    public AbstractDemo(final String name, final String resourceName) {
        this.name = name;
        this.resourceName = resourceName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSource() {
        if (source == null) {
            loadSource(resourceName);
        }
        return source;
    }

    /**
     * Loads the content of the file whose path is <code>resourceName</code>.
     *
     * @param resourceName the path (in the classpath) to the source code file.
     */
    private void loadSource(final String resourceName) {
        URL url = getClass().getResource(resourceName + ".java"); //$NON-NLS-1$
        if (url != null) {
            try {
                StringBuilder buffer = new StringBuilder();
                InputStream in = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                while (line != null) {
                    buffer.append(line);
                    buffer.append("\n"); //$NON-NLS-1$
                    line = reader.readLine();
                }
                source = buffer.toString();
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        } else {
            source = DemoApplication.getApplication().getContext().getResourceMap(
                    AbstractDemo.class).getString("AbstractDemo.noSourceCode"); //$NON-NLS-1$
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractDemo)) {
            return false;
        }
        AbstractDemo that = (AbstractDemo) obj;
        return this.name.equals(that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getName();
    }
}
