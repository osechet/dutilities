/*
 * MoveHandler.java
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

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 10, 2009
 */
public class MoveHandler implements MouseInputListener {

    private Point origin = null;
    private boolean moving = false;

    /**
     * Returns <code>true</code> if the given component can move. By default, this method
     * always returns <code>true</code>.
     *
     * @param component the component to check.
     * @return <code>true</code> if the given component can move.
     */
    protected boolean canMove(JComponent component) {
        return true;
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        origin = evt.getPoint();
        moving = true;
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        moving = false;
        origin = null;
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        if (moving) {
            Point pt = evt.getPoint();
            pt.x -= origin.x;
            pt.y -= origin.y;

            JComponent component = (JComponent) evt.getSource();
            if (canMove(component)) {
                final JComponent parent = (JComponent) component.getParent();
                Insets insets = parent.getInsets();
                // Convert the point to the parent space.
                pt = SwingUtilities.convertPoint(component, pt, parent);
                if (pt.x < insets.left) {
                    pt.x = insets.left;
                }
                if (pt.y < insets.top) {
                    pt.y = insets.top;
                }
                component.setLocation(pt);

                if (pt.x > parent.getX() || pt.y > parent.getY()) {
                    parent.revalidate();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // no op
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        // no op
    }

    @Override
    public void mouseExited(MouseEvent evt) {
        // no op
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
        // no op
    }
}
