
/*
 * EmptyBorderModel.java
 *
 * Created on 2007-8-27, 17:56:46
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.properties.PropertyCellEditor;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class EmptyBorderModel extends AbstractBorderModel {

    private EmptyBorder border;
    private DefaultTableCellRenderer renderer;
    private PropertyCellEditor editor;

    public EmptyBorderModel(EmptyBorder b) {
        border = b;
        renderer = new DefaultTableCellRenderer();
        editor = new PropertyCellEditor(new IntegerPropertyEditor());
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
                    return "top";
                case 1:
                    return "left";
                case 2:
                    return "bottom";
                case 3:
                    return "right";
                default:
                    return null;
            }
        } else {
            switch (row) {
                case 0:
                    return border.getBorderInsets().top;
                case 1:
                    return border.getBorderInsets().left;
                case 2:
                    return border.getBorderInsets().bottom;
                case 3:
                    return border.getBorderInsets().right;
                default:
                    return 0;
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 1) {
            int v = 0;
            if (value instanceof Number) {
                v = ((Number) value).intValue();
            }
            Insets insets=border.getBorderInsets();
            switch (row) {
                case 0:
                    border=new EmptyBorder(v, insets.left, insets.bottom, insets.right);
                    return true;
                case 1:
                    border=new EmptyBorder(insets.top, v, insets.bottom, insets.right);
                    return true;
                case 2:
                    border=new EmptyBorder(insets.top, insets.left, v, insets.right);
                    return true;
                case 3:
                    border=new EmptyBorder(insets.top, insets.left, insets.bottom, v);
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }
    public Border getBorder() {
        return border;
    }
}