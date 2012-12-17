/*
 * JLayeredPaneAdapter.java
 * 
 * Created on 2007-9-3, 22:17:37
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.properties.LayeredPaneConstraints;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLayeredPane;

/**
 *
 * @author William Chen
 */
public class JLayeredPaneAdapter extends AbstractContainerAdapter{
    public JLayeredPaneAdapter(SwingDesigner designer, Component component){
        super(designer, component);
    }
    public void addNextComponent(Component dragged) {
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return new LayeredPaneConstraints((JLayeredPane)component, bean);
    }

    public void addBefore(Component target, Component added) {
    }

    public void addAfter(Component target, Component added) {
    }

    public boolean canAcceptMoreComponent() {
        return true;
    }

    public boolean canAddBefore(Component hovering) {
        return true;
    }

    public boolean canAddAfter(Component hovering) {
        return true;
    }
    public void paintComponentMascot(Graphics g) {
        super.paintComponentMascot(g);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(0, 0, component.getWidth() - 1, component.getHeight() - 1);
    }
}
