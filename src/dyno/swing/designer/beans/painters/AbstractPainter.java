/*
 * AbstractPainter.java
 *
 * Created on 2007年8月24日, 下午11:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import javax.swing.JInternalFrame;

/**
 *
 * @author William Chen
 */
public abstract class AbstractPainter implements HoverPainter, Constants {
    protected Point hotspot;
    protected Rectangle hotspot_bounds;
    protected Container container;
    protected Component component;
    protected SwingDesigner designer;
    public AbstractPainter(SwingDesigner designer, Container container){
        this.designer=designer;
        this.container=container;
    }
    public void setHotspot(Point p) {
        hotspot = p;
    }
    public void setRenderingBounds(Rectangle rect) {
        hotspot_bounds = rect;
    }
    public void setComponent(Component component) {
        this.component = component;
    }
    protected void drawHotspot(Graphics g, Rectangle box, boolean accept){
        drawHotspot(g, box.x, box.y, box.width, box.height, accept);
    }
    protected void drawHotspot(Graphics g, int x, int y, int width, int height,
        boolean accept) {
        Color bColor=accept ? LAYOUT_HOTSPOT_COLOR : LAYOUT_FORBIDDEN_COLOR;
        drawHotspot(g, x, y, width, height, bColor);
    }
    protected void drawHotspot(Graphics g, Rectangle box, Color bColor){
        drawHotspot(g, box.x, box.y, box.width, box.height, bColor);
    }
    protected void drawHotspot(Graphics g, int x, int y, int width, int height, Color bColor){
        Graphics2D g2d = (Graphics2D) g;
        Color color = g2d.getColor();
        Stroke backup = g2d.getStroke();

        g2d.setStroke(STROKE4);
        g2d.setColor(bColor);
        g2d.drawRect(x, y, width, height);

        g2d.setStroke(backup);
        g2d.setColor(color);
    }
    protected boolean isAddingIF(){
        return component instanceof JInternalFrame;
    }
}
