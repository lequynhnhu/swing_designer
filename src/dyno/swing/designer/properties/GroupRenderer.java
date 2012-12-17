/*
 * GroupRenderer.java
 *
 * Created on 2006年11月18日, 下午6:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author William Chen
 */
public class GroupRenderer extends DefaultTableCellRenderer {

    public GroupRenderer() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        String text = getText();
        if (text != null && text.trim().length() > 0) {
            if (text.startsWith("+")) {
                setIcon(PLUS_ICON);
                setText(text.substring(1));
            } else if (text.startsWith("-")) {
                setIcon(MINUS_ICON);
                setText(text.substring(1));
            } else {
                setIcon(null);
                setText(text);
            }
        } else {
            setIcon(null);
            setText(text);
        }
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getGridColor());
            setForeground(Color.black);
        }
        return this;
    }
    static final Icon PLUS_ICON = new ImageIcon(GroupRenderer.class.getResource("plus.png"));
    static final Icon MINUS_ICON = new ImageIcon(GroupRenderer.class.getResource("minus.png"));
}
