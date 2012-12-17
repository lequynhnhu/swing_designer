/*
 * JScrollPaneAdapter.java
 *
 * Created on 2007年5月4日, 上午2:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SpecialContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.ScrollPanePainter;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JScrollPane;
import javax.swing.JViewport;


/**
 *
 * @author William Chen
 */
public class JScrollPaneAdapter extends AbstractContainerAdapter implements SpecialContainerAdapter {

    /** Creates a new instance of JScrollPaneAdapter */
    public JScrollPaneAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    public HoverPainter getPainter() {
        return new ScrollPanePainter(designer, (Container) component);
    }

    protected boolean addBean(Container container, Component bean, int x, int y) {
        if (isAcceptable(container)) {
            JScrollPane scrollPane = (JScrollPane) container;
            if (isScrollPaneComponent(bean)) {
                JScrollPane sp = (JScrollPane) bean;
                JViewport view = sp.getViewport();
                Component viewComponent = view.getView();
                scrollPane.setViewportView(viewComponent);
                viewComponent.addNotify();
            } else {
                scrollPane.setViewportView(bean);
                bean.addNotify();
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isScrollPaneComponent(Component bean) {
        if (bean instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) bean;
            JViewport view = sp.getViewport();
            if(view==null)
                return false;
            else{
                Component viewComponent=view.getView();
                return Util.isDesigning(viewComponent);
            }
        } else {
            return false;
        }
    }

    private static boolean isAcceptable(Container container) {
        JScrollPane scrollPane = (JScrollPane) container;

        return scrollPane.getViewport().getView() == null;
    }

    public int getIndexOfChild(Component child) {
        JScrollPane scrollPane = (JScrollPane) component;
        Component component = scrollPane.getViewport().getView();

        if (component == child) {
            return 0;
        }

        return -1;
    }

    public int getChildCount() {
        JScrollPane scrollPane = (JScrollPane) component;
        Component component = scrollPane.getViewport().getView();

        if (component == null) {
            return 0;
        }

        if (!Util.isDesigning(component)) {
            return 0;
        }

        return 1;
    }

    public Component getChild(int index) {
        if ((index < 0) || (index > 1)) {
            return null;
        }

        JScrollPane scrollPane = (JScrollPane) component;
        Component component = scrollPane.getViewport().getView();

        return component;
    }

    public void addNextComponent(Component dragged) {
        JScrollPane sp = (JScrollPane) component;
        sp.getViewport().setView(dragged);
    }

    public void addBefore(Component target, Component added) {
        ((JScrollPane) component).setViewportView(added);
    }

    public void addAfter(Component target, Component added) {
        ((JScrollPane) component).setViewportView(added);
    }

    public boolean canAcceptMoreComponent() {
        JScrollPane sp = (JScrollPane) component;
        Component comp = sp.getViewport().getView();

        return comp == null || !Util.isDesigning(comp);
    }

    public boolean canAddBefore(Component hovering) {
        return canAcceptMoreComponent();
    }

    public boolean canAddAfter(Component hovering) {
        return canAcceptMoreComponent();
    }

    @Override
    public void removeComponent(Component bean) {
        JScrollPane jsp = (JScrollPane) component;
        jsp.getViewport().setView(null);
        jsp.setColumnHeader(null);
        jsp.setRowHeader(null);
        bean.removeNotify();
    }

    @Override
    public String getAddComponentCode(Component bean) {
        String beanName=Util.getComponentName(bean);
        return Constants.VAR_CONTAINER+".setViewportView("+Util.getGetName(beanName)+"());\n";
    }    
}