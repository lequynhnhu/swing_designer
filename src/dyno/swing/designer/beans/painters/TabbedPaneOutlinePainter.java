/*
 * TabbedPaneOutlinePainter.java
 *
 * Created on 2007-8-9, 18:44:22
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Container;
import java.awt.Graphics;

public class TabbedPaneOutlinePainter extends AbstractPainter {

    public TabbedPaneOutlinePainter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    @Override
    public void paint(Graphics g) {
        if (isAddingIF()) {
            drawHotspot(g, hotspot_bounds, false);
        } else {
            drawHotspot(g, hotspot_bounds, true);
        }
    }
}