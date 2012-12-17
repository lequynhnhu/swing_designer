/*
 * BooleanRenderer.java
 * 
 * Created on 2007-8-27, 1:24:52
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class BooleanRenderer extends JCheckBox implements TableCellRenderer{

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value==null)
            setSelected(false);
        else{
            boolean b=(Boolean)value;
            setSelected(b);
        }
        if(isSelected){
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }else{
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        return this;
    }

}
