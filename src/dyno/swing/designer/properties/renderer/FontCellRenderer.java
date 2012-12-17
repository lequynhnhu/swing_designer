/*
 * FontCellRenderer.java
 *
 * Created on 2007年8月13日, 下午7:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;


import dyno.swing.designer.properties.wrappers.FontWrapper;
import dyno.swing.designer.properties.wrappers.Encoder;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/**
 *
 * @author William Chen
 */
public class FontCellRenderer extends GenericCellRenderer {
    private static Encoder wrapper = new FontWrapper();
    private Font fontValue;

    /** Creates a new instance of ColorCellRenderer */
    public FontCellRenderer() {
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);

        g.setColor(getForeground());

        FontMetrics fm = g.getFontMetrics();
        int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(getFontText(), 0, y);

        if (getBorder() != null) {
            getBorder().paintBorder(this, g, 0, 0, width, height);
        }
    }

    private String getFontText() {
        return (fontValue == null) ? "" : wrapper.encode(fontValue);
    }

    public void setValue(Object value) {
        Font font = (Font) value;

        if (font != null) {
            fontValue = font;
        } else {
            fontValue = getFont();
        }
    }
}
