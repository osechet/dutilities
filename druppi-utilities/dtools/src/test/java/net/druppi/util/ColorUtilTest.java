/*
 * ColorUtilTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

/**
 * @author "Olivier Sechet"
 * @version 1.0 - Jul 22, 2009
 */
public class ColorUtilTest {

    /**
     * Test method for {@link net.druppi.util.ColorUtil#decode(java.lang.String)}.
     */
    @Test
    public void testDecodeHexRGB() {
        String str = "#FFC8D3"; //$NON-NLS-1$
        assertEquals(new Color(255, 200, 211), ColorUtil.decode(str));
    }

    /**
     * Test method for {@link net.druppi.util.ColorUtil#decode(java.lang.String)}.
     */
    @Test
    public void testDecodeHexARGB() {
        String str = "#80FFC8D3"; //$NON-NLS-1$

        assertEquals(new Color(255, 200, 211, 128), ColorUtil.decode(str));
    }

    /**
     * Test method for {@link net.druppi.util.ColorUtil#decode(java.lang.String)}.
     */
    @Test
    public void testDecodeSeqRGB() {
        String str = "255, 200, 211"; //$NON-NLS-1$
        assertEquals(new Color(255, 200, 211), ColorUtil.decode(str));
    }

    /**
     * Test method for {@link net.druppi.util.ColorUtil#decode(java.lang.String)}.
     */
    @Test
    public void testDecodeSeqRGBA() {
        String str = "255, 200, 211, 128"; //$NON-NLS-1$
        assertEquals(new Color(255, 200, 211, 128), ColorUtil.decode(str));
    }

    /**
     * Test method for {@link net.druppi.util.ColorUtil#toARGBHex(java.awt.Color)}.
     */
    @Test
    public void testToARGBHex() {
        assertTrue("#80FFC8D3".equalsIgnoreCase(ColorUtil.toARGBHex(new Color(255, 200, 211, 128)))); //$NON-NLS-1$
    }

    /**
     * Test method for {@link net.druppi.util.ColorUtil#toARGBHex(java.awt.Color)}.
     */
    @Test
    public void testToRGBHex() {
        assertTrue("#FFC8D3".equalsIgnoreCase(ColorUtil.toRGBHex(new Color(255, 200, 211)))); //$NON-NLS-1$
    }
}
