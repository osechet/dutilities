/*
 * LineUtil.java
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

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Utility functions to manipulate lines.
 *
 * @author Olivier Sechet
 * @version 1.0 - Feb 6, 2009
 */
public final class LineUtil {

    /**
     * Creates a new LineUtil.
     */
    private LineUtil() {
        // no op
    }

    /**
     * Returns the intersection point of the two given lines.
     *
     * @param l1 the first line.
     * @param l2 the second line.
     * @return the intersection point or null if the lines does not intersect.
     */
    public static Point2D getLineLineIntersection(final Line2D l1, final Line2D l2) {
        Point2D intersection = null;
        if (l1.intersectsLine(l2)) {
            double x1 = l1.getX1();
            double y1 = l1.getY1();
            double x2 = l1.getX2();
            double y2 = l1.getY2();
            double x3 = l2.getX1();
            double y3 = l2.getY1();
            double x4 = l2.getX2();
            double y4 = l2.getY2();

            double x = det(det(x1, y1, x2, y2), x1 - x2, det(x3, y3, x4, y4), x3 - x4)
                    / det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
            double y = det(det(x1, y1, x2, y2), y1 - y2, det(x3, y3, x4, y4), y3 - y4)
                    / det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);

            intersection = new Point2D.Double(x, y);
        }
        return intersection;
    }

    /**
     * Returns the determinant of the given vectors.
     *
     * @param x1 the x-coordinate of the first vector.
     * @param y1 the y-coordinate of the first vector.
     * @param x2 the x-coordinate of the second vector.
     * @param y2 the y-coordinate of the second vector.
     * @return the determinant.
     */
    private static double det(final double x1, final double y1, final double x2,
            final double y2) {
        return x1 * y2 - y1 * x2;
    }
}
