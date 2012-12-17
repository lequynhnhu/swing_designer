/*
 * BeanPropertyModel.java
 *
 * Created on 2007年8月12日, 下午8:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author William Chen
 */
public interface GroupModel{
    String getGroupName();
    
    int getRowCount();

    TableCellRenderer getRenderer(int row);

    TableCellEditor getEditor(int row);

    Object getValue(int row, int column);

    boolean setValue(Object value, int row, int column);

    boolean isEditable(int row);
    
    boolean isValueSet(int row);
    
    boolean restoreDefaultValue(int row);
    
    String getPropertyDescription(int row);
}
