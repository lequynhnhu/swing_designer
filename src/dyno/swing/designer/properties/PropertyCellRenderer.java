/*
 * PropertyRenderer.java
 *
 * Created on 2007年8月13日, 上午9:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties;

import java.awt.Color;
import java.awt.Component;

import java.beans.PropertyEditor;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author William Chen
 */
public class PropertyCellRenderer implements TableCellRenderer {
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 4, 1,
            1);
    protected static Border noFocusBorder = new EmptyBorder(1, 4, 1, 1);
    private PropertyEditor editor;

    /** Creates a new instance of PropertyRenderer */
    public PropertyCellRenderer(PropertyEditor editor) {
        this.editor = editor;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = editor.getCustomEditor();
        Color fg = null;
        Color bg = null;

        JTable.DropLocation dropLocation = table.getDropLocation();

        if ((dropLocation != null) && !dropLocation.isInsertRow() &&
                !dropLocation.isInsertColumn() &&
                (dropLocation.getRow() == row) &&
                (dropLocation.getColumn() == column)) {
            fg = UIManager.getColor("Table.dropCellForeground");
            bg = UIManager.getColor("Table.dropCellBackground");

            isSelected = true;
        }

        if (isSelected) {
            component.setForeground((fg == null)
                ? table.getSelectionForeground() : fg);
            component.setBackground((bg == null)
                ? table.getSelectionBackground() : bg);
        } else {
            component.setForeground(table.getForeground());
            component.setBackground(table.getBackground());
        }

        component.setFont(table.getFont());

        if (hasFocus) {
            Border border = null;

            if (isSelected) {
                border = UIManager.getBorder(
                        "Table.focusSelectedCellHighlightBorder");
            }

            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }

            ((JComponent) component).setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");

                if (col != null) {
                    component.setForeground(col);
                }

                col = UIManager.getColor("Table.focusCellBackground");

                if (col != null) {
                    component.setBackground(col);
                }
            }
        }

        ((JComponent) component).setBorder(getNoFocusBorder());

        editor.setValue(value);

        return component;
    }

    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }
}
