/*
 * Container.java
 *
 * Created on 2007年8月11日, 下午8:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import java.awt.Component;


/**
 *
 * @author William Chen
 */
public interface ContainerAdapter extends ComponentAdapter {

    /**
     * 该方法只在isContainer返回true时使用。在当前鼠标所在位置的容器组件上添加新的组件。
     *
     * @param designer 当前设计器
     * @param x
     * @param y  添加位置的坐标
     *
     * @return 添加成功返回true，否则false
     */
    boolean acceptComponent(int x, int y);

    /**
     * 获取容器parent的第index个组件，该组件是显示组件，即是要设计的组件
     * @param parent 父容器
     * @param index 字组件的索引
     * @return 获得parent的第index个子组件
     */
    Component getChild(int index);

    /**
     * 添加组件状态时，鼠标移动到某个组件上方时，可以为该组件提供自定义的外观
     * 主要用于容器类组件在放置新的组件前提供方位的提示，通常和当前鼠标所在容器
     * 的布局管理器的Painter协作，为放置组件提供方位指示。比如JSplitPane的Painter
     * 可为要添加的组件提供左右提示矩形，而BorderLayout的Painter可以为布局管理器是
     * BorderLayout的容器提供上下左右中的提示矩形等等。
     *
     * @ return 位置提示渲染器，可以为空，为空时不进行提示
     */
    HoverPainter getPainter();
    Painter getAnchorPainter();
    /**
     * 获取容器parent的所容纳子组件的个数
     * @return 容器parent的字组件个数
     */
    int getChildCount();

    /**
     * 获得组件child在容器parent中的索引数
     */
    int getIndexOfChild(Component child);

    /**
     * 显示parent的字组件child，解决JTabbedPane显示某个非显示组件的特殊情况
     */
    void showComponent(Component child);

    void addNextComponent(Component dragged);

    void addBefore(Component target, Component added);

    void addAfter(Component target, Component added);

    boolean canAcceptMoreComponent();

    boolean canAddBefore(Component hovering);

    boolean canAddAfter(Component hovering);

    ConstraintsGroupModel getLayoutConstraints(Component bean);

    GroupModel getLayoutProperties();
    /**
     * 从当前容器中删除子组件bean的特殊处理（比如JScrollPane删除JViewport的组件）
     */
    void removeComponent(Component bean);
    
    String getAddComponentCode(Component bean);
    
    String getLayoutCode();
}