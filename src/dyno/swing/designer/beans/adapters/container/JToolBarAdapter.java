/*
 * JToolBarAdapter.java
 *
 * Created on August 3, 2007, 1:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.SpecialContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JToolBar;


/**
 *
 * @author William Chen
 */
public class JToolBarAdapter extends AbstractContainerAdapter
    implements SpecialContainerAdapter {
    public JToolBarAdapter(SwingDesigner designer, Component component){
        super(designer, component);
    }
    @Override
    public void paintComponentMascot(Graphics g) {
        super.paintComponentMascot(g);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(0, 0, component.getWidth() - 1, component.getHeight() - 1);
    }

    public int getIndexOfChild(Component child) {
        JToolBar tb = (JToolBar) component;

        return tb.getComponentIndex(child);
    }

    public int getChildCount() {
        JToolBar tb = (JToolBar) component;

        return tb.getComponentCount();
    }

    public Component getChild(int index) {
        JToolBar tb = (JToolBar) component;

        return tb.getComponentAtIndex(index);
    }

    public void addNextComponent(Component dragged) {
        JToolBar tb = (JToolBar) component;
        tb.add(dragged);
    }

    public void addBefore(Component target, Component added) {
        JToolBar tb = (JToolBar) component;
        tb.add(added);
    }

    public void addAfter(Component target, Component added) {
        JToolBar tb = (JToolBar) component;
        tb.add(added);
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
}
