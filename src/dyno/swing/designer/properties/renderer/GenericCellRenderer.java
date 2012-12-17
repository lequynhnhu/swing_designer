/*
 * GenericCellRenderer.java
 *
 * Created on 2007年8月13日, 下午7:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import java.awt.Color;
import java.awt.Component;

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
public abstract class GenericCellRenderer extends JComponent
    implements TableCellRenderer {
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1,
            1);
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    /** Creates a new instance of ColorCellRenderer */
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
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
            setForeground((fg == null) ? table.getSelectionForeground() : fg);
            setBackground((bg == null) ? table.getSelectionBackground() : bg);
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        setFont(table.getFont());

        if (hasFocus) {
            Border border = null;

            if (isSelected) {
                border = UIManager.getBorder(
                        "Table.focusSelectedCellHighlightBorder");
            }

            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }

            setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");

                if (col != null) {
                    setForeground(col);
                }

                col = UIManager.getColor("Table.focusCellBackground");

                if (col != null) {
                    setBackground(col);
                }
            }
        }

        setBorder(getNoFocusBorder());
        setValue(value);

        return this;
    }

    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }

    public abstract void setValue(Object value);
}
