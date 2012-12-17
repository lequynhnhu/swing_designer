/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

/**
 *
 * @author William Chen
 */
public class NullPainter extends AbstractPainter {

    public NullPainter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    public void paint(Graphics g) {
        if (isAddingIF()) {
            drawHotspot(g, hotspot_bounds, false);
        } else {
            drawHotspot(g, hotspot_bounds, Color.lightGray);
        }
    }
}
