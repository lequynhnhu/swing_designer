/*
 * NullLayoutPainter.java
 *
 * Created on 2007-8-2, 18:51:29
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

/**
 *
 * @author William Chen
 */
public class NullLayoutPainter extends AbstractPainter {

    public NullLayoutPainter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    public void paint(Graphics g) {
        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, container);
        HoverPainter painter = null;
        if(adapter instanceof ContainerAdapter)
            painter=((ContainerAdapter)adapter).getPainter();

        if (painter != null) {
            painter.setComponent(this.component);
            painter.setHotspot(this.hotspot);
            painter.setRenderingBounds(this.hotspot_bounds);
            painter.paint(g);
        } else {
            if (isAddingIF()) {
                drawHotspot(g, hotspot_bounds, false);
            } else {
                g.setColor(Color.lightGray);
                g.drawRect(hotspot_bounds.x, hotspot_bounds.y, hotspot_bounds.width, hotspot_bounds.height);
            }
        }
    }
}