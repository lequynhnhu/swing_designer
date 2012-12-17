/*
 * IntegerEditor.java
 *
 * Created on 2007-8-17, 19:27:54
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.editors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;

/**
 *
 * @author William Chen
 */
public class IntegerDesignerEditor extends AbstractDesignerEditor {

    private JFormattedTextField editor;

    /** Creates a new instance of TextEditor */
    public IntegerDesignerEditor() {
        editor = new JFormattedTextField(NumberFormat.getIntegerInstance());
        editor.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireStateChanged();
            }
        });
    }

    public Component getEditorComponent() {
        return editor;
    }

    public void setValue(Object value) {
        editor.setValue(value);
        editor.selectAll();
    }

    public Object getValue() {
        Object v = editor.getValue();
        if (v == null) {
            return 0;
        }
        return ((Number)v).intValue();
    }
}