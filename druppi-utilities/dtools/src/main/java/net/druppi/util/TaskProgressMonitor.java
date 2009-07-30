/*
 * TaskProgressMonitor.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.SwingWorker.StateValue;

/**
 * This class monitors the progression of a task and can update a JProgressBar, a message
 * label and an animation label.
 *
 * @author Olivier Sechet
 * @version 1.0 - Feb 13, 2009
 */
public class TaskProgressMonitor implements PropertyChangeListener {

    /** The progress bar to update. */
    private final JProgressBar progressBar;
    /** The animation label to update. */
    private final JLabel animationLabel;
    /** The message label to update. */
    private final JLabel messageLabel;
    /** A timer used to keep the message visible during a given time. */
    private final Timer messageTimer;
    /** A timer used to change the animation's icons. */
    private final Timer busyIconTimer;
    /** The icon used when no task is working. */
    private final Icon idleIcon;
    /** The list of icons used to generate the animation. */
    private final Icon[] busyIcons;
    /** The index of the current icon of the animation. */
    private int busyIconIndex = 0;

    /**
     * Creates a new TaskProgressManager. The given ResourceMap must contains the
     * following properties:<br>
     * <ul>
     * <li>StatusBar.messageTimeout: the time before to clear the message
     * <li>StatusBar.busyAnimationRate: the pause between each image
     * <li>StatusBar.busyIconsCount: the number of images used for the animation
     * <li>StatusBar.busyIcons[i] (0 &lt;= i &lt; StatusBar.busyIconsCount): the images used for the animation
     * <li>StatusBar.idleIcon: the image used when idle
     * </ul>
     *
     * @param progressBar the progress bar to update or null.
     * @param animationLabel the animation label to update or null.
     * @param messageLabel the message label or null.
     * @param resourceMap the ResourceMap used to get properties, icons, messages...
     *        Cannot be <code>null</code>.
     */
    public TaskProgressMonitor(final JProgressBar progressBar, final JLabel animationLabel,
            final JLabel messageLabel, final ResourceMap resourceMap) {
        if (resourceMap == null) {
            throw new IllegalArgumentException("You must specify a ResourceMap."); //$NON-NLS-1$
        }
        this.progressBar = progressBar;
        this.animationLabel = animationLabel;
        this.messageLabel = messageLabel;

        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout"); //$NON-NLS-1$
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                TaskProgressMonitor.this.messageLabel.setText(""); //$NON-NLS-1$
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate"); //$NON-NLS-1$
        busyIcons = new Icon[resourceMap.getInteger("StatusBar.busyIconsCount")]; //$NON-NLS-1$
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                TaskProgressMonitor.this.animationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon"); //$NON-NLS-1$
        if (animationLabel != null) {
            animationLabel.setIcon(idleIcon);
        }
        if (progressBar != null) {
            progressBar.setVisible(false);
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt a PropertyChangeEvent object describing the event source and the
     *        property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("state".equals(propertyName)) { //$NON-NLS-1$
            if (StateValue.STARTED == evt.getNewValue()) {
                if (animationLabel != null) {
                    if (!busyIconTimer.isRunning()) {
                        animationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                }
                if (progressBar != null) {
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                }
            } else if (StateValue.DONE == evt.getNewValue()) {
                if (animationLabel != null) {
                    busyIconTimer.stop();
                    animationLabel.setIcon(idleIcon);
                }
                if (progressBar != null) {
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                }
            }
        } else if ("message".equals(propertyName)) { //$NON-NLS-1$
            if (messageLabel != null) {
                String text = (String) evt.getNewValue();
                messageLabel.setText((text == null) ? "" : text); //$NON-NLS-1$
                messageTimer.restart();
            }
        } else if ("progress".equals(propertyName)) { //$NON-NLS-1$
            if (progressBar != null) {
                int value = (Integer) (evt.getNewValue());
                progressBar.setVisible(true);
                progressBar.setIndeterminate(false);
                progressBar.setValue(value);
            }
        }
    }
}
