/*
 * JDesktopPanePainter.java
 * 
 * Created on 2007-9-3, 23:03:51
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.painters;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author William Chen
 */
public class DesktopPanePainter extends AbstractPainter{
    public DesktopPanePainter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Composite composite = g2d.getComposite();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                HOVER_TRANSLUCENCY));
        g.setColor(isAddingIF() ? LAYOUT_HOTSPOT_COLOR : LAYOUT_FORBIDDEN_COLOR);
        g.fillRect(hotspot_bounds.x, hotspot_bounds.y, hotspot_bounds.width,
            hotspot_bounds.height);
        g.setColor(SELECTION_COLOR);
        g.drawRect(hotspot_bounds.x, hotspot_bounds.y, hotspot_bounds.width,
            hotspot_bounds.height);

        g2d.setComposite(composite);
    }
}
