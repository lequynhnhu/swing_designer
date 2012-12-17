/*
 * SplitPaneConstraints.java
 *
 * Created on 2007-8-26, 22:56:51
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.Util;
import java.awt.Component;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class SplitPaneConstraints implements ConstraintsGroupModel {

    private DefaultTableCellRenderer renderer;
    private SplitPaneConstraintsEditor editor;
    private Component component;
    private JSplitPane splitPane;

    public SplitPaneConstraints(JSplitPane sp, Component cmp) {
        splitPane = sp;
        component = cmp;
        renderer = new DefaultTableCellRenderer();
        editor = new SplitPaneConstraintsEditor();
    }

    public String getGroupName() {
        return "SplitPane Constraints";
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
            if (splitPane.getLeftComponent() == component) {
                return "left";
            } else if (splitPane.getRightComponent() == component) {
                return "right";
            } else if (splitPane.getTopComponent() == component) {
                return "top";
            } else if (splitPane.getBottomComponent() == component) {
                return "bottom";
            } else {
                return null;
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 1) {
            String constraints = (String) value;
            if (value != null) {
                if (constraints.equals("left")) {
                    Component leftComp = splitPane.getLeftComponent();
                    if (leftComp == null) {
                        splitPane.remove(component);
                        splitPane.setLeftComponent(component);
                    } else {
                        splitPane.remove(leftComp);
                        splitPane.remove(component);
                        splitPane.setLeftComponent(component);
                        splitPane.setRightComponent(leftComp);
                    }
                    splitPane.invalidate();
                    Util.layoutContainer(splitPane);
                    return true;
                } else if (constraints.equals("right")) {
                    Component rightComp = splitPane.getRightComponent();
                    if (rightComp == null) {
                        splitPane.remove(component);
                        splitPane.setRightComponent(component);
                    } else {
                        splitPane.remove(rightComp);
                        splitPane.remove(component);
                        splitPane.setRightComponent(component);
                        splitPane.setLeftComponent(rightComp);
                    }
                    splitPane.invalidate();
                    Util.layoutContainer(splitPane);
                    return true;
                } else if (constraints.equals("top")) {
                    Component topComp = splitPane.getTopComponent();
                    if (topComp == null) {
                        splitPane.remove(component);
                        splitPane.setTopComponent(component);
                    } else {
                        splitPane.remove(topComp);
                        splitPane.remove(component);
                        splitPane.setTopComponent(component);
                        splitPane.setBottomComponent(topComp);
                    }
                    splitPane.invalidate();
                    Util.layoutContainer(splitPane);
                    return true;
                } else if (constraints.equals("bottom")) {
                    Component bottomComp = splitPane.getBottomComponent();
                    if (bottomComp == null) {
                        splitPane.remove(component);
                        splitPane.setBottomComponent(component);
                    } else {
                        splitPane.remove(bottomComp);
                        splitPane.remove(component);
                        splitPane.setBottomComponent(component);
                        splitPane.setTopComponent(bottomComp);
                    }
                    splitPane.invalidate();
                    Util.layoutContainer(splitPane);
                    return true;
                } else {
                    return false;
                }
            } else {
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
        return "SplitPane constraints";
    }

    @Override
    public String getAddComponentCode() {
        String compName = Util.getComponentName(component);
        if (splitPane.getLeftComponent() == component) {
            return Constants.VAR_CONTAINER+".setLeftComponent(" + Util.getGetName(compName) + "());\n";
        } else if (splitPane.getRightComponent() == component) {
            return Constants.VAR_CONTAINER+".setRightComponent(" + Util.getGetName(compName) + "());\n";
        } else if (splitPane.getTopComponent() == component) {
            return Constants.VAR_CONTAINER+".setTopComponent(" + Util.getGetName(compName) + "());\n";
        } else if (splitPane.getBottomComponent() == component) {
            return Constants.VAR_CONTAINER+".setBottomComponent(" + Util.getGetName(compName) + "());\n";
        }
        return null;
    }
}