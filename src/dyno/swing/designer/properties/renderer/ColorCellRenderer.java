/*
 * ColorCellRenderer.java
 *
 * Created on 2007年8月13日, 下午6:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.ColorWrapper;
import dyno.swing.designer.properties.wrappers.Encoder;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author William Chen
 */
public class ColorCellRenderer extends GenericCellRenderer {

    private static Encoder wrapper = new ColorWrapper();
    private static int BOX = 12;
    private static int LEFT = 4;
    private static int ICON_TEXT_PAD = 4;
    private Color color;

    /** Creates a new instance of ColorCellRenderer */
    public ColorCellRenderer() {
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);
        int x = 0;
        int y = (height - BOX) / 2;
        if (color != null) {
            x+=LEFT;
            g.setColor(color);
            g.fillRect(x, y, BOX, BOX);
            g.setColor(getForeground());
            g.drawRect(x, y, BOX, BOX);
            x += (BOX + ICON_TEXT_PAD);
        } else {
            g.setColor(getForeground());
        }
        FontMetrics fm = g.getFontMetrics();
        y = ((height - fm.getHeight()) / 2) + fm.getAscent();
        String colorText = getColorText();
        g.drawString(colorText, x, y);
        if (getBorder() != null) {
            getBorder().paintBorder(this, g, 0, 0, width, height);
        }
    }

    private String getColorText() {
        return wrapper.encode(color);
    }

    public void setValue(Object value) {
        Color color = (Color) value;

        this.color = color;
    }
}