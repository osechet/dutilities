/*
 * ViewState.java
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
package net.druppi.state;

import java.util.logging.Logger;


/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 24, 2009
 */
public abstract class ViewState implements State {

    /** The class logger. */
    private static Logger logger = Logger.getLogger(ViewState.class.getName());

    /**
     * Returns the associated view.
     *
     * @return the associated view.
     */
    public abstract StateView getView();

    /**
     * {@inheritDoc}
     */
    @Override
    public void enter() {
        logger.fine("Entering " + getId()); //$NON-NLS-1$
        getView().activate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        logger.fine("Exiting " + getId()); //$NON-NLS-1$
        getView().deactivate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        logger.fine("Pausing " + getId()); //$NON-NLS-1$
        getView().deactivate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        logger.fine("Resuming " + getId()); //$NON-NLS-1$
        getView().activate();
    }
}
