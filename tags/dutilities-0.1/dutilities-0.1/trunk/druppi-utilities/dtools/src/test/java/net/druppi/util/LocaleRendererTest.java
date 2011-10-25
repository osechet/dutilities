/*
 * LocaleRendererTest.java
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Olivier Sechet
 * @version 1.0 - Apr 22, 2009
 */
public class LocaleRendererTest {

    /** The tested instance. */
    private LocaleRenderer renderer;

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() throws Exception {
        renderer = new LocaleRenderer(Locale.FRANCE);
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#LocaleRenderer(Locale)}.
     */
    @Test
    public void testLocaleRendererNull() {
        try {
            renderer = new LocaleRenderer(null);
            fail("Should throw an exeception."); //$NON-NLS-1$
        } catch (IllegalArgumentException ex) {
            // This is the normal result
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#LocaleRenderer(Locale)}.
     */
    @Test
    public void testLocaleRenderer() {
        renderer = new LocaleRenderer(Locale.FRANCE);
        assertNotNull(renderer);
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#getLocale()}.
     */
    @Test
    public void getLocale() {
        assertNotNull(renderer.getLocale());
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#toString()}.
     */
    @Test
    public void testToString() {
        assertNotNull(renderer.toString());
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#equals(Object)}.
     */
    @Test
    public void testEquals() {
        LocaleRenderer renderer2 = new LocaleRenderer(Locale.FRANCE);
        assertTrue(renderer.equals(renderer2));
        assertTrue(renderer2.equals(renderer));

        LocaleRenderer renderer3 = new LocaleRenderer(Locale.US);
        assertFalse(renderer.equals(renderer3));
        assertFalse(renderer3.equals(renderer));
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#hashCode()}.
     */
    @Test
    public void testHashCode() {
        assertTrue(renderer.hashCode() == new LocaleRenderer(Locale.FRANCE).hashCode());
        assertTrue(renderer.hashCode() != new LocaleRenderer(Locale.US).hashCode());
    }

    /**
     * Test method for {@link net.druppi.util.LocaleRenderer#compareTo(net.druppi.util.LocaleRenderer)}.
     */
    @Test
    public void testCompareTo() {
        LocaleRenderer renderer2 = new LocaleRenderer(Locale.FRANCE);
        assertEquals(0, renderer.compareTo(renderer2));
        assertEquals(0, renderer2.compareTo(renderer));

        LocaleRenderer renderer3 = new LocaleRenderer(Locale.US);
        assertEquals(1, renderer.compareTo(renderer3));
        assertEquals(-1, renderer3.compareTo(renderer));
    }
}
