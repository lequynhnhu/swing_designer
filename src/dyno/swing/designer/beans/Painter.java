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
    //当前焦点区域，即所在容器的边界
    void setRenderingBounds(Rectangle rect);

    //渲染入口，由SwingDesigner调用来外成额外渲染
    void paint(Graphics g);
}
