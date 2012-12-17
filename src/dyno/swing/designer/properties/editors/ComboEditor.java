/*
 * ComboEditor.java
 *
 * Created on 2007-8-15, 8:17:19
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 *
 * @author William Chen
 */
public class ComboEditor extends AbstractPropertyEditor {

    protected JComboBox comboBox;

    public ComboEditor(Object[] items) {
        this(new JComboBox(items));
    }

    public ComboEditor(Vector items) {
        this(new JComboBox(items));
    }

    public ComboEditor(ComboBoxModel model) {
        this(new JComboBox(model));
    }

    /** Creates a new instance of TextEditor */
    public ComboEditor(JComboBox combo) {
        comboBox = combo;
        comboBox.setEditable(false);
        comboBox.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        firePropertyChanged();
                    }
                });
        ((JComponent) comboBox.getEditor().getEditorComponent()).setBorder(null);
        comboBox.setBorder(null);
    }

    public void setValue(Object value) {
        comboBox.setSelectedItem(value);
    }

    public Object getValue() {
        return comboBox.getSelectedItem();
    }

    public Component getCustomEditor() {
        return comboBox;
    }

    @Override
    public void validateValue() throws ValidationException {
    }
}
