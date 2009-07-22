/*
 * ColorUtil.java
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

/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 21, 2009
 */
public final class ColorUtil {

    /**
     * Creates a new ColorUtil. The constructor is private because the class is a utility
     * class.
     */
    private ColorUtil() {
        // no op
    }

    /**
     * Returns the Color represented by the given formatted string. The legal format
     * for color are: "#RRGGBB", "#AARRGGBB", "R, G, B", "R, G, B, A"
     *
     * From org.jdesktop.application.ResourceMap.ColorStringConverter TODO: add a notice
     * about the copy of this function
     *
     * @param str the string to decode.
     * @return a color.
     */
    public static Color decode(final String str) {
        Color color = null;
        if (str.startsWith("#")) { //$NON-NLS-1$
            switch (str.length()) {
            // RGB/hex color
            case 7:
                color = Color.decode(str);
                break;
            // ARGB/hex color
            case 9:
                int alpha = Integer.decode(str.substring(0, 3));
                int rgb = Integer.decode("#" + str.substring(3)); //$NON-NLS-1$
                color = new Color(alpha << 24 | rgb, true);
                break;
            default:
                throw new InvalidResourceException("Invalid # color format: " + str); //$NON-NLS-1$
            }
        } else {
            String[] parts = str.split(","); //$NON-NLS-1$
            if (parts.length < 3 || parts.length > 4) {
                throw new InvalidResourceException("Invalid R, G, B, A color format.: " //$NON-NLS-1$
                        + str);
            }
            try {
                // with alpha component
                if (parts.length == 4) {
                    int r = Integer.parseInt(parts[0].trim());
                    int g = Integer.parseInt(parts[1].trim());
                    int b = Integer.parseInt(parts[2].trim());
                    int a = Integer.parseInt(parts[3].trim());
                    color = new Color(r, g, b, a);
                } else {
                    int r = Integer.parseInt(parts[0].trim());
                    int g = Integer.parseInt(parts[1].trim());
                    int b = Integer.parseInt(parts[2].trim());
                    color = new Color(r, g, b);
                }
            } catch (NumberFormatException e) {
                throw new InvalidResourceException(
                        "Invalid integer in R, G, B, A color format: " + str); //$NON-NLS-1$
            }
        }
        return color;
    }

}
