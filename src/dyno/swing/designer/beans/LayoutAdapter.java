/*
 * LayoutAdapter.java
 *
 * Created on 2007年5月2日, 下午11:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import java.awt.Component;

/**
 * 该接口是LayoutManager的BeanInfo类。标准Java平台没有提供布局管理器的BeanInfo类，
 * 对于界面设计工具来说还需一些特殊的行为。
 *
 * @author William Chen
 */
public interface LayoutAdapter {
    /**
     * 在添加组件状态时，当鼠标移动到某个容器上方时，如果该容器有布局管理器，则会调用该布局
     * 管理适配器的accept来决定当前位置是否可以放置，并提供特殊的标识，比如红色区域标识。比
     * 如在BorderLayout中，如果某个方位已经放置了组件，则此时应该返回false标识该区域不可以
     * 放置。
     *
     * @param container 正在添加新组件的container
     * @param bean 被添加的新组件
     * @param x 添加的位置x，该位置是相对于container的
     * @param y 添加的位置y，该位置是相对于container的
     * @return 是否可以放置。
     */
    boolean accept(Component bean, int x, int y);

    /**
     * 组件的ComponentAdapter在添加组件时，如果发现布局管理器不为空，会继而调用该布局管理器的
     * addBean方法来完成组件的具体添加。在该方法内，布局管理器可以提供额外的功能。
     * @param container 正在添加新组件的container
     * @param bean 被添加的新组件
     * @param x 添加的位置x，该位置是相对于container的
     * @param y 添加的位置y，该位置是相对于container的
     * @return 是否添加成功，成功返回true，否则false
     */
    boolean addBean(Component bean, int x, int y);

    /**
     * 返回该布局管理适配器的Painter，为容器提供放置位置的标识。
     *
     */
    HoverPainter getPainter();

    Painter getAnchorPainter();
    /**
     * 显示parent的字组件child，解决CardLayout中显示某个非显示组件的特殊情况
     */
    void showComponent(Component child);

    void addNextComponent(Component dragged);

    void addBefore(Component target, Component added);

    void addAfter(Component target, Component added);

    boolean canAcceptMoreComponent();
    
    ConstraintsGroupModel getLayoutConstraints(Component bean);
    
    GroupModel getLayoutProperties();
    
    String getLayoutCode();
}
