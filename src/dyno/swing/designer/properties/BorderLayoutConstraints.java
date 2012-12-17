/*
 * BorderGroupModel.java
 *
 * Created on 2007-8-26, 22:30:59
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.items.BorderConstraintsItems;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class BorderLayoutConstraints implements ConstraintsGroupModel, Constants {
    private DefaultTableCellRenderer renderer;
    private BorderConstraintsEditor editor;
    private Component component;
    private Container parent;
    private BorderLayout layout;
    private SourceCoder coder;
    public BorderLayoutConstraints(Container parent, BorderLayout layout, Component comp) {
        this.parent = parent;
        this.layout = layout;
        this.component = comp;
        this.renderer = new DefaultTableCellRenderer();
        this.editor = new BorderConstraintsEditor();
        this.coder=new ItemWrapper(new BorderConstraintsItems());
    }

    public String getGroupName() {
        return "Layout Constraints";
    }

    public int getRowCount() {
        return 1;
    }

    public TableCellRenderer getRenderer(int row) {
        return renderer;
    }

    public TableCellEditor getEditor(int row) {
        return editor;
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            return "Constraints";
        } else {
            return layout.getConstraints(component);
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 1) {
            Component ncomp = layout.getLayoutComponent(value);
            if (ncomp == null) {
                parent.remove(component);
                parent.add(component, value);
            } else {
                Object constraints = layout.getConstraints(component);
                parent.remove(component);
                parent.remove(ncomp);
                parent.add(component, value);
                parent.add(ncomp, constraints);
            }
            Util.layoutContainer(parent);
            parent.invalidate();
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
        return true;
    }

    public String getPropertyDescription(int row) {
        return "The constraints of border layout.";
    }

    @Override
    public String getAddComponentCode() {
        String constraints=(String)layout.getConstraints(component);
        String constCode = coder.getJavaCode(constraints);
        String componentName=Util.getComponentName(component);
        return VAR_CONTAINER+".add("+Util.getGetName(componentName)+"(), "+constCode+");\n";
    }
}