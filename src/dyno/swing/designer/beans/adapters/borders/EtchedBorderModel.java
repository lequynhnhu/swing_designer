
/*
 * EtchedBorderModel.java
 *
 * Created on 2007-8-27, 23:19:28
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.properties.PropertyCellEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyCellEditor;
import dyno.swing.designer.properties.editors.ColorEditor;
import dyno.swing.designer.properties.editors.ItemCellEditor;
import dyno.swing.designer.properties.renderer.ColorCellRenderer;
import dyno.swing.designer.properties.renderer.EncoderCellRenderer;
import dyno.swing.designer.properties.items.EtchedBorderTypeItems;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class EtchedBorderModel extends AbstractBorderModel {

    private EtchedBorder border;
    private EncoderCellRenderer ebtRenderer;
    private PropertyCellEditor ebtEditor;
    private ColorCellRenderer colorRenderer;
    private AccessiblePropertyCellEditor colorEditor;

    public EtchedBorderModel(EtchedBorder border) {
        this.border = border;
        ebtRenderer = new EncoderCellRenderer(new ItemWrapper(new EtchedBorderTypeItems()));
        ebtEditor = new PropertyCellEditor(new ItemCellEditor(new EtchedBorderTypeItems()));
        colorRenderer = new ColorCellRenderer();
        colorEditor = new AccessiblePropertyCellEditor(new ColorEditor());
    }

    public Border getBorder() {
        return border;
    }

    public int getRowCount() {
        return 3;
    }

    public TableCellRenderer getRenderer(int row) {
        if (row == 0) {
            return ebtRenderer;
        } else {
            return colorRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        if (row == 0) {
            return ebtEditor;
        } else {
            return colorEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "etchType";
                case 1:
                    return "highlight";
                default:
                    return "shadow";
            }
        } else {
            switch (row) {
                case 0:
                    return border.getEtchType();
                case 1:
                    return border.getHighlightColor();
                default:
                    return border.getShadowColor();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            int v = border.getEtchType();
            Color hColor = border.getHighlightColor();
            Color sColor = border.getShadowColor();
            switch (row) {
                case 0:
                    if (value != null) {
                        v = ((Number)value).intValue();
                    }
                    break;
                case 1:
                    hColor = (Color) value;
                    break;
                default:
                    sColor = (Color) value;
                    break;
            }
            border = new EtchedBorder(v, hColor, sColor);
            return true;
        }
    }
}