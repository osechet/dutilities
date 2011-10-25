/*
 * DemoList.java
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

/**
 * Defines a container of Demo instance.
 *
 * @author Olivier Sechet
 * @version 1.0 - Nov 26, 2009
 */
public interface DemoList {

    /**
     * Adds a demo to the list.
     *
     * @param demo the new demo.
     */
    void add(final Demo demo);

    /**
     * Returns <code>true</code> if the list contains the specified Demo.
     *
     * @param demo the demo to look for.
     * @return <code>true</code> if the demo exists, <code>false</code> otherwise.
     */
    boolean contains(final Demo demo);

    /**
     * Returns the list of demos.
     *
     * @return the list of demos.
     */
    Demo[] getDemos();

    /**
     * Adds the specified demos to the list.
     *
     * @param demos the demos to add.
     */
    void add(final Demo[] demos);
}
