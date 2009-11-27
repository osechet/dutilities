/*
 * LoadDemosTask.java
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

import org.jdesktop.application.Task;

/**
 * @author Olivier Sechet
 * @version 1.0 - Nov 27, 2009
 */
public class LoadDemosTask extends Task<Object, Object> {

    /**
     * Creates a new LoadDemosTask.
     */
    public LoadDemosTask() {
        super(DemoApplication.getApplication());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object doInBackground() throws Exception {
        message("startMessage");  //$NON-NLS-1$
        DemoApplication.getApplication().getDemoManager().loadDemos();
        message("finishedMessage");  //$NON-NLS-1$
        return null;
    }
}
