/*
 * TextAreaEditor.java
 *
 * Created on 2007年8月15日, 下午11:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.editors;

import java.awt.Component;



/**
 *
 * @author William Chen
 */
public class TextAreaEditor extends AbstractDesignerEditor {

    private JScrollPaneTextArea editor;

    /** Creates a new instance of TextEditor */
    public TextAreaEditor() {
        editor = new JScrollPaneTextArea();
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