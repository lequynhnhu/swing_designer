/*
 * AstractLayoutAdapter.java
 *
 * Created on 2007年5月3日, 上午12:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.LayoutAdapter;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public abstract class AbstractLayoutAdapter implements LayoutAdapter {
    protected Container container;
    protected SwingDesigner designer;
    protected LayoutManager layout;
    /** Creates a new instance of AstractLayoutAdapter */
    public AbstractLayoutAdapter(SwingDesigner designer, Container container) {
        this.designer=designer;
        this.container=container;
        this.layout = container.getLayout();
    }
    public Painter getAnchorPainter(){
        return null;
    }
    public void showComponent(Component child) {
    }

    public void addNextComponent(Component dragged) {
        container.add(dragged);
        Util.layoutContainer(container);
    }

    public void addBefore(Component target, Component added) {
        int index = Util.indexOfComponent(container, target);

        if (index == -1) {
            container.add(added, 0);
        } else {
            container.add(added, index);
        }

        Util.layoutContainer(container);
    }

    public void addAfter(Component target, Component added) {
        int index = Util.indexOfComponent(container, target);

        if (index == -1) {
            container.add(added);
        } else {
            index++;

            if (index >= container.getComponentCount()) {
                container.add(added);
            } else {
                container.add(added, index);
            }
        }

        Util.layoutContainer(container);
    }

    public boolean canAcceptMoreComponent() {
        return true;
    }

    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return null;
    }

    public GroupModel getLayoutProperties() {
        return null;
    }        
}
