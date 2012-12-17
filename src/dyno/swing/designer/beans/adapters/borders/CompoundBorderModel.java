
/*
 * CompoundBorderModel.java
 *
 * Created on 2007-8-27, 23:01:19
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyCellEditor;
import dyno.swing.designer.properties.editors.BorderEditor;
import dyno.swing.designer.properties.renderer.BorderCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class CompoundBorderModel extends AbstractBorderModel  {

    private CompoundBorder border;
    private BorderCellRenderer renderer;
    private AccessiblePropertyCellEditor editor;

    public CompoundBorderModel(CompoundBorder b) {
        border = b;
        renderer = new BorderCellRenderer();
        editor = new AccessiblePropertyCellEditor(new BorderEditor());
    }

    public Border getBorder() {
        return border;
    }

    public int getRowCount() {
        return 2;
    }

    public TableCellRenderer getRenderer(int row) {
        return renderer;
    }

    public TableCellEditor getEditor(int row) {
        return editor;
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            if (row == 0) {
                return "outsideBorder";
            } else {
                return "insideBorder";
            }
        } else {
            if (row == 0) {
                return border.getOutsideBorder();
            } else {
                return border.getInsideBorder();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            Border inner = border.getInsideBorder();
            Border outer = border.getOutsideBorder();
            if (row == 0) {
                outer = (Border) value;
            } else {
                inner = (Border) value;
            }
            border = new CompoundBorder(outer, inner);
            return true;
        }
    }
}