/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

/**
 *
 * @author William Chen
 */
public abstract class AbstractAnchorPainter implements Painter, Constants {

    protected Container container;
    protected SwingDesigner designer;
    protected Rectangle hotspot;

    public AbstractAnchorPainter(SwingDesigner designer, Container container) {
        this.designer = designer;
        this.container = container;
    }

    public void setRenderingBounds(Rectangle rect) {
        this.hotspot = rect;
    }

    protected void drawHotspot(Graphics g, Rectangle box, Color bColor) {
        drawHotspot(g, box.x, box.y, box.width, box.height, bColor);
    }

    protected void drawHotspot(Graphics g, int x, int y, int width, int height, Color bColor) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke backup = g2d.getStroke();
        g2d.setStroke(STROKE2);
        Color color = g2d.getColor();
        g2d.setColor(bColor);
        g2d.drawRect(x, y, width, height);
        g2d.setColor(color);
        g2d.setStroke(backup);
    }
}
