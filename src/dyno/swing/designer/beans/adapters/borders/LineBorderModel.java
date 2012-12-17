
/*
 * LineBorderModel.java
 *
 * Created on 2007-8-27, 23:40:39
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.properties.BooleanRenderer;
import dyno.swing.designer.properties.PropertyCellEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyCellEditor;
import dyno.swing.designer.properties.editors.BooleanEditor;
import dyno.swing.designer.properties.editors.ColorEditor;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import dyno.swing.designer.properties.renderer.ColorCellRenderer;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class LineBorderModel extends AbstractBorderModel {

    private LineBorder border;
    private DefaultTableCellRenderer thickRenderer;
    private PropertyCellEditor thickEditor;
    private ColorCellRenderer colorRenderer;
    private AccessiblePropertyCellEditor colorEditor;
    private BooleanRenderer booleanRenderer;
    private AccessiblePropertyCellEditor booleanEditor;

    public LineBorderModel(LineBorder border) {
        this.border = border;
        thickRenderer = new DefaultTableCellRenderer();
        thickEditor = new PropertyCellEditor(new IntegerPropertyEditor());
        colorRenderer = new ColorCellRenderer();
        colorEditor = new AccessiblePropertyCellEditor(new ColorEditor());
        booleanRenderer = new BooleanRenderer();
        booleanEditor = new AccessiblePropertyCellEditor(new BooleanEditor());
    }

    public Border getBorder() {
        return border;
    }

    public int getRowCount() {
        return 3;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return thickRenderer;
            case 1:
                return colorRenderer;
            default:
                return booleanRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return thickEditor;
            case 1:
                return colorEditor;
            default:
                return booleanEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "thickness";
                case 1:
                    return "lineColor";
                default:
                    return "roundedCorners";
            }
        } else {
            switch (row) {
                case 0:
                    return border.getThickness();
                case 1:
                    return border.getLineColor();
                default:
                    return border.getRoundedCorners();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            int thickness = border.getThickness();
            Color lineColor = border.getLineColor();
            boolean roundedCorners = border.getRoundedCorners();
            switch (row) {
                case 0:
                    if (value != null) {
                        thickness = ((Number) value).intValue();
                    }
                    break;
                case 1:
                    lineColor = (Color) value;
                    break;
                default:
                    if (value != null) {
                        roundedCorners = (Boolean) value;
                    }
                    break;
            }
            border = new LineBorder(lineColor, thickness, roundedCorners);
            return true;
        }
    }
}