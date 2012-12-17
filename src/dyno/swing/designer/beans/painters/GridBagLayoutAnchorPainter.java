/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.adapters.layout.*;
import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;

/**
 *
 * @author William Chen
 */
public class GridBagLayoutAnchorPainter extends AbstractAnchorPainter {

    protected GridBagLayout gridbag_layout;

    public GridBagLayoutAnchorPainter(SwingDesigner designer, Container container) {
        super(designer, container);
        gridbag_layout = (GridBagLayout) container.getLayout();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point origin = gridbag_layout.getLayoutOrigin();
        int[][] dims = gridbag_layout.getLayoutDimensions();
        g2d.setColor(Color.green);
        g2d.drawRect(hotspot.x, hotspot.y, hotspot.width, hotspot.height);
        int x = hotspot.x + origin.x;
        int y = hotspot.y + origin.y;
        for (int i = 0; i < dims[0].length; i++) {
            if (x >= hotspot.x && x < hotspot.x + hotspot.width) {
                g2d.drawLine(x, hotspot.y, x, hotspot.y + hotspot.height);
            }
            x += dims[0][i];
        }
        if (x >= hotspot.x && x < hotspot.x + hotspot.width) {
            g2d.drawLine(x, hotspot.y, x, hotspot.y + hotspot.height);
        }
        for (int i = 0; i < dims[1].length; i++) {
            if (y >= hotspot.y && y < hotspot.y + hotspot.height) {
                g2d.drawLine(hotspot.x, y, hotspot.x + hotspot.width, y);
            }
            y += dims[1][i];
        }
        if (y >= hotspot.y && y < hotspot.y + hotspot.height) {
            g2d.drawLine(hotspot.x, y, hotspot.x + hotspot.width, y);
        }
    }
}
