/*
 * StateMachine.java
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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Olivier Sechet
 * @version 1.0 - Feb 24, 2009
 */
public class StateMachine {

    /** The class logger. */
    private static Logger logger = Logger.getLogger(StateMachine.class.getName());

    /** The active states stack. */
    private Stack<State> states = new Stack<State>();

    /** The registered states. */
    private Map<Object, State> registeredStates = new HashMap<Object, State>();

    /** The first state's id. */
    private Object startId;

    /**
     * Starts the state machine with the starting state.
     */
    public void start() {
        logger.log(Level.FINER, "Starting the state machine"); //$NON-NLS-1$
        if (startId == null) {
            throw new IllegalStateException("No starting state has been defined."); //$NON-NLS-1$
        }
        changeState(startId);
    }

    /**
     * Switch to the specified state.
     *
     * @param stateId the if of the new state.
     */
    public void changeState(final Object stateId) {
        logger.log(Level.FINER, "change state " + stateId); //$NON-NLS-1$

        // First exits all the active states.
        while (!states.empty()) {
            states.pop().exit();
        }

        // store and init the new state
        State state = registeredStates.get(stateId);
        if (state != null) {
            states.push(state);
            state.enter();
        } else {
            logger.log(Level.SEVERE, "The expected screen does not exist: " + stateId); //$NON-NLS-1$
        }
    }

    /**
     * Push the given state onto the stack. All the stacked states are paused and the new
     * state is activated.
     *
     * @param stateId the id of the new state.
     */
    public void pushState(final Object stateId) {
        logger.log(Level.FINER, "push state " + stateId); //$NON-NLS-1$

        // pause current state
        if (!states.empty()) {
            states.peek().pause();
        }

        // store and init the new state
        State state = registeredStates.get(stateId);
        states.push(state);
        state.enter();
    }

    /**
     * Pop the current state. The previous "peek" state is resumed.
     */
    public void popState() {
        logger.log(Level.FINER, "pop state"); //$NON-NLS-1$

        // cleanup the current state
        if (!states.empty()) {
            states.pop().exit();
        }

        // resume previous state
        if (!states.empty()) {
            states.peek().resume();
        }
    }

    /**
     * Registers the specified state with the given id.
     *
     * @param state the state.
     */
    public void register(final State state) {
        State oldState = registeredStates.get(state.getId());
        if (oldState != null) {
            throw new IllegalArgumentException("A state with the same id already exists: " + state.getId()); //$NON-NLS-1$
        }
        logger.log(Level.FINER, "Registering state " + state.getId()); //$NON-NLS-1$
        registeredStates.put(state.getId(), state);
    }

    /**
     * Returns the state identified by id.
     *
     * @param stateId the state's id.
     * @return the state identified by id or null if the given id does not exist.
     */
    public State getState(final Object stateId) {
        return registeredStates.get(stateId);
    }

    /**
     * Defines the starting state. The starting state is the one used when the start
     * method is called.
     *
     * @param stateId the state's id.
     */
    public void setStartingState(final Object stateId) {
        logger.log(Level.FINER, "Defining starting state: " + stateId); //$NON-NLS-1$
        this.startId = stateId;
    }
}
