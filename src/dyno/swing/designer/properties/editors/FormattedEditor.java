/*
 * FormattedEditor.java
 *
 * Created on 2007-8-15, 7:52:36
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.ValidationException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;


/**
 *
 * @author William Chen
 */
public class FormattedEditor extends AbstractPropertyEditor {

    private JPanel panel;
    private JFormattedTextField textField;
    private Format format;

    /** Creates a new instance of TextEditor */
    public FormattedEditor(Format format) {
        this.format = format;
        panel = new JPanel(new BorderLayout());
        textField = new JFormattedTextField(format);
        panel.add(textField, BorderLayout.CENTER);
        textField.setBorder(null);
        textField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                firePropertyChanged();
            }
        });
    }

    public void setValue(Object value) {
        textField.setValue(value);
    }

    public Object getValue() {
        try {
            textField.commitEdit();
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        return textField.getValue();
    }

    public Component getCustomEditor() {
        return panel;
    }

    @Override
    public void validateValue() throws ValidationException {
        try {
            format.parseObject(textField.getText());
        } catch (ParseException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }
}