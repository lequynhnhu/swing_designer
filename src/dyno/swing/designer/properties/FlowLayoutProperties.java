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
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class FlowLayoutProperties implements GroupModel {

    private DefaultTableCellRenderer renderer;
    private PropertyCellEditor editor;
    private BooleanRenderer booleanRenderer;
    private DefaultCellEditor booleanEditor;
    private FlowAlignmentRenderer alignmentRenderer;
    private FlowAlignmentEditor alignmentEditor;
    private FlowLayout layout;

    public FlowLayoutProperties(FlowLayout layout) {
        this.layout = layout;
        renderer = new DefaultTableCellRenderer();
        JFormattedTextField jftf = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jftf.setBorder(null);
        editor = new PropertyCellEditor(new IntegerPropertyEditor());
        booleanRenderer = new BooleanRenderer();
        booleanEditor = new DefaultCellEditor(new JCheckBox()) {

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);
                if (isSelected) {
                    editorComponent.setBackground(table.getSelectionBackground());
                    editorComponent.setForeground(table.getSelectionForeground());
                } else {
                    editorComponent.setBackground(table.getBackground());
                    editorComponent.setForeground(table.getForeground());
                }
                return editorComponent;
            }
        };
        alignmentRenderer = new FlowAlignmentRenderer();
        alignmentEditor = new FlowAlignmentEditor();
    }

    public String getGroupName() {
        return "Flow Layout";
    }

    public int getRowCount() {
        return 4;
    }

    public TableCellRenderer getRenderer(int row) {
        switch (row) {
            case 0:
                return renderer;
            case 1:
                return renderer;
            case 2:
                return booleanRenderer;
            default:
                return alignmentRenderer;
        }
    }

    public TableCellEditor getEditor(int row) {
        switch (row) {
            case 0:
                return editor;
            case 1:
                return editor;
            case 2:
                return booleanEditor;
            default:
                return alignmentEditor;
        }
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            switch (row) {
                case 0:
                    return "hgap";
                case 1:
                    return "vgap";
                case 2:
                    return "alignOnBaseline";
                default:
                    return "alignment";
            }
        } else {
            switch (row) {
                case 0:
                    return layout.getHgap();
                case 1:
                    return layout.getVgap();
                case 2:
                    return layout.getAlignOnBaseline();
                default:
                    return layout.getAlignment();
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            switch (row) {
                case 0:
                    int v = 0;
                    if (value != null) {
                        v = ((Number) value).intValue();
                    }
                    layout.setHgap(v);
                    return true;
                case 1:
                    v = 0;
                    if (value != null) {
                        v = ((Number) value).intValue();
                    }
                    layout.setVgap(v);
                    return true;
                case 2:
                    boolean b = false;
                    if (value != null) {
                        b = ((Boolean) value).booleanValue();
                    }
                    layout.setAlignOnBaseline(b);
                    return true;
                case 3:
                    int alignment = FlowLayout.LEFT;
                    if (value != null) {
                        alignment = ((Number) value).intValue();
                    }
                    layout.setAlignment(alignment);
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
        switch (row) {
            case 0:
                return "hgap";
            case 1:
                return "vgap";
            case 2:
                return "alignOnBaseline";
            case 3:
                return "alignment";
            default:
                return null;
        }
    }
}