/*
 * ColorIcon.java
 * 
 * Created on 2007-10-1, 15:52:03
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors.accessibles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 *
 * @author William Chen
 */
public class ColorIcon implements Icon{
    private static int BOX=11;
    static Color BORDER_COLOR=new Color(172, 168, 153);
    private String name;
    private Color color;
    public ColorIcon(Color color){
        this("["+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+"]", color);
    }
    public ColorIcon(String name, Color color){
        this.name=name;
        this.color=color;
    }
    public Color getColor(){
        return color;
    }
    public String toString(){
        return name;
    }
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, getIconWidth(), getIconHeight());
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, getIconWidth(), getIconHeight());
    }

    public int getIconWidth() {
        return BOX;
    }

    public int getIconHeight() {
        return BOX;
    }
}
