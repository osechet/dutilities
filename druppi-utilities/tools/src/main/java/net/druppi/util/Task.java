/*
 * Task.java
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

import javax.swing.SwingWorker;

/**
 * An improved SwingWorker that can be used to process tasks. It provides a message in
 * order to give information about the progression of the task.
 *
 * @param <T> the type used for carrying out intermediate results by this SwingWorker's publish and process methods
 *
 * @author Olivier Sechet
 * @version 1.0 - Jul 20, 2009
 */
public abstract class Task<T> extends SwingWorker<Object, T> {

    /** The property name used when a new message is set. */
    public static final String PROPERTY_MESSAGE = "message"; //$NON-NLS-1$

    /** The current message. */
    private volatile String message;

    /**
     * Sets a new description message. A PropertyChange event is fired in order to notify
     * the listeners about the change.
     *
     * @param message the new message.
     */
    protected final void setMessage(final String message) {
        if (this.message.equals(message)) {
            return;
        }
        String oldMessage;
        synchronized (this) {
            oldMessage = this.message;
            this.message = message;
            if (!getPropertyChangeSupport().hasListeners(PROPERTY_MESSAGE)) {
                return;
            }
        }
        firePropertyChange(PROPERTY_MESSAGE, oldMessage, this.message);
    }
}
