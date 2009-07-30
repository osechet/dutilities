/*
 * ResourceMap.java
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
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Olivier Sechet
 * @version 1.0 - Mar 27, 2009
 */
public final class ResourceMap {

    /** The class LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ResourceMap.class.getName());

    /** The path to the associated bundle file. */
    private final ResourceBundle bundle;

    /** The ClassLoader used to load resources. */
    private final ClassLoader loader;

    /**
     * Creates a new ResourceMap.
     *
     * @param bundleName the full path to the bundle.
     */
    public ResourceMap(final String bundleName) {
        this(bundleName, null);
    }

    /**
     * Creates a new ResourceMap. The bundles will be loaded with the given ClassLoader.
     *
     * @param bundleName the full path to the bundle.
     * @param loader the class loader to use to load bundles.
     */
    public ResourceMap(final String bundleName, final ClassLoader loader) {
        if (loader == null) {
            this.loader = getClass().getClassLoader();
        } else {
            this.loader = loader;
        }
        this.bundle = ResourceBundle.getBundle(bundleName, Locale.getDefault(), this.loader);
    }

    /**
     * Returns the string associated with the given key.
     *
     * @param key the message's key.
     * @return the message.
     */
    public String getString(final String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException ex) {
            LOGGER.info("Key not found: " + key); //$NON-NLS-1$
            return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Returns the string associated with the given key and replace the parameters with
     * the given arguments. This method uses the String.format() method.
     *
     * @param key the message's key.
     * @param args message's arguments.
     * @return the message.
     * @see String#format(String, Object...)
     */
    public String getString(final String key, final Object... args) {
        return String.format(getString(key), args);
    }

    /**
     * Returns the integer value associated with the given key.
     *
     * @param key the message's key.
     * @return an integer.
     */
    public int getInteger(final String key) {
        return Integer.parseInt(getString(key));
    }

    /**
     * Returns the long value associated with the given key.
     *
     * @param key the message's key.
     * @return a long.
     */
    public long getLong(final String key) {
        return Long.parseLong(getString(key));
    }

    /**
     * Returns the float value associated with the given key.
     *
     * @param key the message's key.
     * @return a float.
     */
    public float getFloat(final String key) {
        return Float.parseFloat(getString(key));
    }

    /**
     * Returns the double value associated with the given key.
     *
     * @param key the message's key.
     * @return a double.
     */
    public double getDouble(final String key) {
        return Double.parseDouble(getString(key));
    }

    /**
     * Returns the mnemonic character associated with the given key. The bundle's message
     * must be a single character string.
     *
     * @param key the mnemonic's key.
     * @return the mnemonic character.
     */
    public char getMnemonic(final String key) {
        return getString(key).charAt(0);
    }

    /**
     * Returns a font according to the description given in the bundle. The method uses
     * Font.decode() to parse the bundle.
     *
     * @param key the font's key.
     * @return a Font.
     * @see Font#decode(String)
     */
    public Font getFont(final String key) {
        return Font.decode(getString(key));
    }

    /**
     * Return the icon whose path is specified with the key.
     *
     * @param key the icon's key.
     * @return an icon.
     */
    public Icon getIcon(final String key) {
        URL url = loader.getResource(getString(key));
        if (url == null) {
            throw new InvalidResourceException(
                    "Impossible to load the specified icon: " + key); //$NON-NLS-1$
        }
        return new ImageIcon(url);
    }

    /**
     * Return the image whose path is specified with the key.
     *
     * @param key the image's key.
     * @return an icon.
     */
    public Image getImage(final String key) {
        return ((ImageIcon) getIcon(key)).getImage();
    }

    /**
     * Returns a Color according to the description given in the bundle. The legal format
     * for color resources are: "#RRGGBB", "#AARRGGBB", "R, G, B", "R, G, B, A"
     *
     * From org.jdesktop.application.ResourceMap.ColorStringConverter TODO: add a notice
     * about the copy of this function
     *
     * @param key the color's key.
     * @return a Color
     * @throws InvalidResourceException if the String does not respect the legal format.
     */
    public Color getColor(final String key) throws InvalidResourceException {
        String s = getString(key);

        return ColorUtil.decode(s);
    }
}
