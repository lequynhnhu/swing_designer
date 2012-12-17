/*
 * ComponentAdapter.java
 *
 * Created on 2007年5月2日, 下午11:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import dyno.swing.designer.properties.ValidationException;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import java.beans.BeanInfo;
import java.util.ArrayList;

import javax.swing.JPopupMenu;


/**
 * 组件适配器接口，可看做是BeanInfo的扩展。
 * 主要目的是为具体组件提供特殊设计行为
 *
 * @author William Chen
 */
public interface ComponentAdapter {
    /**
     * 获取该Component组件类型的BeanInfo
     * @return 获取该组件对应的beanInfo
     */
    BeanInfo getBeanInfo();


    Image getIcon(int iconKind);
    /**
     * 克隆一个该类型的组件。该方法主要用于复制、剪切和粘帖组件时使用。克隆原来的组件，要求
     * 克隆后的组件和原始组件具有相同的属性。
     *
     * @param source源组件
     * @return 克隆组件
     */
    Component clone();

    /**
     * 在组件选择面板上选择了组件类型后，在设计界面上跟随鼠标移动用来代表当前要添加组件的图形
     * 一般使用组件自身的图形代替。
     *
     * @param component 要添加的组件
     * @param g 当前设计器的图形上下文对象
     */
    void paintComponentMascot(Graphics g);


    /**
     * 当鼠标在设计界面上点击时，调用鼠标所在组件适配器的componentClick方法进行处理。
     * 主要是用于解决JTabbedPane在设计状态时能进行面板的切换。
     *
     * @param e 当前鼠标事件
     *
     * @return 是否继续处理该鼠标事件，如果该鼠标事件该预示这拖拽、选择等行为的开始，应该返回true，以便后面的事件处理继续进行
     */
    boolean componentClicked(MouseEvent e);

    /**
     * 当鼠标在此设计组件上右键点击时，该方法根据上下文和组件类型提供弹出响应的菜单
     *
     * @param 引发弹出菜单的鼠标事件
     *
     * @return 弹出菜单
     */
    JPopupMenu getContextPopupMenu(MouseEvent e);

    /**
     * 为当前组件创建描述属性表的model, 分组返回
     * @return BeanPropertyModel
     */
    ArrayList<GroupModel> getBeanPropertyModel();

    /**
     * 为当前组件创建事件描述表，分组返回
     */
    ArrayList<GroupModel> getEventPropertyModel();
    /**
     * 提供双击设计器的编辑器
     * @param bean 鼠标双击的被设计组件
     * @param x
     * @param y 鼠标坐标在bean内的位置
     * @return 被设计的编辑器
     */
    DesignerEditor getDesignerEditor(int x, int y);

    /**
     * 检验value对于bean来说是否有效
     * @param bean被编辑的组件
     * @param value新编辑的值
     * @throws 如果值不合法，抛出的异常，异常中包含错误消息
     */
    void validateBeanValue(Object value)
        throws ValidationException;

    /**
     * 更改bean的值
     * @param 需要更新的bean
     * @param 新的value
     */
    void setBeanValue(Object value);

    /**
     * 获取当前编辑的bean的值
     * @param bean 被编辑的bean
     * @return 当前被编辑的bean的值
     */
    Object getBeanValue();

    /**
     * 获取DesignerEditor的编辑放置的位置
     * @param bean 鼠标双击的被设计组件
     * @param x
     * @param y 鼠标坐标在bean内的位置
     * @return 编辑器应该放置的位置，相对于bean的相对位置
     */
    Rectangle getEditorBounds(int x, int y);
    /**
     * 实例化组件的适配器后，在这儿进行初始化
     */
    void initialize();    
}
