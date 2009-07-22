/*
 * ResourceMapTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 3, 2009
 */
public class ResourceMapTest {

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception {
        Logger.getLogger(ResourceMap.class.getName()).setLevel(Level.OFF);
    }

    /**
     * Test method for {@link net.druppi.util.ResourceMap#ResourceMap(java.lang.String)}.
     */
    @Test
    public void testResourceMap() {
        try {
            new ResourceMap(null);
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (NullPointerException ex) {
            // no op: normal situation
        }

        try {
            new ResourceMap("not_exist"); //$NON-NLS-1$
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (MissingResourceException ex) {
            // no op: normal situation
        }

        // Load an existing bundle
        new ResourceMap("i18n/net/druppi/util/ResourceMapTest"); //$NON-NLS-1$
    }

    /**
     * Test method for {@link net.druppi.util.ResourceMap#getString(java.lang.String)}.
     */
    @Test
    public void testGetStringString() {
        ResourceMap resourceMap = new ResourceMap("i18n/net/druppi/util/ResourceMapTest"); //$NON-NLS-1$

        String key = "NOTHING"; //$NON-NLS-1$
        String msg = resourceMap.getString(key);
        assertEquals("!" + key + "!", msg); //$NON-NLS-1$ //$NON-NLS-2$

        key = "HELLO"; //$NON-NLS-1$
        msg = resourceMap.getString(key);
        assertEquals("Hello", msg); //$NON-NLS-1$
    }

    /**
     * Test method for {@link net.druppi.util.ResourceMap#getString(java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testGetStringStringObjectArray() {
        ResourceMap resourceMap = new ResourceMap("i18n/net/druppi/util/ResourceMapTest"); //$NON-NLS-1$

        String key = "NOTHING_ARG"; //$NON-NLS-1$
        String msg = resourceMap.getString(key);
        assertEquals("!" + key + "!", msg); //$NON-NLS-1$ //$NON-NLS-2$

        key = "HELLO_ARG"; //$NON-NLS-1$
        msg = resourceMap.getString(key, "user"); //$NON-NLS-1$
        assertEquals("Hello user", msg); //$NON-NLS-1$
    }

    /**
     * Test method for {@link net.druppi.util.ResourceMap#getMnemonic(java.lang.String)}.
     */
    @Test
    public void testGetMnemonic() {
        ResourceMap resourceMap = new ResourceMap("i18n/net/druppi/util/ResourceMapTest"); //$NON-NLS-1$

        char mnemonic = resourceMap.getMnemonic("NOTHING"); //$NON-NLS-1$
        assertEquals('!', mnemonic);

        mnemonic = resourceMap.getMnemonic("MNEMO"); //$NON-NLS-1$
        assertEquals('A', mnemonic);
    }
}
