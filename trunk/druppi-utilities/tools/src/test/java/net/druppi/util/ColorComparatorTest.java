/*
 * ColorComparatorTest.java
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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

/**
 * The ColorComparatorTest test class.
 *
 * @author Olivier Sechet
 * @version 1.0 - Jul 1, 2009
 */
public class ColorComparatorTest {

    /**
     * Test method for {@link net.druppi.util.ColorComparator#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        ColorComparator instance1 = ColorComparator.getInstance();
        ColorComparator instance2 = ColorComparator.getInstance();

        assertSame(instance1, instance2);
    }

    /**
     * Test method for {@link net.druppi.util.ColorComparator#compare(java.awt.Color, java.awt.Color)}.
     */
    @Test
    public void testCompare() {
        Color black = Color.BLACK;
        Color black2 = new Color(0, 0, 0);
        Color white = Color.WHITE;
        Color darkGray = Color.DARK_GRAY;
        Color gray = Color.GRAY;
        Color lightGray = Color.LIGHT_GRAY;

        ColorComparator comparator = ColorComparator.getInstance();
        assertTrue(comparator.compare(black, black) == 0);
        assertTrue(comparator.compare(black, black2) == 0);
        assertTrue(comparator.compare(black, white) != 0);
        assertTrue(comparator.compare(black, darkGray) != 0);
        assertTrue(comparator.compare(black, gray) != 0);
        assertTrue(comparator.compare(black, lightGray) != 0);

        assertTrue(comparator.compare(black, white) < 0);
        assertTrue(comparator.compare(white, black) > 0);

        assertTrue(comparator.compare(black, darkGray) < 0);
        assertTrue(comparator.compare(darkGray, gray) < 0);
        assertTrue(comparator.compare(gray, lightGray) < 0);
    }

}
