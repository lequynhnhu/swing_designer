/*
 * TabbedPaneConstraints.java
 *
 * Created on 2007-8-26, 23:45:15
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.renderer.IconCellRenderer;
import dyno.swing.designer.properties.wrappers.IconWrapper;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class TabbedPaneConstraints implements ConstraintsGroupModel, Constants {

    private DefaultTableCellRenderer defaultRenderer;
    private DefaultCellEditor defaultEditor;
    private IconCellEditor iconEditor;
    private IconCellRenderer iconRenderer;
    private JTabbedPane tabbedPane;
    private Component component;

    public TabbedPaneConstraints(JTabbedPane tabbedPane, Component component) {
        this.tabbedPane = tabbedPane;
        this.component = component;
        this.iconRenderer = new IconCellRenderer();
        this.defaultRenderer = new DefaultTableCellRenderer();
        JTextField txt = new JTextField();
        txt.setBorder(null);
        this.defaultEditor = new DefaultCellEditor(txt);
        this.defaultEditor.setClickCountToStart(1);
        this.iconEditor = new IconCellEditor();
    }

    public String getGroupName() {
        return "Tab Constraints";
    }

    public int getRowCount() {
        return 3;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return defaultRenderer;
            case 1:
                return iconRenderer;
            default:
                return defaultRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return defaultEditor;
            case 1:
                return iconEditor;
            default:
                return defaultEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "Tab Title";
                case 1:
                    return "Tab Icon";
                default:
                    return "Tab ToolTip";
            }
        } else {
            int index = tabbedPane.indexOfComponent(component);
            switch (row) {
                case 0:
                    return tabbedPane.getTitleAt(index);
                case 1:
                    return tabbedPane.getIconAt(index);
                default:
                    return tabbedPane.getToolTipTextAt(index);
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 1) {
            int index = tabbedPane.indexOfComponent(component);
            switch (row) {
                case 0:
                    String title = (String) value;
                    tabbedPane.setTitleAt(index, title);
                    return true;
                case 1:
                    Icon icon = (Icon) value;
                    tabbedPane.setIconAt(index, icon);
                    return true;
                case 2:
                    String tooltip = (String) value;
                    tabbedPane.setToolTipTextAt(index, tooltip);
                    return true;
                default:
                    return false;
            }
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
        switch (row) {
            case 0:
                return "Tab title";
            case 1:
                return "Tab icon";
            case 2:
                return "Tab Tooltip";
            default:
                return null;
        }
    }

    @Override
    public String getAddComponentCode() {
        String componentName = Util.getComponentName(component);
        int index = tabbedPane.indexOfComponent(component);
        String tabTitle = tabbedPane.getTitleAt(index);
        Icon tabIcon = tabbedPane.getIconAt(index);
        String tooltip = tabbedPane.getToolTipTextAt(index);
        tabbedPane.addTab(tabTitle, tabIcon, component, tooltip);
        if (tooltip == null) {
            if (tabIcon == null) {
                return VAR_CONTAINER+".addTab(\"" + tabTitle + "\", " + Util.getGetName(componentName) + "());\n";
            } else {
                IconWrapper wrapper = new IconWrapper();
                return VAR_CONTAINER+".addTab(\"" + tabTitle + "\", " + wrapper.getJavaCode(tabIcon) + ", " + Util.getGetName(componentName) + "());\n";
            }
        } else {
            IconWrapper wrapper = new IconWrapper();
            return VAR_CONTAINER+".addTab(\"" + tabTitle + "\", " + wrapper.getJavaCode(tabIcon) + ", " + Util.getGetName(componentName) + "(), \"" + tooltip + "\");\n";
        }
    }
}