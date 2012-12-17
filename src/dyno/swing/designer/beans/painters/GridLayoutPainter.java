/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;

public class GridLayoutPainter extends GridLayoutAnchorPainter implements HoverPainter {

    private Point point;
    private Component current;

    public GridLayoutPainter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    public void setHotspot(Point p) {
        point = p;
    }

    public void setComponent(Component component) {
        this.current = component;
    }

    @Override
        public void paint(Graphics g) {
        super.paint(g);
        int x = point.x - designer.getLeftOffset();
        int y = point.y - designer.getTopOffset();
        int column = grid_layout.getColumns();
        int row = grid_layout.getRows();
        if (column == 0) {
            column = 1;
        }
        if (row == 0) {
            row = 1;
        }
        double w = (double) hotspot.width / column;
        double h = (double) hotspot.height / row;
        int ix = (int) (x / w);
        int iy = (int) (y / h);
        x = (int) (ix * w + hotspot.x);
        y = (int) (iy * h + hotspot.y);
        drawHotspot(g, x, y, (int) w, (int) h, SELECTION_COLOR);
    }
}

