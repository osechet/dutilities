/*
 * PluginApp.java
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
package net.druppi.pluginapp;

import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.ActionMap;

import net.druppi.util.Preferences;

import org.platonos.pluginengine.PluginEngine;
import org.platonos.pluginengine.PluginEngineException;
import org.platonos.pluginengine.logging.ILogger;
import org.platonos.pluginengine.logging.LoggerLevel;

/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 20, 2009
 */
public abstract class PluginApp {

    /** The class logger. */
    private static final Logger LOGGER = Logger.getLogger(PluginApp.class.getName());

    /** The application instance. */
    private static PluginApp app;

    /** The plugin engine. */
    private PluginEngine pluginEngine;

    /** The application's action map. */
    private ActionMap actionMap;

    /** The application's preferences. */
    private Preferences preferences;

    /**
     * Returns the application instance.
     *
     * @return the application instance.
     */
    public static PluginApp getApplication() {
        return app;
    }

    /**
     * Launch the application.
     *
     * @param appClass the application class (the class that extends the PluginApp class).
     * @param args the program's arguments.
     */
    public static final void launch(final Class<? extends PluginApp> appClass,
            final String[] args) {
        try {
            URL url = appClass.getResource("/logging.properties"); //$NON-NLS-1$
            LogManager.getLogManager().readConfiguration(url.openStream());

            app = appClass.newInstance();
            app.start(args);
        } catch (SecurityException ex) {
            throw new RuntimeException("The logging system initialization failed.", ex); //$NON-NLS-1$
        } catch (IOException ex) {
            throw new RuntimeException("The logging system initialization failed.", ex); //$NON-NLS-1$
        } catch (InstantiationException ex) {
            throw new RuntimeException("Impossible to start the application.", ex); //$NON-NLS-1$
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Impossible to start the application.", ex); //$NON-NLS-1$
        }
    }

    /**
     * Starts the application.
     *
     * @param args the program's arguments.
     */
    private void start(final String[] args) {
        // Define a default logger for the plugin engine
        final Logger logger = Logger.getLogger(PluginEngine.class.getName());
        ILogger engineLogger = new ILogger() {
            public void log(final LoggerLevel level, final String message,
                    final Throwable thr) {
                if (level == LoggerLevel.SEVERE) {
                    logger.log(Level.SEVERE, message, thr);
                } else if (level == LoggerLevel.WARNING) {
                    logger.log(Level.WARNING, message, thr);
                } else if (level == LoggerLevel.INFO) {
                    logger.log(Level.INFO, message); // Ignore throwable.
                } else if (level == LoggerLevel.FINE) {
                    logger.log(Level.FINE, message); // Ignore throwable.
                }
            }
        };

        try {
            preferences = new Preferences();
            preferences.load("preferences.properties");
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Impossible to load preferences.", ex); //$NON-NLS-1$
        }

        // Initialize the action map. It must be done before starting the plugin engine.
        actionMap = new ActionMap();

        // Initialize the plugin engine and load the available plugins.
        pluginEngine = new PluginEngine(getName(), engineLogger);
        String dirList = System.getProperty("plugins.directories"); //$NON-NLS-1$
        if (dirList != null) {
            StringTokenizer st = new StringTokenizer(dirList, ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String path = st.nextToken();
                try {
                    pluginEngine.loadPlugin(path);
                } catch (PluginEngineException ex) {
                    LOGGER.log(Level.WARNING, "Impossible to load the plugin in " + path, ex); //$NON-NLS-1$
                }
            }
        } else {
            pluginEngine.loadPlugins("plugins"); //$NON-NLS-1$
        }
        pluginEngine.start();
    }

    /**
     * Terminates the currently running Game. The argument serves as a status code; by
     * convention, a nonzero status code indicates abnormal termination.
     *
     * @param status exit status.
     */
    public static final void exit(final int status) {
        try {
            app.preferences.save("preferences.properties");
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Impossible to save preferences.", ex); //$NON-NLS-1$
        }
        app.pluginEngine.shutdown();
        System.exit(status);
    }

    /**
     * Returns the application's name.
     *
     * @return the application's name.
     */
    public abstract String getName();

    /**
     * Returns the application's action map. The action map contains all the actions
     * associated with the application.
     *
     * @return the application's action map.
     */
    public final ActionMap getActionMap() {
        return actionMap;
    }

    /**
     * Returns the application preferences.
     *
     * @return the application preferences.
     */
    public final Preferences getPreferences() {
        return preferences;
    }
}
