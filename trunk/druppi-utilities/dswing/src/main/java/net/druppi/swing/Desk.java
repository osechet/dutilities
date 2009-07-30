/*
 * Desk.java
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import net.druppi.util.ResourceManager;

/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 25, 2009
 */
@SuppressWarnings("serial")
public class Desk extends JComponent {

    /** The background image. */
    private Image image;

    private Image pinIcon;

    /** The move handler for the added documents. */
    private DesktopMoveHandler moveHandler = new DesktopMoveHandler();

    /**
     * The property listener. It is used to update the desk when an document's property
     * changes.
     */
    private PropertyHandler propertyHandler = new PropertyHandler();

    /**
     * Creates a new Desk with a default background image.
     */
    public Desk() {
        this(ResourceManager.getResourceMap(Desk.class).getImage("Desk.image")); //$NON-NLS-1$
    }

    /**
     * Creates a new Desk.
     *
     * @param image the background image.
     */
    public Desk(Image image) {
        this.image = image;
        this.pinIcon = ResourceManager.getResourceMap(Desk.class).getImage("Desk.drawing-pin"); //$NON-NLS-1$
        setLayout(new DeskLayout());
    }

    /**
     * Adds a document to the desktop.
     *
     * @param doc the new document.
     */
    public void add(Document doc) {
        addImpl(doc, null, -1);

        doc.addMouseListener(moveHandler);
        doc.addMouseMotionListener(moveHandler);
        doc.addPropertyChangeListener(propertyHandler);
    }

    /*
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

    /*
     * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
     */
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof Document) {
                Document doc = (Document) component;
                if (doc.isPinned()) {
                    Insets insets = doc.getInsets();
                    int x = doc.getX() + doc.getWidth() / 2;
                    int y = doc.getY() + insets.top;
                    g.drawImage(pinIcon, x, y, this);
                }
            }
        }
    }

    /**
     * @author Olivier Sechet
     * @version 1.0 - Feb 25, 2009
     */
    private final class PropertyHandler implements PropertyChangeListener {

        /*
         * @see
         * java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent
         * )
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (Document.PROP_PINNED.equals(evt.getPropertyName())) {
                Document doc = (Document) evt.getSource();
                repaint(doc.getBounds());
            }
        }
    }

    /**
     * @author Olivier Sechet
     * @version 1.0 - Feb 25, 2009
     */
    private static final class DesktopMoveHandler extends MoveHandler {

        /*
         * @see net.amidari.swing.MoveHandler#canMove(javax.swing.JComponent)
         */
        @Override
        protected boolean canMove(JComponent component) {
            if (!(component instanceof Document)) {
                return false;
            }
            Document doc = (Document) component;
            return !doc.isPinned();
        }
    }

    /**
     * @author Olivier Sechet
     * @version 1.0 - Feb 5, 2009
     */
    private static final class DeskLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
            // no op
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            // no op
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            int width = 0;
            int height = 0;
            Component[] components = parent.getComponents();
            for (int i = 0; i < components.length; i++) {
                Component component = components[i];
                int right = component.getX() + component.getWidth();
                int bottom = component.getY() + component.getHeight();
                if (right > width) {
                    width = right;
                }
                if (bottom > height) {
                    height = bottom;
                }
            }
            return new Dimension(width, height);
        }

        @Override
        public void layoutContainer(Container parent) {
            // no op
        }
    }
}
