/*
 * JLabelAdapter.java
 *
 * Created on 2007年8月14日, 上午12:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JLabel;


/**
 *
 * @author William Chen
 */
public class JLabelAdapter extends DefaultComponentAdapter {
    /** Creates a new instance of JLabelAdapter */
    public JLabelAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    public Rectangle getEditorBounds(int x, int y) {
        JLabel label = (JLabel) component;
        String text = label.getText();

        return getTextEditorBounds(text, label);
    }
}
