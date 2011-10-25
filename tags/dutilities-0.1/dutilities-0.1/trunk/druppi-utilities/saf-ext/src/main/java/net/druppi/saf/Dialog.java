/*
 * LoggerTreeTableModel.java
 *
 * Copyright (C) 2010 Olivier Sechet
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
package net.druppi.saf;

import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;

/**
 * @param <T> the type of the application.
 *
 * @author Olivier Sechet
 * @version 1.0 - Aug 13, 2010
 */
public class Dialog<T extends SingleFrameApplication> extends JDialog {

    /** The serial version id. */
    private static final long serialVersionUID = 3921233551196661112L;

    /** The application. */
    private final T application;

    /**
     * Creates a new Dialog.
     *
     * @param application the application.
     */
    public Dialog(final T application) {
        super(application.getMainFrame());
        this.application = application;
    }

    /**
     * Creates a new Dialog.
     *
     * @param application the application.
     * @param modal specifies whether dialog blocks user input to other top-level windows
     *            when shown. If true, the modality type property is set to
     *            <code>DEFAULT_MODALITY_TYPE</code>, otherwise the dialog is modeless.
     */
    public Dialog(final T application, final boolean modal) {
        super(application.getMainFrame(), modal);
        this.application = application;
    }

    /**
     * Returns the application.
     *
     * @return the application.
     */
    public T getApplication() {
        return application;
    }

    /**
     * Returns the resource map associated with the class.
     *
     * @return the dialog's resource map.
     */
    public ResourceMap getResourceMap() {
        return application.getContext().getResourceMap(getClass());
    }

    /**
     * Sets the default button.
     *
     * @param button the new default button.
     */
    protected void setDefaultButton(final JButton button) {
        getRootPane().setDefaultButton(button);
    }

    /**
     * Sets the cancel button.
     *
     * @param button the new cancel button.
     */
    protected void setCancelButton(final JButton button) {
        final String cancelActionKey = "CANCEL_ACTION_KEY"; //$NON-NLS-1$
        final KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        final InputMap inputMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(escapeKey, cancelActionKey);
        getRootPane().getActionMap().put(cancelActionKey, button.getAction());
    }
}
