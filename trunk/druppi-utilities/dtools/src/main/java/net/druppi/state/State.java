/*
 * State.java
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
 * A state representation. This interface defines the methods that are called when a state
 * changes.
 *
 * @author Olivier Sechet
 * @version 1.0 - Feb 24, 2009
 */
public interface State {

    /**
     * Returns the id of the state. The id is used to identify the state in the state
     * machine.
     *
     * @return the id of the state.
     */
    Object getId();

    /**
     * Called when the state is entered.
     */
    void enter();

    /**
     * Called when the state is exited.
     */
    void exit();

    /**
     * Called when the state is paused.
     */
    void pause();

    /**
     * Called when the state is resumed.
     */
    void resume();
}
