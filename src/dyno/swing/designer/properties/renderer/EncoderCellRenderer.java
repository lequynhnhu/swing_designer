/*
 * GeometryCellRenderer.java
 *
 * Created on August 14, 2007, 6:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.Encoder;
import java.awt.FontMetrics;
import java.awt.Graphics;


/**
 *
 * @author William Chen
 */
public class EncoderCellRenderer extends GenericCellRenderer {

    private static int LEFT = 1;
    protected Encoder encoder;
    protected Object value;

    /** Creates a new instance of ColorCellRenderer */
    public EncoderCellRenderer(Encoder encoder) {
        this.encoder = encoder;
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);

        int x = LEFT;
        g.setColor(getForeground());

        FontMetrics fm = g.getFontMetrics();
        int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
        String txt = getValueText();
        if (txt != null) {
            g.drawString(txt, x, y);
        }
        if (getBorder() != null) {
            getBorder().paintBorder(this, g, 0, 0, width, height);
        }
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private String getValueText() {
        return encoder.encode(value);
    }
}