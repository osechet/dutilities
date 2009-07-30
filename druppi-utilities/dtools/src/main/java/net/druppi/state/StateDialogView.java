/*
 * StateDialogView.java
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

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;


/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 24, 2009
 */
@SuppressWarnings("serial")
public abstract class StateDialogView extends JDialog implements StateView {

    /** The event handler of this view. */
    private StateDialogViewListener listener;

    /**
     * Creates a new StateDialogView.
     *
     * @param frame the parent frame.
     * @param title the window's title.
     * @param l the event handler of this view.
     */
    public StateDialogView(final Frame frame, final String title, final StateDialogViewListener l) {
        super(frame, title);
        this.listener = l;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent evt) {
                listener.cancel();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivate() {
        setVisible(false);
    }

}
