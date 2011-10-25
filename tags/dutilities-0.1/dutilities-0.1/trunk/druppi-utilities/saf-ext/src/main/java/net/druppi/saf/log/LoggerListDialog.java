/*
 * LoggerListDialog.java
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
package net.druppi.saf.log;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.ActionMap;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.tree.TreePath;

import net.druppi.saf.Dialog;
import net.druppi.saf.log.tree.LoggerInfo;
import net.druppi.saf.log.tree.LoggerTreeTableModel;
import net.druppi.saf.log.tree.LoggerTreeTableNode;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlightPredicate.AndHighlightPredicate;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

/**
 * The LoggerListDialog lists the available loggers and lets the user change their level.
 *
 * @param <T> the type of the application.
 *
 * @author Olivier Sechet
 * @version 1.0 - Aug 25, 2010
 */
public class LoggerListDialog<T extends SingleFrameApplication> extends Dialog<T> {

    /** The serial version id. */
    private static final long serialVersionUID = 181671149086813828L;

    /**
     * Creates new form LoggerListDialog.
     *
     * @param application the application.
     * @param modal specifies whether dialog blocks user input to other top-level windows
     *            when shown. If true, the modality type property is set to
     *            <code>DEFAULT_MODALITY_TYPE</code>, otherwise the dialog is modeless.
     */
    public LoggerListDialog(final T application, final boolean modal) {
        super(application, modal);
        initComponents();

        for (int i = 0; i < LoggerTreeTableNode.COLUMNS.length; i++) {
            tree.getColumn(i).setPreferredWidth(
                    LoggerTreeTableNode.COLUMNS[i].getPreferredWidth());
        }

        initRenderers();
    }

    /**
     * Initializes the renderers and editors.
     */
    private void initRenderers() {
        tree.setHighlighters(new ColorHighlighter(new HighlightPredicate() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isHighlighted(final Component renderer,
                    final org.jdesktop.swingx.decorator.ComponentAdapter adapter) {
                final TreePath path = tree.getPathForRow(adapter.row);
                final LoggerTreeTableNode last = (LoggerTreeTableNode) path
                        .getLastPathComponent();
                return last.getUserObject().isEdited();
            }
        }, getResourceMap().getColor("editedRow.color"), null, //$NON-NLS-1$
                getResourceMap().getColor("editedRow.color"), null), //$NON-NLS-1$
        new ColorHighlighter(new AndHighlightPredicate(
                new HighlightPredicate.IdentifierHighlightPredicate(
                        LoggerTreeTableNode.COLUMNS[1].getName()),
                new HighlightPredicate() {
                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public boolean isHighlighted(final Component renderer,
                            final org.jdesktop.swingx.decorator.ComponentAdapter adapter) {
                        final TreePath path = tree.getPathForRow(adapter.row);
                        final LoggerTreeTableNode last = (LoggerTreeTableNode) path
                                .getLastPathComponent();
                        return last.getUserObject().getLevel() == null;
                    }
                }), null, getResourceMap().getColor("noLevel.color"), //$NON-NLS-1$
                null, getResourceMap().getColor("noLevel.color"))); //$NON-NLS-1$
        /*
         * The editor's comboBox contains all the pre-defined Levels. TThe null value is
         * used to unset the logger's level.
         */
        final JComboBox comboBox = new JComboBox(new String[] { null,
                Level.OFF.getLocalizedName(), Level.SEVERE.getLocalizedName(),
                Level.WARNING.getLocalizedName(), Level.INFO.getLocalizedName(),
                Level.CONFIG.getLocalizedName(), Level.FINE.getLocalizedName(),
                Level.FINER.getLocalizedName(), Level.FINEST.getLocalizedName(),
                Level.ALL.getLocalizedName() });

        // We use a specific renderer for the comboBox to display properly the null Level
        comboBox.setRenderer(new ComboBoxRenderer());
        // Define the editor, using the comboBox
        final ComboBoxCellEditor editor = new ComboBoxCellEditor(comboBox);
        tree.getColumn(LoggerTreeTableNode.COLUMNS[1].getName()).setCellEditor(editor);

