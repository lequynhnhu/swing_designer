/*
 * AbstractPropertyTable.java
 *
 * Created on 2007-8-27, 16:29:17
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.GroupModel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author William Chen
 */
public abstract class AbstractPropertyTable extends JTable {

    protected ArrayList<PropertyGroup> groups;
    protected TableModel default_table_model;

    public AbstractPropertyTable() {
        setRowHeight(22);
        JTableHeader header = getTableHeader();
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 22));
        header.setDefaultRenderer(new HeaderRenderer());
        setGridColor(new Color(212, 208, 200));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        setFillsViewportHeight(true);
        initPopup();
        default_table_model = new DefaultTableModel();
        setModel(default_table_model);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void setBean(Object bean) {
    }

    private Point getGroupIndex(int row) {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            PropertyGroup group = groups.get(i);
            int groupRowCount = 1;
            if (!group.isFoldered()) {
                groupRowCount += group.getModel().getRowCount();
            }
            if (count + groupRowCount > row) {
                return new Point(i, row - count);
            }
            count += groupRowCount;
        }
        return null;
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        if (groups != null) {
            Point pIndex = getGroupIndex(row);
            PropertyGroup group = groups.get(pIndex.x);
            if (pIndex.y == 0) {
                if (column == 0) {
                    return group.get1stRenderer();
                } else {
                    return group.get2stRenderer();
                }
            } else {
                if (column == 0) {
                    int r = pIndex.y - 1;
                    GroupModel model = group.getModel();
                    boolean valueSet = model.isValueSet(r);
                    PropertyNameRenderer.DEFAULT_PROPERTY_RENDERER.setValueSet(valueSet);
                    if (showDescription) {
                        String desc = model.getPropertyDescription(r);
                        PropertyNameRenderer.DEFAULT_PROPERTY_RENDERER.setToolTipText(desc);
                    } else {
                        PropertyNameRenderer.DEFAULT_PROPERTY_RENDERER.setToolTipText(null);
                    }
                    return PropertyNameRenderer.DEFAULT_PROPERTY_RENDERER;
                } else {
                    return group.getModel().getRenderer(pIndex.y - 1);
                }
            }
        } else {
            return super.getCellRenderer(row, column);
        }
    }

    public TableCellEditor getCellEditor(int row, int column) {
        if (groups != null) {
            Point pIndex = getGroupIndex(row);
            PropertyGroup group = groups.get(pIndex.x);
            if (pIndex.y == 0) {
                return super.getCellEditor(row, column);
            } else {
                if (column == 0) {
                    return super.getCellEditor(row, column);
                } else {
                    return group.getModel().getEditor(pIndex.y - 1);
                }
            }
        } else {
            return super.getCellEditor(row, column);
        }
    }

    protected class BeanTableModel extends AbstractTableModel {

        public int getRowCount() {
            int row = 0;
            for (PropertyGroup group : groups) {
                row++;
                if (!group.isFoldered()) {
                    row += group.getModel().getRowCount();
                }
            }
            return row;
        }

        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int row, int column) {
            Point pIndex = getGroupIndex(row);
            PropertyGroup group = groups.get(pIndex.x);
            if (pIndex.y == 0) {
                if (column == 0) {
                    return (group.isFoldered() ? "+" : "-") + group.getName();
                } else {
                    return null;
                }
            } else {
                return group.getModel().getValue(pIndex.y - 1, column);
            }
        }

        public String getColumnName(int column) {
            if (column == 0) {
                return "property";
            } else {
                return "value";
            }
        }

        public void setValueAt(Object aValue, int row, int column) {
            Point pIndex = getGroupIndex(row);
            PropertyGroup group = groups.get(pIndex.x);
            if (pIndex.y != 0) {
                Object old_value = getValueAt(row, column);
                boolean success = group.getModel().setValue(aValue, pIndex.y - 1, column);
                fireValueChanged(old_value, success, aValue);
            } else {
                group.setFoldered((Boolean) aValue);
            }
        }

        public boolean isCellEditable(int row, int column) {
            Point pIndex = getGroupIndex(row);
            PropertyGroup group = groups.get(pIndex.x);
            if (pIndex.y == 0) {
                if (column == 0) {
                    return false;
                } else {
                    return false;
                }
            } else {
                return column == 1 && group.getModel().isEditable(pIndex.y - 1);
            }
        }
    }

    protected void fireValueChanged(Object old_value, boolean success, Object aValue) {
        if (success && (old_value != aValue) && ((old_value != null) && !old_value.equals(aValue) || (aValue != null && !aValue.equals(old_value)))) {
            fireComponentEdited();
        }
    }

    protected abstract void fireComponentEdited();

    private void initPopup() {

        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (!e.isPopupTrigger() && groups != null) {
                    int row = AbstractPropertyTable.super.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        Point pIndex = getGroupIndex(row);
                        if (pIndex.y == 0 && e.getClickCount() > 1) {
                            expandGroup(pIndex.x);
                        }
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
                if (!e.isPopupTrigger() && groups != null) {
                    int row = AbstractPropertyTable.super.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        Point pIndex = getGroupIndex(row);
                        if (pIndex.y == 0 && e.getClickCount() == 1 && e.getX() < 10) {
                            expandGroup(pIndex.x);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() && groups != null) {
                    popupMenu(e);
                }
            }
        });
    }

    private void popupMenu(MouseEvent e) {
        int row = getSelectedRow();
        boolean enabled = false;
        GroupModel model = null;
        if (row != -1) {
            Point pIndex = getGroupIndex(row);
            if (pIndex.y != 0) {
                PropertyGroup group = groups.get(pIndex.x);
                model = group.getModel();
                row = pIndex.y - 1;
                enabled = model.isValueSet(row);
            }
        }
        JPopupMenu popup = new JPopupMenu();
        JMenuItem showDescMenu = new JMenuItem(showDescription ? "Hide description tip" : "Show description tip");
        popup.add(showDescMenu);
        showDescMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showDescription = !showDescription;
            }
        });
        popup.add(new JSeparator());
        JMenuItem restoreMenu = new JMenuItem("Restore default value");
        popup.add(restoreMenu);
        if (enabled) {
            restoreMenu.addActionListener(new RestoreAction(model, row));
        }
        restoreMenu.setEnabled(enabled);
        popup.show(this, e.getX(), e.getY());
    }
    private boolean showDescription = true;

    private class RestoreAction implements ActionListener {

        private GroupModel model;
        private int row;

        public RestoreAction(GroupModel m, int row) {
            this.model = m;
            this.row = row;
        }

        public void actionPerformed(ActionEvent e) {
            if (model.restoreDefaultValue(row)) {
                fireComponentEdited();
            }
        }
    }

    private void expandGroup(int groupIndex) {
        PropertyGroup group = groups.get(groupIndex);
        group.setFoldered(!group.isFoldered());
        Container parent = AbstractPropertyTable.this.getParent();
        if (parent != null) {
            LayoutManager layout = parent.getLayout();
            parent.doLayout();
        }
        repaint();
    }
}