/*
 * NotImplementedException.java
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
package net.druppi.lang;

/**
 * An exception that can be used in not implemented methods.
 *
 * @author Olivier Sechet
 * @version 1.0 - Apr 1, 2009
 */
public class NotImplementedException extends RuntimeException {

    /** The serial version id. */
    private static final long serialVersionUID = 5968697178418132850L;

    /**
     * Creates a new NotImplementedException.
     */
    public NotImplementedException() {
        super();
    }

    /**
     * Creates a new NotImplementedException.
     *
     * @param message the detail message. The detail message is saved for later retrieval
     *        by the getMessage() method.
     */
    public NotImplementedException(final String message) {
        super(message);
    }

}
