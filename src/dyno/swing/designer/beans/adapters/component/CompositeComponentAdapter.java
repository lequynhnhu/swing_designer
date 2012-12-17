/*
 * JTableAdapter.java
 *
 * Created on 2007年5月4日, 上午12:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.component;


import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import java.awt.Graphics;


/**
 *
 * @author William Chen
 */
public class CompositeComponentAdapter extends AbstractComponentAdapter {
    /** Creates a new instance of JTableAdapter */
    public CompositeComponentAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    @Override
    public void paintComponentMascot(Graphics g) {
        super.paintComponentMascot(g);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(0, 0, component.getWidth() - 1, component.getHeight() - 1);
    }
}
