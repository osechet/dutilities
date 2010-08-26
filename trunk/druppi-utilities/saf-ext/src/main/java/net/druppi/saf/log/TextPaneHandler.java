/*
 * TextPaneHandler.java
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
package net.druppi.saf.log;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * A log handler that writes into a JTextPane.
 *
 * @author Olivier Sechet
 * @version 1.0 - Aug 25, 2010
 */
final class TextPaneHandler extends Handler {

    /** The maximum number of characters in the console. */
    private static final int BUFFER_SIZE = 80000;

    /** The JTextPane. */
    private JTextPane textPane;

    /** A messages. */
    private final List<Message> messages = new ArrayList<Message>();

    /** The scrollLocked flag. */
    private volatile boolean scrollLocked = false;

    /** The foreground color associated to specific levels. */
    private final Map<Level, Color> colors = new HashMap<Level, Color>();

    /**
     * Creates a new TextPaneHandler.
     *
     * @param textPane the JTextPane used to write the logs.
     */
    public TextPaneHandler(final JTextPane textPane) {
        this.textPane = textPane;
        setFormatter(new SimpleFormatter());
        setLevel(Level.ALL);

        colors.put(Level.SEVERE, Color.RED);
        colors.put(Level.WARNING, Color.ORANGE);
        colors.put(Level.INFO, Color.BLUE);
        colors.put(Level.CONFIG, Color.GREEN);
        colors.put(Level.FINE, Color.GRAY);
        colors.put(Level.FINER, Color.GRAY);
        colors.put(Level.FINEST, Color.GRAY);
    }

    /**
     * Clears the JTextPane.
     */
    public void clear() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                textPane.setText(""); //$NON-NLS-1$
            }
        });
    }

    /**
     * Locks/unlocks scroll.
     *
     * @param locked <code>true</code> to lock.
     */
    public void setScrollLocked(final boolean locked) {
        this.scrollLocked = locked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        String msg;
        try {
            msg = getFormatter().format(record);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.FORMAT_FAILURE);
            return;
        }

        messages.add(new Message(msg, colors.get(record.getLevel()), Color.WHITE));
        flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                final Rectangle rect = textPane.getVisibleRect();
                final Document document = textPane.getDocument();
                for (Message message : messages) {
                    try {
                        SimpleAttributeSet att = new SimpleAttributeSet();

                        att.addAttribute(StyleConstants.Foreground, message.getForeground());
                        att.addAttribute(StyleConstants.Background, message.getBackground());

                        document.insertString(document.getLength(), message.getText(), att);
                    } catch (final BadLocationException ex) {
                        // ignore
                    }
                }
                messages.clear();

                if (document.getLength() > BUFFER_SIZE) {
                    final int size = document.getLength() - BUFFER_SIZE;
                    try {
                        document.remove(0, size);
                    } catch (final BadLocationException ex) {
                        // ignore
                    }
                }

                if (scrollLocked) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            textPane.scrollRectToVisible(rect);
                        }
                    });
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        flush();
    }

    /**
     * A message is a part of information that will be displayed in the JTextPane.
     *
     * @author Olivier Sechet
     * @version 1.0 - Aug 25, 2010
     */
    private static final class Message {

        /** The text of the message. */
        private String text;
        /** The foreground color of the message. */
        private Color foreground;
        /** The background color of the message. */
        private Color background;

        /**
         * Creates a new TextPaneHandler.Message.
         *
         * @param text the message's text.
         * @param foreground the foreground color.
         * @param background the background color.
         */
        public Message(final String text, final Color foreground, final Color background) {
            this.text = text;
            this.foreground = foreground;
            this.background = background;
        }

        /**
         * Returns the text of the message.
         *
         * @return the text of the message.
         */
        public String getText() {
            return text;
        }

        /**
         * Returns the foreground color.
         *
         * @return the foreground color.
         */
        public Color getForeground() {
            return foreground;
        }

        /**
         * Returns the background color.
         *
         * @return the background color.
         */
        public Color getBackground() {
            return background;
        }
    }
}
