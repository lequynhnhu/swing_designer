/*
 * BorderLayoutProperties.java
 *
 * Created on 2007-8-27, 0:58:44
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import java.awt.CardLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class CardLayoutProperties implements GroupModel {

    private DefaultTableCellRenderer renderer;
    private PropertyCellEditor editor;
    private CardLayout layout;

    public CardLayoutProperties(CardLayout layout) {
        this.layout = layout;
        renderer = new DefaultTableCellRenderer();
        editor = new PropertyCellEditor(new IntegerPropertyEditor());
    }

    public String getGroupName() {
        return "Card Layout";
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
            switch (row) {
                case 0:
                    return "hgap";
                default:
                    return "vgap";
            }
        } else {
            switch (row) {
                case 0:
                    return layout.getHgap();
                default:
                    return layout.getVgap();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            int v = 0;
            if (value != null) {
                v = ((Number) value).intValue();
            }
            switch (row) {
                case 0:
                    layout.setHgap(v);
                    return true;
                case 1:
                    layout.setVgap(v);
                    return true;
                default:
                    return false;
            }
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
        switch(row){
        case 0:
            return "hgap";
        case 1:
            return "vgap";
        default:
            return null;
        }
    }
}