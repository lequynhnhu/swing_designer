/*
 * BoundsGroupModel.java
 *
 * Created on 2007年8月26日, 上午1:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class BoundsGroupModel implements ConstraintsGroupModel, Constants {

    private DefaultTableCellRenderer renderer;
    private PropertyCellEditor editor;
    private Component component;
    private Container parent;

    public BoundsGroupModel(Container parent, Component comp) {
        this.parent = parent;
        component = comp;
        renderer = new DefaultTableCellRenderer();
        editor = new PropertyCellEditor(new IntegerPropertyEditor());
    }

    public String getGroupName() {
        return "Component Bounds";
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
                    return "X";
                case 1:
                    return "Y";
                case 2:
                    return "WIDTH";
                default:
                    return "HEIGHT";
            }
        } else {
            switch (row) {
                case 0:
                    return component.getX();
                case 1:
                    return component.getY();
                case 2:
                    return component.getWidth();
                default:
                    return component.getHeight();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 1) {
            int v = 0;
            if (value != null) {
                v = ((Number) value).intValue();
            }
            Rectangle bounds = new Rectangle(component.getBounds());
            switch (row) {
                case 0:
                    bounds.x = v;
                    break;
                case 1:
                    bounds.y = v;
                    break;
                case 2:
                    bounds.width = v;
                    break;
                case 3:
                    bounds.height = v;
                    break;
            }
            component.setBounds(bounds);
            return true;
        } else {
            return false;
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
        return null;
    }

    @Override
    public String getAddComponentCode() {
        String childName = Util.getComponentName(component);
        return VAR_CONTAINER+".add(" + Util.getGetName(childName) + "());\n";
    }
}