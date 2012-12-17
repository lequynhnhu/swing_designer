/*
 * JTextAreaEditor.java
 *
 * Created on 2007年8月16日, 上午12:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.editors;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 *
 * @author William Chen
 */
public class JScrollPaneTextArea extends JScrollPane {

    private JTextArea textArea;

    /** Creates a new instance of JTextAreaEditor */
    public JScrollPaneTextArea() {
        textArea = new JTextArea();
        getViewport().setView(textArea);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void selectAll() {
        textArea.selectAll();
    }

    public String getText() {
        return textArea.getText();
    }

    @Override
    public void requestFocus() {
        textArea.requestFocus();
    }
}