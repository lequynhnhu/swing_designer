/*
 * LayoutPainter.java
 *
 * Created on 2007-8-2, 15:41:27
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;


import java.awt.Component;
import java.awt.Point;


/**
 * 渲染器，目的是为组件或者布局管理器提供额外的渲染入口。
 *
 * @author William Chen
 */
public interface HoverPainter extends Painter{
    //当前焦点热点，即鼠标所在点
    void setHotspot(Point p);

    //当前要放置的组件
    void setComponent(Component component);
}
