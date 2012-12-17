/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.adapters.layout.*;
import dyno.swing.designer.beans.SwingDesigner;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author William Chen
 */
public class GridBagLayoutPainter extends GridBagLayoutAnchorPainter implements HoverPainter, Constants {

    public GridBagLayoutPainter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        HotspotBounds hotspot_bounds = getHotspotBounds();
        Rectangle bounds = hotspot_bounds.bounds;
        boolean accept = hotspot_bounds.accept;
        if (bounds != null) {
            Graphics2D g2d = (Graphics2D) g;
            Color back = g2d.getBackground();
            Composite composite = g2d.getComposite();
            g2d.setColor(accept ? LAYOUT_HOTSPOT_COLOR : LAYOUT_FORBIDDEN_COLOR);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, HOVER_TRANSLUCENCY));
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2d.setComposite(composite);
            g2d.setColor(back);
        }
    }

    private class HotspotBounds {

        public boolean accept;
        public Rectangle bounds;

        public HotspotBounds(boolean accept, Rectangle bounds) {
            this.accept = accept;
            this.bounds = bounds;
        }

        public HotspotBounds(Rectangle bounds) {
            this(false, bounds);
        }

        public HotspotBounds() {
        }
    }

    private HotspotBounds getHotspotBounds() {
        Point origin = gridbag_layout.getLayoutOrigin();
        int[][] dims = gridbag_layout.getLayoutDimensions();
        int x = hotspot.x + origin.x;
        int y = hotspot.y + origin.y;
        int[] xi = getIndex(x, dims[0], mouse_hotspot.x);
        if (xi[0] == -1) {
            return new HotspotBounds();
        }
        int[] yi = getIndex(y, dims[1], mouse_hotspot.y);
        if (yi[0] == -1) {
            return new HotspotBounds();
        }
        if (xi[0] < dims[0].length) {
            if (yi[0] < dims[1].length) {                
                return new HotspotBounds(true, new Rectangle(xi[1], yi[1], xi[2], yi[2]));
            } else {
                return new HotspotBounds(true, new Rectangle(xi[1], yi[1], xi[2], hotspot.y + hotspot.height - yi[1]));
            }
        } else {
            if (yi[0] < dims[1].length) {
                return new HotspotBounds(true, new Rectangle(xi[1], yi[1], hotspot.x + hotspot.width - xi[1], yi[2]));
            } else {
                return new HotspotBounds(true, new Rectangle(xi[1], yi[1], hotspot.x + hotspot.width - xi[1], hotspot.y + hotspot.height - yi[1]));
            }
        }
    }

    private int[] getIndex(int start, int[] dims, int hotspot) {
        int i;
        for (i = 0; i < dims.length; i++) {
            if (hotspot < start) {
                break;
            }
            start += dims[i];
        }
        if (i == 0) {
            return new int[]{-1};
        }
        if (i == dims.length) {
            if (hotspot < start) {
                int index = dims.length - 1;
                return new int[]{index, start - dims[index], dims[index]};
            } else {
                return new int[]{dims.length, start, 0};
            }
        } else {
            int index = i - 1;
            return new int[]{index, start - dims[index], dims[index]};
        }
    }

    public void setHotspot(Point p) {
        mouse_hotspot = p;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
    private Point mouse_hotspot;
    private Component component;
}
