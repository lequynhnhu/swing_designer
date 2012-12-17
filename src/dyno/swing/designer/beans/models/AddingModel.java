/*
 * AddingModel.java
 *
 * Created on August 1, 2007, 4:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.models;

import dyno.swing.designer.beans.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.beans.BeanInfo;
import java.beans.Introspector;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;


/**
 * 添加状态下的model
 * @author William Chen
 */
public class AddingModel {

    private static int counter = 0;
    private SwingDesigner designer;
    //当前组件对应的BeanInfo

    private BeanInfo beanInfo;
    //当前要添加的组件

    private Component bean;
    //当前组件的变量名称

    private String beanName;
    //记录当前鼠标的位置信息

    private int current_x;
    private int current_y;

    /** Creates a new instance of AddingModel */
    public AddingModel(SwingDesigner designer, BeanInfo beanInfo) {
        this.designer = designer;
        if (needScrollPane(beanInfo)) {
            try {
                Component child = instantiateBean(beanInfo, designer);
                this.beanInfo = Introspector.getBeanInfo(JScrollPane.class);
                beanName = this.beanInfo.getBeanDescriptor().getName();
                beanName = beanName + (counter++);
                beanName = "" + Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
                bean = Util.instantiateBean(designer, this.beanInfo, beanName);
                ((JScrollPane) bean).setViewportView(child);
                Dimension size = bean.getSize();
                Dimension prefSize = child.getPreferredSize();
                if (size.width > prefSize.width) {
                    prefSize.width = size.width;
                }
                if (size.height > prefSize.height) {
                    prefSize.height = size.height;
                }
                bean.setSize(prefSize);
                bean.setPreferredSize(prefSize);
                child.addNotify();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            this.beanInfo = beanInfo;
            beanName = beanInfo.getBeanDescriptor().getName();
            beanName = beanName + (counter++);
            beanName = "" + Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
            bean = Util.instantiateBean(designer, beanInfo, beanName);
        }
        if (bean instanceof Container) {
            Util.layoutContainer((Container) bean);
        }
        current_x = -bean.getWidth();
        current_y = -bean.getHeight();
    }

    private boolean needScrollPane(BeanInfo beanInfo) {
        Class beanClass = beanInfo.getBeanDescriptor().getBeanClass();
        return JTable.class.isAssignableFrom(beanClass) || JTree.class.isAssignableFrom(beanClass) || JList.class.isAssignableFrom(beanClass) || JTextArea.class.isAssignableFrom(beanClass) || JTextPane.class.isAssignableFrom(beanClass) || JEditorPane.class.isAssignableFrom(beanClass);
    }

    public AddingModel(Component component, int x, int y) {
        this.beanInfo = AdapterBus.getComponentAdapter(designer, component).getBeanInfo();
        beanName = Util.getComponentName(component);
        bean = component;
        current_x = x - (component.getWidth() / 2);
        current_y = y - (component.getHeight() / 2);
    }

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    //隐藏当前组件的图标

    public void reset() {
        current_x = -bean.getWidth();
        current_y = -bean.getHeight();
    }

    public int getCurrentX() {
        return current_x;
    }

    public int getCurrentY() {
        return current_y;
    }

    public void setCurrentX(int x) {
        current_x = x;
    }

    public void setCurrentY(int y) {
        current_y = y;
    }

    //移动组件图标到鼠标事件发生的位置

    public void moveTo(MouseEvent e) {
        current_x = e.getX() - (bean.getWidth() / 2);
        current_y = e.getY() - (bean.getHeight() / 2);
    }

    public void moveTo(int x, int y) {
        current_x = x - (bean.getWidth() / 2);
        current_y = y - (bean.getHeight() / 2);
    }

    public Component getBean() {
        return bean;
    }

    private Component instantiateBean(BeanInfo beanInfo, SwingDesigner designer) {
        String bName = beanInfo.getBeanDescriptor().getName();
        bName = bName + (counter++);
        Component child = Util.instantiateBean(designer, beanInfo, bName);
        return child;
    }
}