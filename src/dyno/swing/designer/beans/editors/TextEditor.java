/*
 * TextEditor.java
 *
 * Created on 2007年8月13日, 下午2:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.editors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;


/**
 *
 * @author William Chen
 */
public class TextEditor extends AbstractDesignerEditor {

    private JTextField editor;

    /** Creates a new instance of TextEditor */
    public TextEditor() {
        editor = new JTextField();
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
        editor.setText((value == null) ? "" : (String) value);
        editor.selectAll();
    }

    public Object getValue() {
        return editor.getText();
    }
}