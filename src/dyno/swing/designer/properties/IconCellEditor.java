/*
 * IconCellEditor.java
 *
 * Created on 2007-8-26, 23:54:34
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.editors.accessibles.AccessibleIconEditor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author William Chen
 */
public class IconCellEditor extends AbstractCellEditor implements TableCellEditor {
    protected JComponent editorComponent;
    protected EditorDelegate delegate;
    protected int clickCountToStart = 1;
    public IconCellEditor() {
        final AccessibleIconEditor iconEditor = new AccessibleIconEditor();
        editorComponent = iconEditor;
        delegate = new EditorDelegate() {

            public void setValue(Object value) {
                iconEditor.setValue(value);
            }

            public Object getCellEditorValue() {
                return iconEditor.getValue();
            }
        };
        iconEditor.addChangeListener(delegate);
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

    public Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }

    public boolean isCellEditable(EventObject anEvent) {
        return delegate.isCellEditable(anEvent);
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return delegate.shouldSelectCell(anEvent);
    }

    public boolean stopCellEditing() {
        return delegate.stopCellEditing();
    }

    public void cancelCellEditing() {
        delegate.cancelCellEditing();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        delegate.setValue(value);
        return editorComponent;
    }

    protected class EditorDelegate implements ActionListener, ItemListener, ChangeListener, Serializable {

        protected Object value;

        public Object getCellEditorValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
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

        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

        public boolean stopCellEditing() {
            fireEditingStopped();
            return true;
        }

        public void cancelCellEditing() {
            fireEditingCanceled();
        }

        public void actionPerformed(ActionEvent e) {
            IconCellEditor.this.stopCellEditing();
        }

        public void itemStateChanged(ItemEvent e) {
            IconCellEditor.this.stopCellEditing();
        }

        public void stateChanged(ChangeEvent e) {
            IconCellEditor.this.stopCellEditing();
        }
    }
}