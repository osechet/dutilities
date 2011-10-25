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

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 21, 2009
 */
public class Preferences {

    /** The class logger. */
    private static final Logger LOGGER = Logger.getLogger(Preferences.class.getName());

    /** The preferences storage. */
    private Properties prefs = new Properties();

    /** The timer */
    private Timer timer;

    /** The task called to periodically save. */
    private TimerTask saveTask;

    private String prefFilePath;

    /**
     * @param path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void load(final String path) throws FileNotFoundException, IOException {
        prefs.load(new FileInputStream(path));
    }

    /**
     * @param path
     */
    public void save(final String path) throws IOException {
        prefs.store(new FileOutputStream(path), ""); //$NON-NLS-1$
    }

    /**
     * @param path
     * @param period time in milliseconds between successive save.
     */
    public void save(final String path, final long period) throws IOException {
        // Cancel any running task
        if (saveTask != null) {
            saveTask.cancel();
        }

        // Call the store method a first time to ensure no error happens during its execution.
        prefs.store(new FileOutputStream(path), ""); //$NON-NLS-1$

        // Save the path of the file used to store the preferences
        this.prefFilePath = path;

        if (timer == null) {
            timer = new Timer("Preferences"); //$NON-NLS-1$
            saveTask = new TimerTask() {

                @Override
                public void run() {
                    try {
                        LOGGER.log(Level.FINE, "Autoaving preferences."); //$NON-NLS-1$
                        prefs.store(new FileOutputStream(prefFilePath), ""); //$NON-NLS-1$
                    } catch (final IOException ex) {
                        LOGGER.log(Level.SEVERE, "Autosave failed.", ex); //$NON-NLS-1$
                        cancel();
                    }
                }
            };
        }
        timer.schedule(saveTask, period, period);
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
     * @param value
     */
    public void put(final String key, final int value) {
        put(key, Integer.toString(value));
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

    /**
     * @param key
     * @return
     */
    public Rectangle getRectangle(final String key) {
        try {
            int x = getInteger(key + ".x"); //$NON-NLS-1$
            int y = getInteger(key + ".y"); //$NON-NLS-1$
            int width = getInteger(key + ".width"); //$NON-NLS-1$
            int height = getInteger(key + ".height"); //$NON-NLS-1$
            return new Rectangle(x, y, width, height);
        } catch (final NumberFormatException ex) {
            return null;
        }
    }

    /**
     * @param key
     * @param rect
     */
    public void put(final String key, final Rectangle rect) {
        put(key + ".x", rect.x); //$NON-NLS-1$
        put(key + ".y", rect.y); //$NON-NLS-1$
        put(key + ".width", rect.width); //$NON-NLS-1$
        put(key + ".height", rect.height); //$NON-NLS-1$
    }
}
