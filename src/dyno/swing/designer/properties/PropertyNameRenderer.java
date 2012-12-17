/*
 * PropertyNameRenderer.java
 *
 * Created on 2007-8-24, 15:22:44
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author William Chen
 */
public class PropertyNameRenderer extends DefaultTableCellRenderer {
    public static PropertyNameRenderer DEFAULT_PROPERTY_RENDERER=new PropertyNameRenderer();
    private static Font value_font;
    private boolean valueSet;
    private String description;
    public void setValueSet(boolean b){
        valueSet=b;
    }
    public void setDescription(String desc){
        description=desc;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        value_font=getFont();
        if(valueSet){
            if(value_font==null)
                value_font=new Font("Dialog", Font.BOLD, 12);
            else
                value_font=new Font(value_font.getFamily(), Font.BOLD, value_font.getSize());
        }
        setFont(value_font);
        if(description!=null)
            this.setToolTipText(description);
        return this;
    }
    
}