/*
 * DemoApplication.java
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
package net.druppi.demo;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 *
 * @author Olivier Sechet
 * @version 1.1 - Nov 26, 2009
 */
public class DemoApplication extends SingleFrameApplication {

    /** The application's demo manager. */
    private final DemoManager demoManager = new DemoManager();

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new DemoView(this));
        LoadDemosTask task = new LoadDemosTask();
        getContext().getTaskService().execute(task);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
        // no op
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DemoApplication
     */
    public static DemoApplication getApplication() {
        return Application.getInstance(DemoApplication.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(DemoApplication.class, args);
    }

    /**
     * Returns the application demo manager.
     *
     * @return the application demo manager.
     */
    public DemoManager getDemoManager() {
        return demoManager;
    }
}
