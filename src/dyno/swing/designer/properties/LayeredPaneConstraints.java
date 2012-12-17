/*
 * LayeredPaneConstraints.java
 *
 * Created on 2007-9-3, 22:26:46
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
import javax.swing.JLayeredPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class LayeredPaneConstraints implements ConstraintsGroupModel, Constants {

    private PropertyCellEditor layerEditor;
    private EncoderCellRenderer layerRenderer;
    private PropertyCellEditor positionEditor;
    private DefaultTableCellRenderer positionRenderer;
    private JLayeredPane layeredPane;
    private Component component;

    public LayeredPaneConstraints(JLayeredPane layeredPane, Component component) {
        this.layeredPane = layeredPane;
        this.component = component;
        layerEditor = new PropertyCellEditor(new ItemCellEditor(new LayerItems()));
        layerRenderer = new EncoderCellRenderer(new ItemWrapper(new LayerItems()));
        positionEditor = new PropertyCellEditor(new IntegerPropertyEditor());
        positionRenderer = new DefaultTableCellRenderer();
    }

    public String getGroupName() {
        return "Layer Properties";
    }

    public int getRowCount() {
        return 2;
    }

    public TableCellRenderer getRenderer(int row) {
        if (row == 0) {
            return layerRenderer;
        } else {
            return positionRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        if (row == 0) {
            return layerEditor;
        } else {
            return positionEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            if (row == 0) {
                return "Layer";
            } else {
                return "Index";
            }
        } else {
            if (row == 0) {
                return layeredPane.getLayer(component);
            } else {
                return layeredPane.getPosition(component);
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
                    layeredPane.setLayer(component, layer);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (value != null) {
                    int position = ((Number) value).intValue();
                    layeredPane.setPosition(component, position);
                    return true;
                } else {
                    return false;
                }
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
        return VAR_CONTAINER+".add("+Util.getGetName(childName)+"());\n";
    }
}