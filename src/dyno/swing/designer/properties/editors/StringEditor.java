/*
 * TextEditor.java
 *
 * Created on 2007年8月13日, 上午9:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.ValidationException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author William Chen
 */
public class StringEditor extends AbstractPropertyEditor {
    private JPanel panel;
    private JTextField textField;

    /** Creates a new instance of TextEditor */
    public StringEditor() {
        panel = new JPanel(new BorderLayout());
        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);
        textField.setBorder(null);
        textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    firePropertyChanged();
                }
            });
    }

    public void setValue(Object value) {
        textField.setText((String) value);
    }

    public Object getValue() {
        return textField.getText();
    }

    public Component getCustomEditor() {
        return panel;
    }

    @Override
    public void validateValue() throws ValidationException {
    }
}
