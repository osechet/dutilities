/*
 * ClassPathUtil.java
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
package net.druppi.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Olivier Sechet
 * @version 1.0 - Mar 5, 2009
 */
public final class ClassPathUtil {

    /** The class LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ClassPathUtil.class.getName());

    /**
     * Creates a new ClassPathUtil.
     */
    private ClassPathUtil() {
        // no op
    }

    /**
     * Adds the specified URL to the classpath.
     *
     * @param url the url to add.
     */
    public static void addToClassPath(final URL url) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class); //$NON-NLS-1$
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), url);
        } catch (SecurityException ex) {
            // Should not happen
            LOGGER.log(Level.WARNING, "Cannot access the URLClassLoader.addURL method.", ex); //$NON-NLS-1$
        } catch (NoSuchMethodException ex) {
            // Should not happen
            LOGGER.log(Level.WARNING, "Cannot access the URLClassLoader.addURL method.", ex); //$NON-NLS-1$
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, "An error occured when calling the URLClassLoader.addURL method.", ex); //$NON-NLS-1$
        } catch (IllegalAccessException ex) {
            LOGGER.log(Level.WARNING, "An error occured when calling the URLClassLoader.addURL method.", ex); //$NON-NLS-1$
        } catch (InvocationTargetException ex) {
            LOGGER.log(Level.WARNING, "An error occured when calling the URLClassLoader.addURL method.", ex); //$NON-NLS-1$
        }
    }
}
