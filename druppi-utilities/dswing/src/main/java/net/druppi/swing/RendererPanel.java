/*
 * RendererPanel.java
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

import javax.swing.JPanel;


/**
 * @author Olivier Sechet
 * @version 1.0 - Mar 4, 2009
 */
@SuppressWarnings("serial")
public abstract class RendererPanel<T> extends JPanel {

    public abstract void setObject(T value);

    public abstract void setSelected(boolean selected);

    public abstract void setHasFocus(boolean hasFocus);

}
