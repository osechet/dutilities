/*
 * Document.java
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;

import net.druppi.swing.borders.ShadowBorder;


/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 25, 2009
 */
@SuppressWarnings("serial")
public class Document extends JComponent {

    /** The name of the pinned property. */
    public static final String PROP_PINNED = "pinned"; //$NON-NLS-1$

    /** The pinned state. <code>true</code> if the document is pinned to the desk. */
    private boolean pinned;

    /**
     * Creates a new Document.
     */
    public Document() {
        setBorder(new ShadowBorder(new Insets(5, 5, 5, 5)));
        setBackground(Color.WHITE);
    }

    /**
     * Returns the pinned state. The state is <code>true</code> if the document is pinned.
     * If a document is pinned, it cannot move.
     *
     * @return the pinned state.
     */
    public boolean isPinned() {
        return pinned;
    }

    /**
     * Sets the pinned state.
     *
     * @param pinned <code>true</code> if the document has to be pinned.
     * @see Document#isPinned()
     */
    public void setPinned(boolean pinned) {
        boolean old = this.pinned;
        this.pinned = pinned;
        firePropertyChange(PROP_PINNED, old, this.pinned);
    }

    /*
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Insets insets = getInsets();
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        g.setColor(getBackground());
        g.fillRect(insets.left, insets.top, width, height);
    }
}
