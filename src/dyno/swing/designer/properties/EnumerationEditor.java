/*
 * EnumerationEditor.java
 *
 * Created on 2007-8-26, 22:09:09
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.items.ItemProvider;
import dyno.swing.designer.properties.types.Item;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author William Chen
 */
public class EnumerationEditor extends AbstractCellEditor implements TableCellEditor {

    protected JComponent editorComponent;
    protected EditorDelegate delegate;
    protected int clickCountToStart = 1;
    public EnumerationEditor(ItemProvider provider){
        this(provider.getItems());
    }
    public EnumerationEditor(Item[]items){
        DefaultComboBoxModel model=new DefaultComboBoxModel();
        for(Item item:items)
            model.addElement(item);
        init(new JComboBox(model));
    }
    public EnumerationEditor(Iterable<Item>items){
        DefaultComboBoxModel model=new DefaultComboBoxModel();
        for(Item item:items)
            model.addElement(item);
        init(new JComboBox(model));
    }
    public EnumerationEditor(Enumeration<Item>items){
        DefaultComboBoxModel model=new DefaultComboBoxModel();
        while(items.hasMoreElements()){
            model.addElement(items.nextElement());
        }
        init(new JComboBox(model));
    }
    public EnumerationEditor(JComboBox comboBox) {
        init(comboBox);
    }
    private void init(final JComboBox comboBox){
        editorComponent = comboBox;
        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        delegate = new EditorDelegate() {

            public void setValue(Object value) {
                Item item=new Item(null, value);
                comboBox.setSelectedItem(item);
            }

            public Object getCellEditorValue() {
                Item item = (Item) comboBox.getSelectedItem();
                return item.getValue();
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    MouseEvent e = (MouseEvent) anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }

            public boolean stopCellEditing() {
                if (comboBox.isEditable()) {
                    comboBox.actionPerformed(new ActionEvent(EnumerationEditor.this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };
        ((JComponent)comboBox.getEditor().getEditorComponent()).setBorder(null);
        editorComponent.setBorder(null);
        comboBox.addActionListener(delegate);
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

    protected class EditorDelegate implements ActionListener, ItemListener, Serializable {

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
            EnumerationEditor.this.stopCellEditing();
        }

        public void itemStateChanged(ItemEvent e) {
            EnumerationEditor.this.stopCellEditing();
        }
    }
}