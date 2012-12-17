/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.editors.DoubleEditor;
import dyno.swing.designer.properties.editors.InsetsEditor;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import dyno.swing.designer.properties.editors.ItemCellEditor;
import dyno.swing.designer.properties.editors.SpinnerEditor;
import dyno.swing.designer.properties.items.GridBagConstraintsAnchorItems;
import dyno.swing.designer.properties.items.GridBagConstraintsFillItems;
import dyno.swing.designer.properties.renderer.EncoderCellRenderer;
import dyno.swing.designer.properties.renderer.GridBagConstraintsDimWrapper;
import dyno.swing.designer.properties.renderer.InsetsCellRenderer;
import dyno.swing.designer.properties.wrappers.GridBagConstraintsSourceCoder;
import dyno.swing.designer.properties.wrappers.InsetsWrapper;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class GridBagLayoutConstraints implements ConstraintsGroupModel, Constants {

    private Component component;
    private Container parent;
    private GridBagLayout layout;
    private TableCellRenderer anchorRenderer;
    private TableCellEditor anchorEditor;
    private SourceCoder anchorCoder;
    private TableCellRenderer fillRenderer;
    private TableCellEditor fillEditor;
    private SourceCoder fillCoder;
    private TableCellRenderer gridheightRenderer;
    private TableCellEditor gridheightEditor;
    private SourceCoder gridheightCoder;
    private TableCellRenderer gridwidthRenderer;
    private TableCellEditor gridwidthEditor;
    private SourceCoder gridwidthCoder;
    private TableCellRenderer gridxRenderer;
    private SourceCoder gridxCoder;
    private TableCellRenderer gridyRenderer;
    private SourceCoder gridyCoder;
    private TableCellEditor gridxEditor;
    private TableCellEditor gridyEditor;
    private TableCellRenderer insetsRenderer;
    private TableCellEditor insetsEditor;
    private TableCellRenderer ipadxRenderer;
    private TableCellEditor ipadxEditor;
    private TableCellRenderer ipadyRenderer;
    private TableCellEditor ipadyEditor;
    private TableCellRenderer weightxRenderer;
    private TableCellEditor weightxEditor;
    private TableCellRenderer weightyRenderer;
    private TableCellEditor weightyEditor;

    public GridBagLayoutConstraints(Container parent, GridBagLayout layout, Component comp) {
        this.parent = parent;
        this.layout = layout;
        this.component = comp;
        
        this.anchorRenderer = new EncoderCellRenderer(new ItemWrapper(new GridBagConstraintsAnchorItems()));
        this.anchorEditor = new PropertyCellEditor(new ItemCellEditor(new GridBagConstraintsAnchorItems()));
        this.anchorCoder = new ItemWrapper(new GridBagConstraintsAnchorItems());
        
        this.fillRenderer = new EncoderCellRenderer(new ItemWrapper(new GridBagConstraintsFillItems()));
        this.fillEditor = new PropertyCellEditor(new ItemCellEditor(new GridBagConstraintsFillItems()));
        this.fillCoder = new ItemWrapper(new GridBagConstraintsFillItems());
        
        SpinnerNumberModel model = new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1);

        this.gridwidthRenderer = new EncoderCellRenderer(new GridBagConstraintsDimWrapper());
        this.gridwidthEditor = new PropertyCellEditor(new SpinnerEditor(model));
        this.gridwidthCoder = new GridBagConstraintsDimWrapper();

        this.gridheightRenderer = new EncoderCellRenderer(new GridBagConstraintsDimWrapper());
        this.gridheightEditor = new PropertyCellEditor(new SpinnerEditor(model));
        this.gridheightCoder = new GridBagConstraintsDimWrapper();

        model = new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1);

        this.gridxRenderer = new EncoderCellRenderer(new GridBagConstraintsDimWrapper(true));
        this.gridxEditor = new PropertyCellEditor(new SpinnerEditor(model));
        this.gridxCoder = new GridBagConstraintsDimWrapper(true);

        this.gridyRenderer = new EncoderCellRenderer(new GridBagConstraintsDimWrapper(true));
        this.gridyEditor = new PropertyCellEditor(new SpinnerEditor(model));
        this.gridyCoder = new GridBagConstraintsDimWrapper(true);

        this.insetsRenderer = new InsetsCellRenderer();
        this.insetsEditor = new PropertyCellEditor(new InsetsEditor());

        this.ipadxRenderer = new DefaultTableCellRenderer();
        this.ipadxEditor = new PropertyCellEditor(new IntegerPropertyEditor());

        this.ipadyRenderer = new DefaultTableCellRenderer();
        this.ipadyEditor = new PropertyCellEditor(new IntegerPropertyEditor());

        this.weightxRenderer = new DefaultTableCellRenderer();
        this.weightxEditor = new PropertyCellEditor(new DoubleEditor());

        this.weightyRenderer = new DefaultTableCellRenderer();
        this.weightyEditor = new PropertyCellEditor(new DoubleEditor());
    }

    public String getGroupName() {
        return "Layout Constraints";
    }

    public int getRowCount() {
        return 11;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return anchorRenderer;
            case 1:
                return fillRenderer;
            case 2:
                return gridwidthRenderer;
            case 3:
                return gridheightRenderer;
            case 4:
                return gridxRenderer;
            case 5:
                return gridyRenderer;
            case 6:
                return insetsRenderer;
            case 7:
                return ipadxRenderer;
            case 8:
                return ipadyRenderer;
            case 9:
                return weightxRenderer;
            case 10:
                return weightyRenderer;
        }
        return new DefaultTableCellRenderer();
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return anchorEditor;
            case 1:
                return fillEditor;
            case 2:
                return gridwidthEditor;
            case 3:
                return gridheightEditor;
            case 4:
                return gridxEditor;
            case 5:
                return gridyEditor;
            case 6:
                return insetsEditor;
            case 7:
                return ipadxEditor;
            case 8:
                return ipadyEditor;
            case 9:
                return weightxEditor;
            case 10:
                return weightyEditor;
        }
        return new DefaultCellEditor(new JTextField());
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "anchor";
                case 1:
                    return "fill";
                case 2:
                    return "gridwidth";
                case 3:
                    return "gridheight";
                case 4:
                    return "gridx";
                case 5:
                    return "gridy";
                case 6:
                    return "insets";
                case 7:
                    return "ipadx";
                case 8:
                    return "ipady";
                case 9:
                    return "weightx";
                case 10:
                    return "weighty";
            }
        } else {
            GridBagConstraints constraints = layout.getConstraints(component);
            switch (row) {
                case 0:
                    return constraints.anchor;
                case 1:
                    return constraints.fill;
                case 2:
                    return constraints.gridwidth;
                case 3:
                    return constraints.gridheight;
                case 4:
                    return constraints.gridx;
                case 5:
                    return constraints.gridy;
                case 6:
                    return constraints.insets;
                case 7:
                    return constraints.ipadx;
                case 8:
                    return constraints.ipady;
                case 9:
                    return constraints.weightx;
                case 10:
                    return constraints.weighty;
            }
        }
        return null;
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 1) {
            GridBagConstraints constraints = layout.getConstraints(component);
            switch (row) {
                case 0:
                    constraints.anchor = ((Number) value).intValue();
                    break;
                case 1:
                    constraints.fill = ((Number) value).intValue();
                    break;
                case 2:
                    constraints.gridwidth = ((Number) value).intValue();
                    break;
                case 3:
                    constraints.gridheight = ((Number) value).intValue();
                    break;
                case 4:
                    constraints.gridx = ((Number) value).intValue();
                    break;
                case 5:
                    constraints.gridy = ((Number) value).intValue();
                    break;
                case 6:
                    constraints.insets = (Insets) value;
                    break;
                case 7:
                    constraints.ipadx = ((Number) value).intValue();
                    break;
                case 8:
                    constraints.ipady = ((Number) value).intValue();
                    break;
                case 9:
                    constraints.weightx = ((Number) value).doubleValue();
                    break;
                case 10:
                    constraints.weighty = ((Number) value).doubleValue();
                    break;
            }
            layout.setConstraints(component, constraints);
            layout.layoutContainer(parent);
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
        GridBagConstraints constraints=layout.getConstraints(component);
        String componentName=Util.getComponentName(component);
        String constraintsName=componentName+"_const";
        
        String constCode = "java.awt.GridBagConstraints "+constraintsName+" = "+coder.getJavaCode(constraints)+";\n";
        if(constraints.anchor != GridBagConstraints.CENTER)
        constCode += constraintsName+".anchor = "+anchorCoder.getJavaCode(constraints.anchor)+";\n";
        if(constraints.fill != GridBagConstraints.NONE)
        constCode += constraintsName+".fill = "+fillCoder.getJavaCode(constraints.fill)+";\n";
        if(constraints.gridx != GridBagConstraints.RELATIVE)
        constCode += constraintsName+".gridx = "+gridxCoder.getJavaCode(constraints.gridx)+";\n";
        if(constraints.gridy != GridBagConstraints.RELATIVE)
        constCode += constraintsName+".gridy = "+gridyCoder.getJavaCode(constraints.gridy)+";\n";
        if(constraints.gridwidth != 1)
        constCode += constraintsName+".gridwidth = "+gridwidthCoder.getJavaCode(constraints.gridwidth)+";\n";
        if(constraints.gridheight != 1)
        constCode += constraintsName+".gridheight = "+gridheightCoder.getJavaCode(constraints.gridheight)+";\n";
        if(constraints.ipadx != 0)
        constCode += constraintsName+".ipadx = "+constraints.ipadx+";\n";
        if(constraints.ipady != 0)
        constCode += constraintsName+".ipady = "+constraints.ipady+";\n";
        if(constraints.weightx > 0)
        constCode += constraintsName+".weightx = "+constraints.weightx+";\n";
        if(constraints.weighty > 0)
        constCode += constraintsName+".weighty = "+constraints.weighty+";\n";
        if(constraints.insets.top != 0 || constraints.insets.left != 0 || constraints.insets.bottom != 0 || constraints.insets.right != 0)
        constCode += constraintsName+".insets = "+insetsCoder.getJavaCode(constraints.insets)+";\n";
        
        return constCode+VAR_CONTAINER+".add("+Util.getGetName(componentName)+"(), "+constraintsName+");\n";
    }
    private static GridBagConstraintsSourceCoder coder=new GridBagConstraintsSourceCoder();
    private static InsetsWrapper insetsCoder=new InsetsWrapper();
}
