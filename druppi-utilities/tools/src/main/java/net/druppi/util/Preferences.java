/*
 * Preferences.java
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 21, 2009
 */
public class Preferences {

    /** The preferences storage. */
    private Properties prefs = new Properties();

    /**
     * @param path
     */
    public void load(final String path) throws IOException {
        prefs.load(new FileInputStream(path));
    }

    /**
     * @param path
     */
    public void save(final String path) throws IOException {
        prefs.store(new FileOutputStream(path), ""); //$NON-NLS-1$
    }

    /**
     * @param key
     * @return
     */
    public String get(final String key) {
        return prefs.getProperty(key);
    }

    /**
     * @param key
     * @param value
     */
    public void put(final String key, final String value) {
        prefs.put(key, value);
    }

    /**
     * @param key
     * @return
     */
    public String getString(final String key) {
        return get(key);
    }

    /**
     * @param key
     * @return
     */
    public int getInteger(final String key) {
        return Integer.parseInt(get(key));
    }

    /**
     * @param key
     * @return
     */
    public long getLong(final String key) {
        return Long.parseLong(get(key));
    }

    /**
     * @param key
     * @return
     */
    public float getFloat(final String key) {
        return Float.parseFloat(get(key));
    }

    /**
     * @param key
     * @return
     */
    public double getDouble(final String key) {
        return Double.parseDouble(get(key));
    }

    /**
     * @param key
     * @return
     */
    public boolean getBoolean(final String key) {
        return Boolean.parseBoolean(get(key));
    }
}
