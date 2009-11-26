/*
 * DemoListModel.java
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

import javax.swing.AbstractListModel;

/**
 * @author Olivier Sechet
 * @version 1.0 - Sep 3, 2009
 */
@SuppressWarnings("serial")
public class DemoListModel extends AbstractListModel {

    /** The associated DemoManager. */
    private final DemoManager demoManager;

    /**
     * Creates a new DemoListModel.
     *
     * @param demoManager the associated DemoManager.
     */
    public DemoListModel(final DemoManager demoManager) {
        this.demoManager = demoManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getElementAt(final int index) {
        if (demoManager == null) {
            return null;
        }
        return demoManager.getDemo(index).getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        if (demoManager == null) {
            return 0;
        }
        return demoManager.getDemosCount();
    }

}
