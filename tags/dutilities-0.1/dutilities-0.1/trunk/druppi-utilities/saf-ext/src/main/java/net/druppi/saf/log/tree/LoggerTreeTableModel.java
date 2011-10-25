/*
 * LoggerTreeTableModel.java
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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * This model is used to list the loggers in the tree.
 *
 * @author Olivier Sechet
 * @version 1.0 - Aug 25, 2010
 */
public final class LoggerTreeTableModel extends DefaultTreeTableModel {

    /** The name of the root logger. */
    private String rootName;

    /**
     * Creates a new LoggerTreeTableModel.
     *
     * @param rootName the name of the root logger.
     */
    public LoggerTreeTableModel(final String rootName) {
        this.rootName = rootName;
        reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(final int column) {
        return LoggerTreeTableNode.COLUMNS[column].getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getColumnClass(final int column) {
        return LoggerTreeTableNode.COLUMNS[column].getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(final Object value, final Object node, final int column) {
        super.setValueAt(value, node, column);

        final LoggerTreeTableNode ttn = (LoggerTreeTableNode) node;
        // When the level of a node changed, the children's level may change
        if (!ttn.isLeaf()) {
            modelSupport.fireChildrenChanged(new TreePath(getPathToRoot(ttn)), null, null);
        }
    }

    /**
     * Resets the model using the current list of <code>Logger</code>s.
     */
    public void reset() {
        if (getRoot() == null) {
            final Logger rootLogger = Logger.getLogger(""); //$NON-NLS-1$
            setRoot(new LoggerTreeTableNode(new LoggerInfo(rootName, rootLogger
                    .getLevel().getName())));
        }
        updateTree();
    }

    /**
     * Builds the tree.
     */
    private void updateTree() {
        final Logger rootLogger = Logger.getLogger(""); //$NON-NLS-1$
        final LoggerTreeTableNode root = (LoggerTreeTableNode) getRoot();
        root.getUserObject().setLevel(rootLogger.getLevel().getLocalizedName());
        root.getUserObject().apply();

        final Enumeration<String> names = LogManager.getLogManager().getLoggerNames();
        while (names.hasMoreElements()) {
            final Logger logger = Logger.getLogger(names.nextElement());
            if (!logger.equals(rootLogger)) {
                final LoggerTreeTableNode node = buildPath(logger, root);
                root.add(node);
            }
        }
        // Notify the root changed
        modelSupport.fireChildrenChanged(new TreePath(root), null, null);
    }

    /**
     * Builds all the nodes (if necessary) to the node of the specified logger.
     *
     * @param logger a logger.
     * @param root the root node.
     * @return the logger's node.
     */
    private LoggerTreeTableNode buildPath(final Logger logger,
            final LoggerTreeTableNode root) {
        final String name = logger.getName();
        final StringTokenizer st = new StringTokenizer(name, "."); //$NON-NLS-1$
        LoggerTreeTableNode current = root;

        LoggerTreeTableNode node = null;
        LoggerTreeTableNode first = null;
        while (st.hasMoreTokens()) {
            final String nodeName = st.nextToken();
            node = current.getChild(nodeName);
            if (node == null) {
                node = new LoggerTreeTableNode(new LoggerInfo(nodeName));
                current.add(node);
            }
            current = node;
            if (first == null) {
                first = node;
            }
        }
        // The last processed node is the logger's node. It has to be updated.
        if (logger.getLevel() != null) {
            node.getUserObject().setLevel(logger.getLevel().getLocalizedName());
            node.getUserObject().apply();
        }
        return first;
    }

    /**
     * Returns the node for the specified path. The path is defined as the long name of a
     * Logger with a dot as the separator: <code>path.to.node</code>.
     *
     * @param path the path to the node.
     * @return the specified node or <code>null</code> if the path is not valid.
     */
    public LoggerTreeTableNode getNode(final String path) {
        final StringTokenizer st = new StringTokenizer(path, "."); //$NON-NLS-1$
        LoggerTreeTableNode current = (LoggerTreeTableNode) root;
        while (st.hasMoreTokens() && current != null) {
            final String name = st.nextToken();
            final LoggerTreeTableNode node = current.getChild(name);
            // If a child does not exist, node is null, so will be current.
            current = node;
        }
        return current;
    }

    /**
     * Returns the path of the specified node. The path is defined as the long name of a
     * Logger with a dot as the separator: <code>path.to.node</code>.
     *
     * @param node the node.
     * @return the path of the node.
     */
    public String getPath(final LoggerTreeTableNode node) {
        final StringBuilder buffer = new StringBuilder();

        LoggerTreeTableNode current = node;
        while (current != null) {
            final String name = current.getUserObject().getName();
            // don't happen the root
            if (name.length() > 0 && !name.equals(rootName)) {
                if (buffer.length() > 0) {
                    // it isn't the first node
                    buffer.insert(0, "."); //$NON-NLS-1$
                }
                buffer.insert(0, name);
            }
            current = (LoggerTreeTableNode) current.getParent();
        }
        return buffer.toString();
    }

    /**
     * Returns the tree path from the root to the given node.
     *
     * @param node the node.
     * @return the tree path from the root.
     */
    public TreePath getTreePath(final LoggerTreeTableNode node) {
        final List<LoggerTreeTableNode> path = new ArrayList<LoggerTreeTableNode>();
        LoggerTreeTableNode current = node;
        while (current != null) {
            path.add(0, current);
            current = (LoggerTreeTableNode) current.getParent();
        }
        return new TreePath(path.toArray(new LoggerTreeTableNode[path.size()]));
    }
}
