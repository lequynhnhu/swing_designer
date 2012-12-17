/*
 * BorderLayoutProperties.java
 *
 * Created on 2007-8-27, 0:58:44
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import java.awt.GridLayout;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class GridLayoutProperties implements GroupModel {

    private DefaultTableCellRenderer renderer;
    private PropertyCellEditor editor;
    private GridLayout layout;

    public GridLayoutProperties(GridLayout layout) {
        this.layout = layout;
        renderer = new DefaultTableCellRenderer();
        JFormattedTextField jftf = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jftf.setBorder(null);
        editor = new PropertyCellEditor(new IntegerPropertyEditor());
    }

    public String getGroupName() {
        return "Grid Layout";
    }

    public int getRowCount() {
        return 4;
    }

    public TableCellRenderer getRenderer(int row) {
        return renderer;
    }

    public TableCellEditor getEditor(int row) {
        return editor;
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "hgap";
                case 1:
                    return "vgap";
                case 2:
                    return "rows";
                case 3:
                    return "columns";
                default:
                    return null;
            }
        } else {
            switch (row) {
                case 0:
                    return layout.getHgap();
                case 1:
                    return layout.getVgap();
                case 2:
                    return layout.getRows();
                case 3:
                    return layout.getColumns();
                default:
                    return null;
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            int v = 0;
            if (value != null) {
                v = ((Number) value).intValue();
            }
            switch (row) {
                case 0:
                    layout.setHgap(v);
                    return true;
                case 1:
                    layout.setVgap(v);
                    return true;
                case 2:
                    layout.setRows(v);
                    return true;
                case 3:
                    layout.setColumns(v);
                    return true;
                default:
                    return false;
            }
        }
    }

    public boolean isEditable(int row) {
        return true;
    }

    public boolean isValueSet(int row) {
        return false;
    }

    public boolean restoreDefaultValue(int row) {
        return false;
    }

    public String getPropertyDescription(int row) {
        switch (row) {
            case 0:
                return "hgap";
            case 1:
                return "vgap";
            case 2:
                return "rows";
            case 3:
                return "columns";
            default:
                return null;
        }
    }
}