/*
 * FloatUtil.java
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

/**
 *
 * @author Olivier Sechet
 * @version 1.0 - Feb 4, 2009
 */
public final class FloatUtil {

    /** The default deviation. */
    public static final double EPSILON = 1e-6;

    /**
     * Creates a new FloatUtil. The constructor is private because the class is a utility
     * class.
     */
    private FloatUtil() {
        // no op
    }

    /**
     * Tests if a is equal to b, allowing deviation epsilon.
     *
     * @param a a double.
     * @param b a double.
     * @param epsilon the allowed deviation.
     * @return <code>true</code> if a is equal to b.
     */
    public static boolean eq(final double a, final double b, final double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }

    /**
     * Tests if a is equal to b, allowing deviation EPSILON.
     *
     * @param a a double.
     * @param b a double.
     * @return <code>true</code> if a is equal to b.
     */
    public static boolean eq(final double a, final double b) {
        return eq(a, b, EPSILON);
    }

    /**
     * Tests if a is greater than b, allowing deviation epsilon.
     *
     * @param a a double.
     * @param b a double.
     * @param epsilon the allowed deviation.
     * @return <code>true</code> if a is greater than b.
     */
    public static boolean gt(final double a, final double b, final double epsilon) {
        return a - b > epsilon;
    }

    /**
     * Tests if a is greater than b, allowing deviation EPSILON.
     *
     * @param a a double.
     * @param b a double.
     * @return <code>true</code> if a is greater than b.
     */
    public static boolean gt(final double a, final double b) {
        return gt(a, b, EPSILON);
    }

    /**
     * Tests if a is greater or equal to b, allowing deviation epsilon.
     *
     * @param a a double.
     * @param b a double.
     * @param epsilon the allowed deviation.
     * @return <code>true</code> if a is greater or equal to b.
     */
    public static boolean gte(final double a, final double b, final double epsilon) {
        return !lt(a, b, epsilon);
    }

    /**
     * Tests if a is greater or equal to b, allowing deviation EPSILON.
     *
     * @param a a double.
     * @param b a double.
     * @return <code>true</code> if a is greater or equal to b.
     */
    public static boolean gte(final double a, final double b) {
        return gte(a, b, EPSILON);
    }

    /**
     * Tests if a is lesser than b, allowing deviation epsilon.
     *
     * @param a a double.
     * @param b a double.
     * @param epsilon the allowed deviation.
     * @return <code>true</code> if a is lesser than b.
     */
    public static boolean lt(final double a, final double b, final double epsilon) {
        return b - a > epsilon;
    }

    /**
     * Tests if a is lesser than b, allowing deviation EPSILON.
     *
     * @param a a double.
     * @param b a double.
     * @return <code>true</code> if a is lesser than b.
     */
    public static boolean lt(final double a, final double b) {
        return lt(a, b, EPSILON);
    }

    /**
     * Tests if a is lesser or equal to b, allowing deviation epsilon.
     *
     * @param a a double.
     * @param b a double.
     * @param epsilon the allowed deviation.
     * @return <code>true</code> if a is lesser or equal to b.
     */
    public static boolean lte(final double a, final double b, final double epsilon) {
        return !gt(a, b, epsilon);
    }

    /**
     * Tests if a is lesser or equal to b, allowing deviation EPSILON.
     *
     * @param a a double.
     * @param b a double.
     * @return <code>true</code> if a is lesser or equal to b.
     */
    public static boolean lte(final double a, final double b) {
        return lte(a, b, EPSILON);
    }

}
