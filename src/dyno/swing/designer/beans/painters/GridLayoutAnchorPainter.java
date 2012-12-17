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
import java.awt.GridLayout;
import java.awt.Stroke;

/**
 *
 * @author William Chen
 */
public class GridLayoutAnchorPainter extends AbstractAnchorPainter{
    protected GridLayout grid_layout;
    public GridLayoutAnchorPainter(SwingDesigner designer, Container container) {
        super(designer, container);
        grid_layout=(GridLayout)container.getLayout();
    }
    
    public void paint(Graphics g) {
        int columns=grid_layout.getColumns();
        int rows=grid_layout.getRows();
        int width=hotspot.width;
        int height=hotspot.height;
        Graphics2D g2d=(Graphics2D)g;
        Stroke back=g2d.getStroke();
        g2d.setStroke(STROKE2);
        g.setColor(Color.green);
        g.drawRect(hotspot.x, hotspot.y, hotspot.width, hotspot.height);
        
        if(columns!=0){
            for(int i=1;i<columns;i++){
                int x=(i*width)/columns;
                g.drawLine(hotspot.x+x, hotspot.y, hotspot.x+x, hotspot.y+height);
            }
        }
        if(rows!=0){
            for(int i=1;i<rows;i++){
                int y=(i*height)/rows;
                g.drawLine(hotspot.x, hotspot.y+y, hotspot.x+width, hotspot.y+y);
            }
        }
        g2d.setStroke(back);
    }
}
