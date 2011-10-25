/*
 * LoggerTreeTableNode.java
 *
 * Copyright (C) 2010 Olivier Sechet
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
package net.druppi.saf.log.tree;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * A node for the logger tree.
 *
 * @author Olivier Sechet
 * @version 1.0 - Aug 25, 2010
 */
public final class LoggerTreeTableNode extends AbstractMutableTreeTableNode {

    /** The resource map. */
    private static ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(LoggerTreeTableNode.class); // CS_IGNORE LineLength

    /** The column names. */
    public static final Column[] COLUMNS = {
        new Column(resourceMap.getString("Column.logger.name"), String.class, //$NON-NLS-1$
                resourceMap.getInteger("Column.logger.width")) { //$NON-NLS-1$

            @Override
            public Object value(final LoggerTreeTableNode node) {
                return node.getUserObject().getName();
            }
        },
        new Column(resourceMap.getString("Column.level.name"), String.class, //$NON-NLS-1$
                resourceMap.getInteger("Column.level.width"), true) { //$NON-NLS-1$

            @Override
            public Object value(final LoggerTreeTableNode node) {
                return node.getLevel();
            }
        },
    };

    /**
     * Creates a new LoggerTreeTableNode.
     *
     * @param logger the logger's details.
     */
    public LoggerTreeTableNode(final LoggerInfo logger) {
        setUserObject(logger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggerInfo getUserObject() {
        return (LoggerInfo) super.getUserObject();
    }

    /**
     * Returns the level for the specified logger. If the logger's level is
     * <code>null</code>, the parent is checked.
     *
     * @return the logger's level.
     */
    protected String getLevel() {
        final String level = getUserObject().getLevel();
        if (level == null && parent != null) {
            return ((LoggerTreeTableNode) parent).getLevel();
        }
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValueAt(final int column) {
        return COLUMNS[column].value(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    /**
     * Returns the child with the specified name.
     *
     * @param name the child's name.
     * @return the child or <code>null</code> if no child has this name.
     */
    public LoggerTreeTableNode getChild(final String name) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final LoggerTreeTableNode child = (LoggerTreeTableNode) getChildAt(i);
            if (child.getUserObject().getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEditable(final int column) {
        return COLUMNS[column].isEditable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(final Object aValue, final int column) {
        final LoggerInfo logger = getUserObject();
        logger.setLevel((String) aValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LoggerTreeTableNode)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        final LoggerTreeTableNode that = (LoggerTreeTableNode) obj;
        return this.getUserObject().equals(that.getUserObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getUserObject().hashCode();
    }

    /**
     * Describes a column.
     *
     * @author Olivier Sechet
     * @version 1.0 - Aug 12, 2010
     */
    public abstract static class Column {

        /** The column's name. */
        private final String name;

        /** The column's type. */
        private final Class<?> type;

        /** The preferred width. */
        private final int prefWidth;

        /** <code>true</code> if the column is editable. */
        private final boolean editable;

        /**
         * Creates a new Column.
         *
         * @param name the column's name.
         * @param type the column's type.
         * @param prefWidth the preferred width.
         */
        public Column(final String name, final Class<?> type, final int prefWidth) {
            this(name, type, prefWidth, false);
        }

        /**
         * Creates a new Column.
         *
         * @param name the column's name.
         * @param type the column's type.
         * @param prefWidth the preferred width.
         * @param editable <code>true</code> if the column is editable.
         */
        public Column(final String name, final Class<?> type, final int prefWidth,
                final boolean editable) {
            this.name = name;
            this.type = type;
            this.prefWidth = prefWidth;
            this.editable = editable;
        }

        /**
         * Returns the column's name.
         *
         * @return the name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the column's type.
         *
         * @return the type.
         */
        public Class<?> getType() {
            return type;
        }

        /**
         * Returns the preferred width.
         *
         * @return the preferred width.
         */
        public int getPreferredWidth() {
            return prefWidth;
        }

        /**
         * Returns <code>true</code> if the column is editable.
         *
         * @return <code>true</code> if the column is editable.
         */
        public boolean isEditable() {
            return editable;
        }

        /**
         * Returns the value for this column.
         *
         * @param node the tree node.
         * @return the value for this column.
         */
        public abstract Object value(final LoggerTreeTableNode node);
    }
}
