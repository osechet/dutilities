/*
 * StateView.java
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


/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 24, 2009
 */
public interface StateView {

    /**
     * Called when the view is activated, that is to say when the associated state is
     * entered or resumed.
     */
    void activate();

    /**
     * Called when the view is deactivated, that is to say when the associated state is
     * exited or paused.
     */
    void deactivate();
}
