/*
 * MessageBox.java
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
package net.druppi.swing;

import java.awt.Component;
import java.awt.HeadlessException;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * @author Olivier Sechet
 * @version 1.0 - Mar 6, 2009
 */
@SuppressWarnings("serial")
public class MessageBox extends JOptionPane {

    private static final int DEFAULT_MAX = 150;

    private int maxCharactersPerLineCount;

    /**
     * Creates a new MessageBox.
     *
     * @param maxCharactersPerLineCount
     */
    public MessageBox(int maxCharactersPerLineCount) {
        this.maxCharactersPerLineCount = maxCharactersPerLineCount;
    }

    /*
     * @see javax.swing.JOptionPane#getMaxCharactersPerLineCount()
     */
    @Override
    public int getMaxCharactersPerLineCount() {
        return maxCharactersPerLineCount;
    }

    /**
     * Brings up an information-message dialog titled "Message".
     *
     * @param parentComponent determines the <code>Frame</code> in which the dialog is
     *        displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     *        no <code>Frame</code>, a default <code>Frame</code> is used
     * @param message the <code>Object</code> to display
     * @exception HeadlessException if <code>GraphicsEnvironment.isHeadless</code> returns
     *            <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static void showMessageDialog(Component parentComponent, Object message)
            throws HeadlessException {
        Locale l = (parentComponent == null) ? Locale.getDefault()
                : parentComponent.getLocale();
        showMessageDialog(parentComponent, message, UIManager.getString(
                "OptionPane.messageDialogTitle", l), INFORMATION_MESSAGE, null); //$NON-NLS-1$
    }

    /**
     * Brings up a dialog that displays a message using a default icon determined by the
     * <code>messageType</code> parameter.
     *
     * @param parentComponent determines the <code>Frame</code> in which the dialog is
     *        displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     *        no <code>Frame</code>, a default <code>Frame</code> is used
     * @param message the <code>Object</code> to display
     * @param title the title string for the dialog
     * @param messageType the type of message to be displayed: <code>ERROR_MESSAGE</code>,
     *        <code>INFORMATION_MESSAGE</code>, <code>WARNING_MESSAGE</code>,
     *        <code>QUESTION_MESSAGE</code>, or <code>PLAIN_MESSAGE</code>
     * @exception HeadlessException if <code>GraphicsEnvironment.isHeadless</code> returns
     *            <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static void showMessageDialog(Component parentComponent, Object message,
            String title, int messageType) throws HeadlessException {
        showMessageDialog(parentComponent, message, title, INFORMATION_MESSAGE, null);
    }

    /**
     * Brings up a dialog displaying a message, specifying all parameters.
     *
     * @param parentComponent determines the <code>Frame</code> in which the dialog is
     *        displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     *        no <code>Frame</code>, a default <code>Frame</code> is used
     * @param message the <code>Object</code> to display
     * @param title the title string for the dialog
     * @param messageType the type of message to be displayed: <code>ERROR_MESSAGE</code>,
     *        <code>INFORMATION_MESSAGE</code>, <code>WARNING_MESSAGE</code>,
     *        <code>QUESTION_MESSAGE</code>, or <code>PLAIN_MESSAGE</code>
     * @param icon an icon to display in the dialog that helps the user identify the kind
     *        of message that is being displayed
     * @exception HeadlessException if <code>GraphicsEnvironment.isHeadless</code> returns
     *            <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static void showMessageDialog(Component parentComponent, Object message,
            String title, int messageType, Icon icon) throws HeadlessException {
        JOptionPane pane = new MessageBox(DEFAULT_MAX);
        pane.setMessage(message);
        pane.setMessageType(messageType);
        JDialog dialog = pane.createDialog(parentComponent, title);
        dialog.setVisible(true);
    }
}
