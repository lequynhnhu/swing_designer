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
 * ��Ⱦ����Ŀ����Ϊ������߲��ֹ������ṩ�������Ⱦ��ڡ�
 *
 * @author William Chen
 */
public interface HoverPainter extends Painter{
    //��ǰ�����ȵ㣬��������ڵ�
    void setHotspot(Point p);

    //��ǰҪ���õ����
    void setComponent(Component component);
}
