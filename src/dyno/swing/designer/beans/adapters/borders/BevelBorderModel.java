
/*
 * BevelBorderModel.java
 *
 * Created on 2007-8-27, 21:47:22
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
import dyno.swing.designer.properties.items.BevelBorderTypeItems;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class BevelBorderModel extends AbstractBorderModel {

    protected BevelBorder border;
    protected EncoderCellRenderer bbtRenderer;
    protected PropertyCellEditor bbtEditor;
    protected ColorCellRenderer colorRenderer;
    protected AccessiblePropertyCellEditor colorEditor;

    public BevelBorderModel(BevelBorder border) {
        this.border = border;
        bbtRenderer = new EncoderCellRenderer(new ItemWrapper(new BevelBorderTypeItems()));
        bbtEditor = new PropertyCellEditor(new ItemCellEditor(new BevelBorderTypeItems()));
        colorRenderer = new ColorCellRenderer();
        colorEditor = new AccessiblePropertyCellEditor(new ColorEditor());
    }

    public Border getBorder() {
        return border;
    }

    public int getRowCount() {
        return 5;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return bbtRenderer;
            default:
                return colorRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return bbtEditor;
            default:
                return colorEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "bevelType";
                case 1:
                    return "highlightOuter";
                case 2:
                    return "highlightInner";
                case 3:
                    return "shadowInner";
                case 4:
                    return "shadowOuter";
                default:
                    return null;
            }
        } else {
            switch (row) {
                case 0:
                    return border.getBevelType();
                case 1:
                    return border.getHighlightOuterColor();
                case 2:
                    return border.getHighlightInnerColor();
                case 3:
                    return border.getShadowInnerColor();
                case 4:
                    return border.getShadowOuterColor();
                default:
                    return null;
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            switch (row) {
                case 0:
                    int v = BevelBorder.RAISED;
                    if (value != null) {
                        v = ((Number) value).intValue();
                    }
                    border = new BevelBorder(v, border.getHighlightOuterColor(), border.getHighlightInnerColor(), border.getShadowOuterColor(), border.getShadowInnerColor());
                    return true;
                case 1:
                    Color c = (Color) value;
                    border = new BevelBorder(border.getBevelType(), c, border.getHighlightInnerColor(), border.getShadowOuterColor(), border.getShadowInnerColor());
                    return true;
                case 2:
                    c = (Color) value;
                    border = new BevelBorder(border.getBevelType(), border.getHighlightOuterColor(), c, border.getShadowOuterColor(), border.getShadowInnerColor());
                    return true;
                case 3:
                    c = (Color) value;
                    border = new BevelBorder(border.getBevelType(), border.getHighlightOuterColor(), border.getHighlightInnerColor(), border.getShadowOuterColor(), c);
                    return true;
                case 4:
                    c = (Color) value;
                    border = new BevelBorder(border.getBevelType(), border.getHighlightOuterColor(), border.getHighlightInnerColor(), c, border.getShadowInnerColor());
                    return true;
                default:
                    return false;
            }
        }
    }
}