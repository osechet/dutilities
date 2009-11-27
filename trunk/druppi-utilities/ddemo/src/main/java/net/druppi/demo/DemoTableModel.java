/*
 * DemoTableModel.java
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

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * A TableModel used to display the content of a DemoManager.
 *
 * @author Olivier Sechet
 * @version 1.0 - Nov 26, 2009
 */
@SuppressWarnings("serial")
class DemoTableModel extends DefaultTableModel implements DemoList {

    /**
     * Creates a new DemoTableModel.
     */
    public DemoTableModel() {
        super(new Object[] {"Demo"}, 0); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final Demo demo) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                addRow(new Object[] { demo });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Demo demo) {
        return dataVector.contains(demo);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(final Demo[] demos) {
        int first = getRowCount();
        Vector<Object> rowData = new Vector<Object>();
        for (Demo demo : demos) {
            rowData.clear();
            rowData.add(demo);
            dataVector.insertElementAt(rowData, getRowCount());
        }
        fireTableRowsInserted(first, getRowCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Demo[] getDemos() {
        Demo[] demos = new Demo[dataVector.size()];
        for (int i = 0; i < demos.length; i++) {
            Vector<?> rowData = (Vector<?>) dataVector.get(i);
            demos[i] = (Demo) rowData.get(0);
        }
        return demos;
    }
}
