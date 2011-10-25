/*
 * ResourceManager.java
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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Olivier Sechet
 * @version 1.0 - Mar 27, 2009
 */
public final class ResourceManager {

    /** The class logger. */
    private static final Logger LOGGER = Logger.getLogger(ResourceManager.class.getName());

    /** The default bundle path. */
    private static final String DEFAULT_BUNDLE_PATH = "i18n/"; //$NON-NLS-1$

    /** The map of ResourceMap. */
    private static Map<String, ResourceMap> map = new HashMap<String, ResourceMap>();

    /**
     * This class is not instanciable.
     */
    private ResourceManager() {
        // no op
    }

    /**
     * Returns the ResourceMap for the specified bundle name. The method throws a
     * MissingResourceException if the bundle cannot be found and an
     * IllegalArgumentException if the bundle name is null.
     *
     * @param bundleName the bundle name (cannot be null).
     * @return the specified ResourceMap.
     */
    public static ResourceMap getResourceMap(final String bundleName) {
        return getResourceMap(bundleName, null);
    }

    /**
     * Returns the ResourceMap for the specified bundle name. The method throws a
     * MissingResourceException if the bundle cannot be found and an
     * IllegalArgumentException if the bundle name is null.
     *
     * @param bundleName the bundle name (cannot be null).
     * @param loader the class loader to use to load bundles.
     * @return the specified ResourceMap.
     */
    public static ResourceMap getResourceMap(final String bundleName, final ClassLoader loader) {
        if (bundleName == null) {
            throw new IllegalArgumentException("The bundle name cannot be null."); //$NON-NLS-1$
        }
        ResourceMap resourceMap = map.get(bundleName);
        if (resourceMap == null) {
            resourceMap = new ResourceMap(bundleName, loader);
            map.put(bundleName, resourceMap);
            LOGGER.finer("New ResourceMap created from " + bundleName); //$NON-NLS-1$
        }
        return resourceMap;
    }

    /**
     * Returns the ResourceMap for the specified class. The class name is used to get a
     * bundle name with the following rule: <code>
     * bundleName = DEFAULT_BUNDLE_PATH + cls.getSimpleName()
     * </code> The method throws a
     * MissingResourceException if the bundle cannot be found and an
     * IllegalArgumentException if the bundle name is null.
     *
     * @param cls the class (cannot be null).
     * @return the specified ResourceMap.
     */
    public static ResourceMap getResourceMap(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("The class cannot be null."); //$NON-NLS-1$
        }
        return getResourceMap(DEFAULT_BUNDLE_PATH + cls.getName().replaceAll("\\.", "/"), cls.getClassLoader()); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
