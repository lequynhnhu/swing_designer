/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author William Chen
 */
public interface Painter {
    //��ǰ�������򣬼����������ı߽�
    void setRenderingBounds(Rectangle rect);

    //��Ⱦ��ڣ���SwingDesigner��������ɶ�����Ⱦ
    void paint(Graphics g);
}
