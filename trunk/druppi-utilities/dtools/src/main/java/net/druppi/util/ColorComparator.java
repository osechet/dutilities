/*
 * ColorComparator.java
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

import java.awt.Color;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * A singleton class that can be used to compare two <code>java.awt.Color</code>.
 *
 * @author Olivier Sechet
 * @version 1.0 - Jul 1, 2009
 */
public final class ColorComparator implements Comparator<Color> {

    /**
     * Creates a new ColorComparator.
     */
    private ColorComparator() {
        // no op
    }

    /**
     * Returns the ColorComparator's instance.
     *
     * @return the singleton instance.
     */
    public static ColorComparator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * The singleton holder.
     *
     * @author Olivier Sechet
     * @version 1.0 - Jul 1, 2009
     */
    private static final class SingletonHolder {
        /** The singleton instance. */
        private static final ColorComparator INSTANCE = new ColorComparator();

        /** Creates a new SingletonHolder. */
        private SingletonHolder() {
            // no op
        }
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, or a
     * positive integer as the first argument is less than, equal to, or greater than the
     * second.
     * <p>
     * The comparison is first made on the red component, then on the green component, on
     * the blue component and finally on the alpha component.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is
     *         less than, equal to, or greater than the second.
     */
    @Override
    public int compare(final Color o1, final Color o2) {
        return new CompareToBuilder().append(o1.getRed(), o2.getRed()).append(
                o1.getGreen(), o2.getGreen()).append(o1.getBlue(), o2.getBlue()).append(
                o1.getAlpha(), o2.getAlpha()).toComparison();
    }

}
