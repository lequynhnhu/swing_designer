/*
 * SplitPanePainter.java
 *
 * Created on 2007-8-9, 18:41:26
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JSplitPane;


public class SplitPanePainter extends AbstractPainter {
    public SplitPanePainter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    @Override
    public void paint(Graphics g) {
        JSplitPane splitPane = (JSplitPane) container;
        int div = splitPane.getDividerLocation();
        int div_size = splitPane.getDividerSize();
        int hx = hotspot_bounds.x;
        int hy = hotspot_bounds.y;
        int hw = hotspot_bounds.width;
        int hh = hotspot_bounds.height;

        if (isHorizontal()) {
            if (isOnLeft()) {
                boolean accept = isLeftAccept() && !isAddingIF();
                drawHotspot(g, hx, hy, div + (div_size / 2), hh, accept);
            } else {
                boolean accept = isRightAccept() && !isAddingIF();
                drawHotspot(g, hx + div + (div_size / 2), hy,
                    hw - div - (div_size / 2), hh, accept);
            }
        } else {
            if (isOnLeft()) {
                boolean accept = isLeftAccept() && !isAddingIF();
                drawHotspot(g, hx, hy, hw, div + (div_size / 2), accept);
            } else {
                boolean accept = isRightAccept() && !isAddingIF();
                drawHotspot(g, hx, hy + div + (div_size / 2), hw,
                    hh - div - (div_size / 2), accept);
            }
        }
    }

    private static boolean isHorizontal(JSplitPane splitPane) {
        int orient = splitPane.getOrientation();

        return orient == JSplitPane.HORIZONTAL_SPLIT;
    }

    private boolean isHorizontal() {
        return isHorizontal((JSplitPane) container);
    }

    private boolean isOnLeft() {
        return isOnLeft((JSplitPane) container, hotspot, hotspot_bounds);
    }

    private boolean isLeftAccept() {
        return isLeftAccept((JSplitPane) container);
    }

    private boolean isRightAccept() {
        return isRightAccept((JSplitPane) container);
    }

    private static boolean isOnLeft(JSplitPane splitPane, Point hotspot,
        Rectangle hotspot_bounds) {
        int div = splitPane.getDividerLocation();
        int div_size = splitPane.getDividerSize();
        int x = hotspot.x;
        int y = hotspot.y;
        int hx = hotspot_bounds.x;
        int hy = hotspot_bounds.y;

        return isHorizontal(splitPane) ? (x < (hx + div + (div_size / 2)))
                                       : (y < (hy + div + (div_size / 2)));
    }

    private static boolean isLeftAccept(JSplitPane splitPane) {
        return (splitPane.getLeftComponent() == null) ||
        !Util.isDesigning(splitPane.getLeftComponent());
    }

    private static boolean isRightAccept(JSplitPane splitPane) {
        return (splitPane.getRightComponent() == null) ||
        !Util.isDesigning(splitPane.getRightComponent());
    }
}
