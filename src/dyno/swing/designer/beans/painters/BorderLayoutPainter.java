/*
 * BorderLayoutPainter.java
 *
 * Created on 2007-8-2, 16:56:01
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.adapters.layout.BorderLayoutAdapter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JInternalFrame;


/**
 *
 * @author William Chen
 */
public class BorderLayoutPainter extends AbstractPainter {
    public BorderLayoutPainter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    public void paint(Graphics g) {
        int x = hotspot.x;
        int y = hotspot.y;
        int hx = hotspot_bounds.x;
        int hy = hotspot_bounds.y;
        int hw = hotspot_bounds.width;
        int hh = hotspot_bounds.height;
        BorderLayoutAdapter adapter=(BorderLayoutAdapter)AdapterBus.getLayoutAdapter(designer, container);
        Dimension prefSize = adapter.getPreferredSize(component);
        BorderLayout layout = (BorderLayout) container.getLayout();
        boolean accept = adapter.accept(component, x - hx, y - hy) && !isAddingIF();
        int sx = hx;
        int sy = hy;
        int sw = hw;
        int sh = hh;

        if (y < (hy + prefSize.height)) {
            //NORTH
            sx = hx;
            sy = hy;
            sw = hw;
            sh = prefSize.height;
        } else if ((y >= (hy + prefSize.height)) &&
                (y < ((hy + hh) - prefSize.height))) {
            if (x < (hx + prefSize.width)) {
                //WEST
                Component north = layout.getLayoutComponent(BorderLayout.NORTH);
                Component south = layout.getLayoutComponent(BorderLayout.SOUTH);
                sx = hx;
                sy = hy;

                if (north != null) {
                    sy += north.getPreferredSize().height;
                }

                sw = prefSize.width;
                sh = hh;

                if (north != null) {
                    sh -= north.getPreferredSize().height;
                }

                if (south != null) {
                    sh -= south.getPreferredSize().height;
                }
            } else if ((x >= (hx + prefSize.width)) &&
                    (x < ((hx + hw) - prefSize.width))) {
                //CENTER
                Component north = layout.getLayoutComponent(BorderLayout.NORTH);
                Component south = layout.getLayoutComponent(BorderLayout.SOUTH);
                Component east = layout.getLayoutComponent(BorderLayout.EAST);
                Component west = layout.getLayoutComponent(BorderLayout.WEST);
                sx = hx;

                if (west != null) {
                    sx += west.getPreferredSize().width;
                }

                sy = hy;

                if (north != null) {
                    sy += north.getPreferredSize().height;
                }

                sw = hw;

                if (west != null) {
                    sw -= west.getPreferredSize().width;
                }

                if (east != null) {
                    sw -= east.getPreferredSize().width;
                }

                sh = hh;

                if (north != null) {
                    sh -= north.getPreferredSize().height;
                }

                if (south != null) {
                    sh -= south.getPreferredSize().height;
                }
            } else {
                //EAST
                Component north = layout.getLayoutComponent(BorderLayout.NORTH);
                Component south = layout.getLayoutComponent(BorderLayout.SOUTH);

                sx = (hx + hw) - prefSize.width;
                sy = hy;

                if (north != null) {
                    sy += north.getPreferredSize().height;
                }

                sw = prefSize.width;
                sh = hh;

                if (north != null) {
                    sh -= north.getPreferredSize().height;
                }

                if (south != null) {
                    sh -= south.getPreferredSize().height;
                }
            }
        } else {
            //SOUTH
            sx = hx;
            sy = (hy + hh) - prefSize.height;
            sw = hw;
            sh = prefSize.height;
        }

        drawHotspot(g, sx, sy, sw, sh, accept);
    }


}
