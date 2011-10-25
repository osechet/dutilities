/*
 * Demo.java
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
package net.druppi.demo;

import javax.swing.JPanel;

/**
 * Defines a Demo panel.
 *
 * @author Olivier Sechet
 * @version 1.0 - Sep 3, 2009
 */
public interface Demo {

    /**
     * Returns the demo's name. The name of a demo is used to easily recognize it.
     *
     * @return the demo's name.
     */
    String getName();

    /**
     * Returns the demo's panel. The panel of a demo is where the demo shows its
     * capabilities.
     *
     * @return the demo's panel
     */
    JPanel getPanel();

    /**
     * Returns the demo's source. The source of a demo is the code used to create the
     * demo.
     *
     * @return the demo's source.
     */
    String getSource();
}