        /*
         * This listener is used to automatically stop the edition when the comboxBox
         * selection changes.
         */
        comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(final ItemEvent evt) {
                // Use the DESELECTED state because if the selected value is null, no
                // event is fired
                if (evt.getStateChange() == ItemEvent.DESELECTED) {
                    editor.stopCellEditing();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING:
     * Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    @SuppressWarnings("nls")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new JScrollPane();
        tree = new JXTreeTable();
        okButton = new JButton();
        applyButton = new JButton();
        cancelButton = new JButton();

        setTitle("Loggers");
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        scrollPane.setName("scrollPane"); // NOI18N

        tree.setName("tree"); // NOI18N
        tree.setRootVisible(true);
        tree.setTreeTableModel(new LoggerTreeTableModel(getResourceMap().getString(
                "LoggerTree.root.name")));
        scrollPane.setViewportView(tree);

        ActionMap actionMap = Application.getInstance().getContext()
                .getActionMap(LoggerListDialog.class, this);
        okButton.setAction(actionMap.get("ok")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        setDefaultButton(okButton);

        applyButton.setAction(actionMap.get("apply")); // NOI18N
        applyButton.setName("applyButton"); // NOI18N

        cancelButton.setAction(actionMap.get("cancel")); // NOI18N
        cancelButton.setDefaultCapable(false);
        cancelButton.setName("cancelButton"); // NOI18N
        setCancelButton(cancelButton);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        Alignment.TRAILING,
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(scrollPane,
                                                        Alignment.LEADING,
                                                        GroupLayout.DEFAULT_SIZE, 380,
                                                        Short.MAX_VALUE)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(okButton)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(applyButton)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        cancelButton)))
                                .addContainerGap()));

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { applyButton,
                cancelButton, okButton });

        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(
                        Alignment.TRAILING,
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 244,
                                        Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(
                                        layout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(cancelButton)
                                                .addComponent(applyButton)
                                                .addComponent(okButton))
                                .addContainerGap()));

        layout.linkSize(SwingConstants.VERTICAL, new Component[] { applyButton,
                cancelButton, okButton });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when the dialog appears.
     *
     * @param evt the associated event.
     */
    private void formComponentShown(ComponentEvent evt) {// GEN-FIRST:event_formComponentShown
        /*
         * Rebuild the tree each time the dialog is shown. Indeed the list of logger can
         * change from one call to another one.
         */
        ((LoggerTreeTableModel) tree.getTreeTableModel()).reset();
    }// GEN-LAST:event_formComponentShown

    /**
     * Called when the OK button is pressed.
     */
    @Action
    public void ok() {
        apply();
        setVisible(false);
    }

    /**
     * Called when the Apply button is pressed.
     */
    @Action
    public void apply() {
        final LoggerTreeTableModel model = (LoggerTreeTableModel) tree.getTreeTableModel();
        final Enumeration<String> names = LogManager.getLogManager().getLoggerNames();
        final ArrayList<String> list = Collections.list(names);

        final LoggerTreeTableNode root = (LoggerTreeTableNode) model.getRoot();
        apply(root, model, list);
    }

    /**
     * Apply the modification of the specified node.
     *
     * @param node a node.
     * @param model the model.
     * @param list the list of existing loggers.
     */
    private void apply(final LoggerTreeTableNode node, final LoggerTreeTableModel model,
            final ArrayList<String> list) {
        final LoggerInfo logger = node.getUserObject();
        if (logger.isEdited()) {
            final String name = model.getPath(node);
            final String levelName = logger.getLevel();
            if (levelName != null) {
                final Level level = Level.parse(levelName);
                Logger.getLogger(name).setLevel(level);
            } else {
                if (list.contains(name)) {
                    Logger.getLogger(name).setLevel(null);
                }
            }
            logger.apply();

            model.valueForPathChanged(model.getTreePath(node), node.getUserObject());
        }

        final Enumeration<? extends MutableTreeTableNode> children = node.children();
        while (children.hasMoreElements()) {
            apply((LoggerTreeTableNode) children.nextElement(), model, list);
        }
    }

    /**
     * Called when the Cancel button is pressed.
     */
    @Action
    public void cancel() {
        final LoggerTreeTableModel model = (LoggerTreeTableModel) tree.getTreeTableModel();
        final LoggerTreeTableNode root = (LoggerTreeTableNode) model.getRoot();

        reset(root);
        setVisible(false);
    }

    /**
     * Resets the value of the given node.
     *
     * @param node the node.
     */
    private void reset(final LoggerTreeTableNode node) {
        final LoggerInfo logger = node.getUserObject();
        if (logger.isEdited()) {
            node.getUserObject().reset();
        }

        final Enumeration<? extends MutableTreeTableNode> children = node.children();
        while (children.hasMoreElements()) {
            reset((LoggerTreeTableNode) children.nextElement());
        }
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton applyButton;
    private JButton cancelButton;
    private JButton okButton;
    private JScrollPane scrollPane;
    private JXTreeTable tree;

    // End of variables declaration//GEN-END:variables

    /**
     * The renderer used in the editor's comboBox. Its purpose is to properly display the
     * <code>null</code> Level.
     *
     * @author Olivier Sechet
     * @version 1.0 - Aug 26, 2010
     */
    private final class ComboBoxRenderer extends DefaultListCellRenderer {

        /** The serial version id. */
        private static final long serialVersionUID = -4184601569872599724L;

        /**
         * {@inheritDoc}
         */
        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value, final int index, final boolean isSelected,
                final boolean cellHasFocus) {
            if (value == null) {
                return super.getListCellRendererComponent(list, getResourceMap()
                        .getString("use_parent_level"), index, isSelected, //$NON-NLS-1$
                        cellHasFocus);
            }
            return super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);
        }
    }
}
