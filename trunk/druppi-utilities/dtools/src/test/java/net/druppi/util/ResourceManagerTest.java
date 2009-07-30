/*
 * ResourceManagerTest.java
 *
 * Copyright (C) 2009 Olivier Sechet
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.druppi.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.MissingResourceException;

import org.junit.Test;


/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 3, 2009
 */
public class ResourceManagerTest {

    /**
     * Test method for {@link net.druppi.util.ResourceManager#getResourceMap(java.lang.String)}.
     */
    @Test
    public void testGetResourceMapString() {
        try {
            ResourceManager.getResourceMap((String) null);
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (IllegalArgumentException ex) {
            // no op: normal situation
        }

        try {
            ResourceManager.getResourceMap("does_not_exist"); //$NON-NLS-1$
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (MissingResourceException ex) {
            // no op: normal situation
        }

        // Load an existing bundle
        ResourceMap resourceMap = ResourceManager.getResourceMap("i18n/net/druppi/util/ResourceManagerTest"); //$NON-NLS-1$
        assertNotNull(resourceMap);
    }

    /**
     * Test method for {@link net.druppi.util.ResourceManager#getResourceMap(java.lang.Class)}.
     */
    @Test
    public void testGetResourceMapClassOfQ() {
        try {
            ResourceManager.getResourceMap((Class<?>) null);
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (IllegalArgumentException ex) {
            // no op: normal situation
        }

        try {
            ResourceManager.getResourceMap(String.class);
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (MissingResourceException ex) {
            // no op: normal situation
        }

        // Load an existing bundle
        ResourceMap resourceMap = ResourceManager.getResourceMap(getClass());
        assertNotNull(resourceMap);
    }
}
