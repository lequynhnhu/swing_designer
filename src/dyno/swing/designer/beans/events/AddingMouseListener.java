/*
 * AddingMouseListener.java
 *
 * Created on August 1, 2007, 4:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import dyno.swing.designer.beans.*;
import dyno.swing.designer.beans.models.AddingModel;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.MouseEvent;
import java.beans.BeanInfo;
import javax.swing.RootPaneContainer;
import javax.swing.event.MouseInputListener;


/**
 * 添加模式下鼠标事件处理器。
 *
 * @author William Chen
 */
public class AddingMouseListener extends DropTarget implements MouseInputListener, Constants {

    private SwingDesigner designer;
    /**
     * 添加模式下当前要添加组件的BeanInfo
     */
    private BeanInfo beanInfo;
    /**
     * 当前鼠标的设计组件
     */
    private Component current;
    /**
     * 当前添加模式对应的model
     */
    private AddingModel addingModel;

    /** Creates a new instance of AddingMouseListener */
    public AddingMouseListener(SwingDesigner designer, BeanInfo beanInfo) {
        this.designer = designer;
        this.beanInfo = beanInfo;
        this.addingModel = designer.getAddingModel();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        adding(e.getX(), e.getY());
    }

    private void adding(int x, int y) {
        //当前鼠标所在的组件
        Component hoveredComponent = designer.getComponentAt(x, y);

        //获取该组件所在的焦点容器
        Component container = Util.getHotspotContainer(designer, hoveredComponent);

        boolean success = false;

        if (container != null) {
            //获取该container组件对应的适配器
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, container);

            if (adapter instanceof ContainerAdapter) {
                //如果是容器，则调用其acceptComponent接受组件
                success = ((ContainerAdapter) adapter).acceptComponent(x, y);
            }
        }

        if (success) {
            //如果添加成功，则触发相应事件
            fireComponentAdded();
            designer.getSelectionModel().setSelectedComponent(addingModel.getBean());
        } else {
            Toolkit.getDefaultToolkit().beep();
            fireComponentCanceled();
        }

        //取消提示
        designer.setPainter(null);
        //切换添加状态到普通状态
        designer.stopAddingState();
    }

    //触发各种事件
    private void fireComponentAdded() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setAddedComponent(addingModel.getBean());
        designer.getEditListenerTable().fireComponentAdded(evt);
    }

    private void fireComponentCanceled() {
        DesignerEvent evt = new DesignerEvent(this);
        designer.getEditListenerTable().fireComponentCanceled(evt);
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        entering(e.getX(), e.getY());
    }

    private void entering(int x, int y) {
        //将要添加的组件图标移动到鼠标下的位置
        addingModel.moveTo(x, y);
        designer.repaint();
    }

    public void mouseExited(MouseEvent e) {
        exiting();
    }

    private void exiting() {
        //隐藏组件图标
        addingModel.reset();
        designer.repaint();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        hovering(e.getX(), e.getY());
    }

    private void hovering(int x, int y) {
        //当前位置移植鼠标e所在的位置
        addingModel.moveTo(x, y);

        //获取e所在的焦点组件
        Component hotspot = designer.getComponentAt(x, y);

        //获取焦点组件所在的焦点容器
        Component container = Util.getHotspotContainer(designer, hotspot);

        if(container instanceof RootPaneContainer)
            container=((RootPaneContainer)container).getContentPane();
        
        if (container != null) {
            HoverPainter painter = null;

            if (container != current) {
                //如果焦点容器不是当前容器
                if (current != null) {
                    //取消前一个焦点容器的提示渲染器
                    designer.setPainter(null);
                }
                
                painter = Util.getContainerPainter(designer, container);

                //为界面设计器设置提示渲染提示器
                designer.setPainter(painter);

                //将当前容器更新为新的容器
                current = container;
            } else {
                //获取当前设计界面的提示渲染器
                Painter p =designer.getPainter();
                if(p instanceof HoverPainter)
                painter = (HoverPainter)p;
            }

            if (painter != null) {
                //为提示渲染器设置焦点位置、区域等渲染参数
                Rectangle rect = Util.getRelativeBounds(container);
                rect.x += designer.getOuterLeft();
                rect.y += designer.getOuterTop();
                painter.setRenderingBounds(rect);
                painter.setHotspot(new Point(x, y));
                painter.setComponent(addingModel.getBean());
            }
        } else {
            //如果鼠标不在任何组件上，则取消提示器
            designer.setPainter(null);
            current = null;
        }

        designer.repaint();
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        Point loc = dtde.getLocation();
        entering(loc.x, loc.y);
    }

    public void dragOver(DropTargetDragEvent dtde) {
        Point loc = dtde.getLocation();
        hovering(loc.x, loc.y);
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
        exiting();
    }

    public void drop(DropTargetDropEvent dtde) {
        Point loc = dtde.getLocation();
        adding(loc.x, loc.y);
    }
}