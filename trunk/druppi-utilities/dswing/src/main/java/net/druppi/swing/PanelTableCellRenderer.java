/*
 * PluginCellRenderer.java
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

import java.awt.Component;
import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author Olivier Sechet
 * @version 1.0 - Mar 3, 2009
 */
@SuppressWarnings("serial")
public class PanelTableCellRenderer<T> extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private T plugin;
    private RendererPanel<T> panel;

    /**
     * Creates a new PanelTableCellRenderer.
     *
     * @param panel
     */
    public PanelTableCellRenderer(RendererPanel<T> panel) {
        this.panel = panel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        try {
            plugin = (T) value;

            panel.setObject(plugin);
            panel.setSelected(isSelected);
            panel.setHasFocus(hasFocus);

            final int height = isSelected ? 150 : 55;

            if (table.getRowHeight(row) != height) {
                table.setRowHeight(row, height);
            }
            panel.setPreferredSize(new Dimension(50, height));
            return panel;
        } catch (ClassCastException ex) {
            return new JLabel(value.toString());
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return this.getTableCellRendererComponent(table, value, isSelected, isSelected, row, column);
    }

     @Override
    public Object getCellEditorValue() {
        return plugin;
    }

    @Override
    public boolean isCellEditable(EventObject evt) {
        return true;
    }

}