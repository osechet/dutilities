/*
 * StateComponentView.java
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

import javax.swing.JComponent;

/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 25, 2009
 */
@SuppressWarnings("serial")
public class StateComponentView extends JComponent implements StateView {

    /** The view's parent. */
    private JComponent parent;

    /**
     * Creates a new StateComponentView.
     *
     * @param parent the view's parent.
     */
    public StateComponentView(final JComponent parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        parent.add(this);
        parent.revalidate();
        parent.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivate() {
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

}
