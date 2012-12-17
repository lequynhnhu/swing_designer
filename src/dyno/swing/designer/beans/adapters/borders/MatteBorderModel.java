
/*
 * MatteBorderModel.java
 *
 * Created on 2007-8-28, 0:01:25
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.properties.PropertyCellEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyCellEditor;
import dyno.swing.designer.properties.editors.ColorEditor;
import dyno.swing.designer.properties.editors.IconEditor;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import dyno.swing.designer.properties.renderer.ColorCellRenderer;
import dyno.swing.designer.properties.renderer.IconCellRenderer;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class MatteBorderModel extends AbstractBorderModel {

    private MatteBorder border;
    private DefaultTableCellRenderer intRenderer;
    private PropertyCellEditor intEditor;
    private ColorCellRenderer colorRenderer;
    private AccessiblePropertyCellEditor colorEditor;
    private AccessiblePropertyCellEditor iconEditor;
    private IconCellRenderer iconRenderer;

    public MatteBorderModel(MatteBorder border) {
        this.border = border;
        intRenderer = new DefaultTableCellRenderer();
        intEditor = new PropertyCellEditor(new IntegerPropertyEditor());
        colorRenderer = new ColorCellRenderer();
        colorEditor = new AccessiblePropertyCellEditor(new ColorEditor());
        iconEditor = new AccessiblePropertyCellEditor(new IconEditor());
        iconRenderer = new IconCellRenderer();
    }

    public Border getBorder() {
        return border;
    }

    public int getRowCount() {
        return 6;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return intRenderer;
            case 1:
                return intRenderer;
            case 2:
                return intRenderer;
            case 3:
                return intRenderer;
            case 4:
                return colorRenderer;
            default:
                return iconRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return intEditor;
            case 1:
                return intEditor;
            case 2:
                return intEditor;
            case 3:
                return intEditor;
            case 4:
                return colorEditor;
            default:
                return iconEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "top";
                case 1:
                    return "left";
                case 2:
                    return "bottom";
                case 3:
                    return "right";
                case 4:
                    return "color";
                default:
                    return "titleIcon";
            }
        } else {
            switch (row) {
                case 0:
                    return border.getBorderInsets().top;
                case 1:
                    return border.getBorderInsets().left;
                case 2:
                    return border.getBorderInsets().bottom;
                case 3:
                    return border.getBorderInsets().right;
                case 4:
                    return border.getMatteColor();
                default:
                    return border.getTileIcon();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            int top = border.getBorderInsets().top;
            int left = border.getBorderInsets().left;
            int bottom = border.getBorderInsets().bottom;
            int right = border.getBorderInsets().right;
            Color matteColor = border.getMatteColor();
            Icon titleIcon = border.getTileIcon();
            switch (row) {
                case 0:
                    if (value != null) {
                        top = ((Number) value).intValue();
                    }
                    break;
                case 1:
                    if (value != null) {
                        left = ((Number) value).intValue();
                    }
                    break;
                case 2:
                    if (value != null) {
                        bottom = ((Number) value).intValue();
                    }
                    break;
                case 3:
                    if (value != null) {
                        right = ((Number) value).intValue();
                    }
                    break;
                case 4:
                        matteColor = (Color) value;
                    break;
                default:
                        titleIcon = (Icon) value;
                    break;
            }
            if (titleIcon == null) {
                border = new MatteBorder(top, left, bottom, right, matteColor);
            } else {
                border = new MatteBorder(titleIcon);
            }
            return true;
        }
    }
}