/*
 * FontComparatorTest.java
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

import java.awt.Font;

import org.junit.Test;

/**
 * The FontComparator test class.
 *
 * @author Olivier Sechet
 * @version 1.0 - Jul 1, 2009
 */
public class FontComparatorTest {

    /**
     * Test method for {@link net.druppi.util.FontComparator#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        FontComparator instance1 = FontComparator.getInstance();
        FontComparator instance2 = FontComparator.getInstance();

        assertSame(instance1, instance2);
    }

    /**
     * Test method for {@link net.druppi.util.FontComparator#compare(java.awt.Font, java.awt.Font)}.
     */
    @Test
    public void testCompare() {
        Font arial = new Font("Arial", Font.PLAIN, 10); //$NON-NLS-1$
        Font arial2 = new Font("Arial", Font.PLAIN, 10); //$NON-NLS-1$
        Font lucida = new Font("Lucida", Font.PLAIN, 10); //$NON-NLS-1$
        Font arialBold = new Font("Arial", Font.BOLD, 10); //$NON-NLS-1$
        Font arialBig = new Font("Arial", Font.PLAIN, 20); //$NON-NLS-1$

        FontComparator comparator = FontComparator.getInstance();
        assertTrue(comparator.compare(arial, arial) == 0);
        assertTrue(comparator.compare(arial, arial2) == 0);
        assertTrue(comparator.compare(arial, lucida) != 0);
        assertTrue(comparator.compare(arial, arialBold) != 0);
        assertTrue(comparator.compare(arial, arialBig) != 0);

        assertTrue(comparator.compare(arial, lucida) < 0);
        assertTrue(comparator.compare(lucida, arial) > 0);
    }

}
