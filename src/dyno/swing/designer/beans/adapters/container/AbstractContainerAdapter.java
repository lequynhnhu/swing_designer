/*
 * AbstractContainerAdapter.java
 *
 * Created on 2007年8月11日, 下午9:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.adapters.component.AbstractComponentAdapter;
import dyno.swing.designer.beans.actions.AbsoluteLayoutAction;
import dyno.swing.designer.beans.actions.BorderLayoutAction;
import dyno.swing.designer.beans.actions.BoxLayoutAction;
import dyno.swing.designer.beans.actions.CardLayoutAction;
import dyno.swing.designer.beans.actions.FlowLayoutAction;
import dyno.swing.designer.beans.actions.GridBagLayoutAction;
import dyno.swing.designer.beans.actions.GridLayoutAction;
import dyno.swing.designer.beans.actions.NullLayoutAction;
import dyno.swing.designer.beans.models.AddingModel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;


/**
 *
 * @author William Chen
 */
public abstract class AbstractContainerAdapter extends AbstractComponentAdapter implements ContainerAdapter, Constants {

    /** Creates a new instance of AbstractContainerAdapter */
    public AbstractContainerAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    @Override
    public void initialize() {
        super.initialize();
        component.setPreferredSize(getInitialSize());
    }
    public HoverPainter getPainter(){
        return null;
    }
    public boolean acceptComponent(int x, int y) {
        AddingModel model = designer.getAddingModel();

        if (model != null) {
            //获取该组件所在的焦点容器
            Rectangle rect = Util.getRelativeBounds(component);
            Component bean = model.getBean();
            if (bean instanceof JInternalFrame) {
                return false;
            }
            //调用其acceptComponent接受组件
            return addBean((Container) component, bean, x - designer.getOuterLeft() - rect.x, y - designer.getOuterTop() - rect.y);
        } else {
            return false;
        }
    }

    protected boolean addBean(Container container, Component bean, int x, int y) {
        bean.setLocation(x - (bean.getWidth() / 2), y - (bean.getHeight() / 2));
        container.add(bean);
        Util.layoutContainer(container);

        return true;
    }

    public Component getChild(int index) {
        return ((Container) component).getComponent(index);
    }

    public int getChildCount() {
        return ((Container) component).getComponentCount();
    }

    public int getIndexOfChild(Component child) {
        Container container = (Container) component;
        int count = container.getComponentCount();

        for (int i = 0; i < count; i++) {
            Component comp = container.getComponent(i);

            if (comp == child) {
                return i;
            }
        }

        return -1;
    }

    public void showComponent(Component child) {
    }

    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return null;
    }

    public GroupModel getLayoutProperties() {
        return null;
    }

    public void removeComponent(Component bean) {
        ((Container) component).remove(bean);
    }

    private class FreeFormAction extends NullLayoutAction {

        public FreeFormAction(SwingDesigner designer) {
            super(designer);
            putValue(NAME, "Free Form Layout");
        }
    }

    protected JMenu createLayoutMenu(Component current) {
        Action[] actions = new Action[]{new FreeFormAction(designer), null, new BorderLayoutAction(designer), new FlowLayoutAction(designer), new AbsoluteLayoutAction(designer), new BoxLayoutAction(designer), new CardLayoutAction(designer), new GridLayoutAction(designer), new GridBagLayoutAction(designer), new NullLayoutAction(designer)};
        JMenu setLayout = new JMenu("Set Layout");
        initSubMenu(setLayout, actions, current);
        return setLayout;
    }

    @Override
    public String getAddComponentCode(Component bean) {
        ConstraintsGroupModel cgm=this.getLayoutConstraints(bean);
        if(cgm==null){
            String childName=Util.getComponentName(bean);
            return VAR_CONTAINER+".add("+Util.getGetName(childName)+"());\n";
        }else
            return cgm.getAddComponentCode();
    }

    public String getLayoutCode() {
        return null;
    }    
    public Painter getAnchorPainter(){
        return null;
    }
}