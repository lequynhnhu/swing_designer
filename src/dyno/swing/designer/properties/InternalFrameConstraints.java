/*
 * InternalFrameConstraints.java
 *
 * Created on 2007-9-5, 1:32:12
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import dyno.swing.designer.properties.editors.ItemCellEditor;
import dyno.swing.designer.properties.items.LayerItems;
import dyno.swing.designer.properties.renderer.EncoderCellRenderer;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class InternalFrameConstraints implements ConstraintsGroupModel, Constants {

    private PropertyCellEditor layerEditor;
    private EncoderCellRenderer layerRenderer;
    private PropertyCellEditor positionEditor;
    private DefaultTableCellRenderer positionRenderer;
    private JDesktopPane desktopPane;
    private Component component;
    private DefaultTableCellRenderer renderer;
    private PropertyCellEditor editor;

    public InternalFrameConstraints(JDesktopPane desktopPane, Component component) {
        this.desktopPane = desktopPane;
        this.component = component;
        layerEditor = new PropertyCellEditor(new ItemCellEditor(new LayerItems()));
        layerRenderer = new EncoderCellRenderer(new ItemWrapper(new LayerItems()));
        positionEditor = new PropertyCellEditor(new IntegerPropertyEditor());
        positionRenderer = new DefaultTableCellRenderer();

        renderer = new DefaultTableCellRenderer();
        editor = new PropertyCellEditor(new IntegerPropertyEditor());
    }

    public String getGroupName() {
        return "Layer Properties";
    }

    public int getRowCount() {
        return 6;
    }

    public TableCellRenderer getRenderer(int row) {
        if (row == 0) {
            return layerRenderer;
        } else if (row == 1) {
            return positionRenderer;
        } else {
            return renderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        if (row == 0) {
            return layerEditor;
        } else if (row == 1) {
            return positionEditor;
        } else {
            return editor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "Layer";
                case 1:
                    return "Index";
                case 2:
                    return "X";
                case 3:
                    return "Y";
                case 4:
                    return "WIDTH";
                default:
                    return "HEIGHT";
            }
        } else {
            switch (row) {
                case 0:
                    return desktopPane.getLayer(component);
                case 1:
                    return desktopPane.getPosition(component);
                case 2:
                    return component.getX();
                case 3:
                    return component.getY();
                case 4:
                    return component.getWidth();
                default:
                    return component.getHeight();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            if (row == 0) {
                if (value != null) {
                    int layer = ((Number) value).intValue();
                    desktopPane.setLayer(component, layer);
                    return true;
                } else {
                    return false;
                }
            } else if (row == 1) {
                if (value != null) {
                    int position = ((Number) value).intValue();
                    desktopPane.setPosition(component, position);
                    return true;
                } else {
                    return false;
                }
            } else {
                int v = 0;
                if (value != null) {
                    v = ((Number) value).intValue();
                }
                Rectangle bounds = new Rectangle(component.getBounds());
                switch (row) {
                    case 2:
                        bounds.x = v;
                        break;
                    case 3:
                        bounds.y = v;
                        break;
                    case 4:
                        bounds.width = v;
                        break;
                    case 5:
                        bounds.height = v;
                        break;
                }
                component.setBounds(bounds);
                return true;
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
        return null;
    }

    @Override
    public String getAddComponentCode() {
        String childName=Util.getComponentName(component);
        String code=VAR_CONTAINER+".add("+Util.getGetName(childName)+"());\n";
        return code;
    }
}