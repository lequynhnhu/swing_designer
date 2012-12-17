/*
 * SpinnerEditor.java
 *
 * Created on 2007-8-15, 8:05:53
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.ValidationException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author William Chen
 */
public class SpinnerEditor extends AbstractPropertyEditor {
    private JPanel panel;
    private JSpinner spinner;

    public SpinnerEditor() {
        this(null);
    }

    /** Creates a new instance of TextEditor */
    public SpinnerEditor(SpinnerModel model) {
        panel = new JPanel(new BorderLayout());

        if (model != null) {
            spinner = new JSpinner(model);
        } else {
            spinner = new JSpinner();
        }

        panel.add(spinner, BorderLayout.CENTER);
        spinner.setBorder(null);
    }

    public void setValue(Object value) {
        spinner.setValue(value);
    }

    public Object getValue() {
        return spinner.getValue();
    }

    public Component getCustomEditor() {
        return panel;
    }

    @Override
    public void validateValue() throws ValidationException {
    }
}
