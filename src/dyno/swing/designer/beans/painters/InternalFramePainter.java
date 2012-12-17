/*
 * InternalFramePainter.java
 *
 * Created on 2007-9-4, 16:24:54
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JInternalFrame;

/**
 *
 * @author William Chen
 */
public class InternalFramePainter extends AbstractPainter {

    protected JInternalFrame frame;
    protected Container contentPane;

    public InternalFramePainter(SwingDesigner designer, Container container) {
        super(designer, container);
        frame = (JInternalFrame) container;
        contentPane = frame.getContentPane();
    }

    public void paint(Graphics g) {
        int x = hotspot.x;
        int y = hotspot.y;
        int hx = hotspot_bounds.x;
        int hy = hotspot_bounds.y;
        int hw = hotspot_bounds.width;
        int hh = hotspot_bounds.height;
        Rectangle contentRect = Util.getRelativeBounds(contentPane);
        contentRect.x += designer.getOuterLeft();
        contentRect.y += designer.getOuterTop();
        if (!contentRect.contains(hotspot)) {
            Rectangle titleRect = new Rectangle(hotspot_bounds);
            titleRect.height = hotspot_bounds.height - contentRect.height;
            drawHotspot(g, titleRect, false);
        } else {
            HoverPainter painter=Util.getContainerPainter(designer, contentPane);
            painter.setComponent(component);
            painter.setHotspot(hotspot);
            painter.setRenderingBounds(contentRect);
            painter.paint(g);
        }
    }
}