/*
 * PropertyCellEditor.java
 *
 * Created on 2007��8��13��, ����9:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.properties.editors.ExtendedPropertyEditor;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.io.Serializable;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


/**
 *
 * @author William Chen
 */
public class PropertyCellEditor extends AbstractCellEditor implements TableCellEditor, PropertyChangeListener, PropertyContext, Serializable {

    private PropertyEditor editor;
    protected Component editorComponent;
    protected int clickCountToStart;
    protected Object value;
    private Object bean;
    private SwingDesigner designer;
    private PropertyDescriptor property;

    public PropertyCellEditor(PropertyEditor propertyEditor) {
        this(null, propertyEditor);
    }

    public PropertyCellEditor(PropertyDescriptor property, PropertyEditor propertyEditor) {
        this.property = property;
        this.editor = propertyEditor;
        this.editorComponent = this.editor.getCustomEditor();
        this.clickCountToStart = 1;
        editor.addPropertyChangeListener(this);
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Object getCellEditorValue() {
        return editor.getValue();
    }

    public Component getComponent() {
        return editorComponent;
    }

    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    public int getClickCountToStart() {
        return clickCountToStart;
    }

    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
        }

        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    public boolean stopCellEditing() {
        if (editor instanceof ExtendedPropertyEditor) {
            try {
                ((ExtendedPropertyEditor) editor).validateValue();
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(editorComponent, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                editorComponent.requestFocus();
                return false;
            }
        }
        fireEditingStopped();
        return true;
    }

    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editorComponent.setForeground(table.getSelectionForeground());
        editorComponent.setBackground(table.getSelectionBackground());
        editorComponent.setFont(table.getFont());
        editor.setValue(value);

        return editorComponent;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        stopCellEditing();
    }

    public void setSwingDesigner(SwingDesigner designer) {
        this.designer = designer;
    }
}