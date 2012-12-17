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
 * ���ģʽ������¼���������
 *
 * @author William Chen
 */
public class AddingMouseListener extends DropTarget implements MouseInputListener, Constants {

    private SwingDesigner designer;
    /**
     * ���ģʽ�µ�ǰҪ��������BeanInfo
     */
    private BeanInfo beanInfo;
    /**
     * ��ǰ����������
     */
    private Component current;
    /**
     * ��ǰ���ģʽ��Ӧ��model
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
        //��ǰ������ڵ����
        Component hoveredComponent = designer.getComponentAt(x, y);

        //��ȡ��������ڵĽ�������
        Component container = Util.getHotspotContainer(designer, hoveredComponent);

        boolean success = false;

        if (container != null) {
            //��ȡ��container�����Ӧ��������
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, container);

            if (adapter instanceof ContainerAdapter) {
                //������������������acceptComponent�������
                success = ((ContainerAdapter) adapter).acceptComponent(x, y);
            }
        }

        if (success) {
            //�����ӳɹ����򴥷���Ӧ�¼�
            fireComponentAdded();
            designer.getSelectionModel().setSelectedComponent(addingModel.getBean());
        } else {
            Toolkit.getDefaultToolkit().beep();
            fireComponentCanceled();
        }

        //ȡ����ʾ
        designer.setPainter(null);
        //�л����״̬����ͨ״̬
        designer.stopAddingState();
    }

    //���������¼�
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
        //��Ҫ��ӵ����ͼ���ƶ�������µ�λ��
        addingModel.moveTo(x, y);
        designer.repaint();
    }

    public void mouseExited(MouseEvent e) {
        exiting();
    }

    private void exiting() {
        //�������ͼ��
        addingModel.reset();
        designer.repaint();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        hovering(e.getX(), e.getY());
    }

    private void hovering(int x, int y) {
        //��ǰλ����ֲ���e���ڵ�λ��
        addingModel.moveTo(x, y);

        //��ȡe���ڵĽ������
        Component hotspot = designer.getComponentAt(x, y);

        //��ȡ����������ڵĽ�������
        Component container = Util.getHotspotContainer(designer, hotspot);

        if(container instanceof RootPaneContainer)
            container=((RootPaneContainer)container).getContentPane();
        
        if (container != null) {
            HoverPainter painter = null;

            if (container != current) {
                //��������������ǵ�ǰ����
                if (current != null) {
                    //ȡ��ǰһ��������������ʾ��Ⱦ��
                    designer.setPainter(null);
                }
                
                painter = Util.getContainerPainter(designer, container);

                //Ϊ���������������ʾ��Ⱦ��ʾ��
                designer.setPainter(painter);

                //����ǰ��������Ϊ�µ�����
                current = container;
            } else {
                //��ȡ��ǰ��ƽ������ʾ��Ⱦ��
                Painter p =designer.getPainter();
                if(p instanceof HoverPainter)
                painter = (HoverPainter)p;
            }

            if (painter != null) {
                //Ϊ��ʾ��Ⱦ�����ý���λ�á��������Ⱦ����
                Rectangle rect = Util.getRelativeBounds(container);
                rect.x += designer.getOuterLeft();
                rect.y += designer.getOuterTop();
                painter.setRenderingBounds(rect);
                painter.setHotspot(new Point(x, y));
                painter.setComponent(addingModel.getBean());
            }
        } else {
            //�����겻���κ�����ϣ���ȡ����ʾ��
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