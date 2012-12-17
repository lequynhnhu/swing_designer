/*
 * AbstractButtonAdapter.java
 *
 * Created on 2007��8��14��, ����1:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.AbstractButton;


/**
 *
 * @author William Chen
 */
public class AbstractButtonAdapter extends DefaultComponentAdapter {
    /** Creates a new instance of AbstractButtonAdapter */
    public AbstractButtonAdapter(SwingDesigner designer, Component button) {
        super(designer, button);
    }

    public Rectangle getEditorBounds(int x, int y) {
        AbstractButton button = (AbstractButton) component;
        String text = button.getText();

        return getTextEditorBounds(text, component);
    }
}
