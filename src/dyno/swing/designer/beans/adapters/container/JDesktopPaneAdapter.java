/*
 * JDesktopPaneAdapter.java
 *
 * Created on 2007-9-2, 21:36:42
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.models.AddingModel;
import dyno.swing.designer.beans.painters.DesktopPanePainter;
import dyno.swing.designer.properties.InternalFrameConstraints;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author William Chen
 */
public class JDesktopPaneAdapter extends JLayeredPaneAdapter {

    private JDesktopPane desktopPane;
    private HoverPainter painter;

    public JDesktopPaneAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
        desktopPane = (JDesktopPane) component;
        painter = new DesktopPanePainter(designer, desktopPane);
    }

    public void addNextComponent(Component dragged) {
    }

    public boolean acceptComponent(int x, int y) {
        AddingModel model = designer.getAddingModel();

        if (model != null) {
            //获取该组件所在的焦点容器
            Component bean = model.getBean();
            if (bean instanceof JInternalFrame) {
                Rectangle rect = Util.getRelativeBounds(component);
                int lx = x - designer.getOuterLeft() - rect.x - bean.getWidth() / 2;
                int ly = y - designer.getOuterTop() - rect.y - bean.getHeight() / 2;
                bean.setLocation(lx, ly);
                desktopPane.add(bean);
                bean.setVisible(true);
                return true;
            }
        }
        return false;
    }
    @Override
    public HoverPainter getPainter() {
        return painter;
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

    @Override
    public int getChildCount() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        int count = frames == null ? 0 : frames.length;
        return count;
    }

    @Override
    public Component getChild(int index) {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        return frames[index];
    }

    @Override
    public int getIndexOfChild(Component child) {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        for(int i=0;i<frames.length;i++){
            if(frames[i]==child)
                return i;
        }
        return -1;
    }

    public boolean canAddAfter(Component hovering) {
        return true;
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return new InternalFrameConstraints(desktopPane, bean);
    }

    @Override
    public void showComponent(Component child) {
        Component parent=child;
        while(!(parent instanceof JInternalFrame || parent == null))
            parent=parent.getParent();
        if(parent != null){
            ((JInternalFrame)parent).toFront();
        }
    }    
}