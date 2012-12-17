
/*
 * TitledBorderModel.java
 *
 * Created on 2007-8-28, 0:51:41
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.properties.PropertyCellEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyCellEditor;
import dyno.swing.designer.properties.editors.BorderEditor;
import dyno.swing.designer.properties.editors.ColorEditor;
import dyno.swing.designer.properties.editors.FontEditor;
import dyno.swing.designer.properties.editors.ItemCellEditor;
import dyno.swing.designer.properties.items.TitleJustificationItems;
import dyno.swing.designer.properties.items.TitlePositionItems;
import dyno.swing.designer.properties.renderer.BorderCellRenderer;
import dyno.swing.designer.properties.renderer.ColorCellRenderer;
import dyno.swing.designer.properties.renderer.FontCellRenderer;
import dyno.swing.designer.properties.renderer.EncoderCellRenderer;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import java.awt.Color;
import java.awt.Font;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class TitledBorderModel extends AbstractBorderModel {

    private TitledBorder titleBorder;
    private DefaultTableCellRenderer titleRenderer;
    private DefaultCellEditor titleEditor;
    private BorderCellRenderer borderRenderer;
    private AccessiblePropertyCellEditor borderEditor;
    private EncoderCellRenderer titlePositionRenderer;
    private PropertyCellEditor titlePositionEditor;
    private EncoderCellRenderer titleJustificationRenderer;
    private PropertyCellEditor titleJustificationEditor;
    private FontCellRenderer titleFontRenderer;
    private AccessiblePropertyCellEditor titleFontEditor;
    private ColorCellRenderer titleColorRenderer;
    private AccessiblePropertyCellEditor titleColorEditor;

    public TitledBorderModel(TitledBorder b) {
        titleBorder = b;
        titleRenderer = new DefaultTableCellRenderer();
        JTextField text = new JTextField();
        text.setBorder(null);
        titleEditor = new DefaultCellEditor(text);
        borderRenderer = new BorderCellRenderer();
        borderEditor = new AccessiblePropertyCellEditor(new BorderEditor());
        titlePositionRenderer = new EncoderCellRenderer(new ItemWrapper(new TitlePositionItems()));
        titlePositionEditor = new PropertyCellEditor(new ItemCellEditor(new TitlePositionItems()));
        titleJustificationRenderer = new EncoderCellRenderer(new ItemWrapper(new TitleJustificationItems()));
        titleJustificationEditor = new PropertyCellEditor(new ItemCellEditor(new TitleJustificationItems()));
        titleFontRenderer = new FontCellRenderer();
        titleFontEditor = new AccessiblePropertyCellEditor(new FontEditor());
        titleColorRenderer = new ColorCellRenderer();
        titleColorEditor = new AccessiblePropertyCellEditor(new ColorEditor());
    }

    public Border getBorder() {
        return titleBorder;
    }

    public int getRowCount() {
        return 6;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return titleRenderer;
            case 1:
                return borderRenderer;
            case 2:
                return titlePositionRenderer;
            case 3:
                return titleJustificationRenderer;
            case 4:
                return titleFontRenderer;
            case 5:
                return titleColorRenderer;
            default:
                return null;
        }
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return titleEditor;
            case 1:
                return borderEditor;
            case 2:
                return titlePositionEditor;
            case 3:
                return titleJustificationEditor;
            case 4:
                return titleFontEditor;
            case 5:
                return titleColorEditor;
            default:
                return null;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "title";
                case 1:
                    return "border";
                case 2:
                    return "titlePosition";
                case 3:
                    return "titleJustification";
                case 4:
                    return "titleFont";
                case 5:
                    return "titleColor";
                default:
                    return null;
            }
        } else {
            switch (row) {
                case 0:
                    return titleBorder.getTitle();
                case 1:
                    return titleBorder.getBorder();
                case 2:
                    return titleBorder.getTitlePosition();
                case 3:
                    return titleBorder.getTitleJustification();
                case 4:
                    return titleBorder.getTitleFont();
                case 5:
                    return titleBorder.getTitleColor();
                default:
                    return null;
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            String t = titleBorder.getTitle();
            Border b = titleBorder.getBorder();
            int tp = titleBorder.getTitlePosition();
            int tj = titleBorder.getTitleJustification();
            Font tf = titleBorder.getTitleFont();
            Color tc = titleBorder.getTitleColor();
            switch (row) {
                case 0:
                    t = (String) value;
                    break;
                case 1:
                    b = (Border) value;
                    break;
                case 2:
                    if (value != null) {
                        tp = (Integer) value;
                    }
                    break;
                case 3:
                    if (value != null) {
                        tj = (Integer) value;
                    }
                    break;
                case 4:
                    tf = (Font) value;
                    break;
                case 5:
                    tc = (Color) value;
                    break;
            }
            titleBorder = new TitledBorder(b, t, tj, tp, tf, tc);
            return true;
        }
    }
}