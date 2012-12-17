/*
 * WindowBorder.java
 *
 * Created on 2007-10-1, 21:28:23
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public class FrameBorder implements Border {

    private static int ICON_PAD = 2;
    private static int ICON_TEXT_PAD = 4;
    private static int OUTER_PAD = 4;
    private static int TITLE_HEIGHT = 27;
    private static Image TL_CORNER;
    private static Image TOP_RIGHT;
    private static Image BOTTOM_LEFT;
    private static Image BOTTOM_RIGHT;
    private static Image TOP_BORDER;
    private static Image LEFT_BORDER;
    private static Image BOTTOM_BORDER;
    private static Image RIGHT_BORDER;
    private static Image TITLE_PANE;
    private static Image CONTROL_CLOSE;
    private static Image CONTROL_MIN;
    private static Image CONTROL_MAX;
    private static Color BEGIN_COLOR;
    private static Color END_COLOR;
    private static Color CONTROL_COLOR;
    static {
        boolean isXP = Util.isXP();
        String ext = "";
        if (isXP) {
            TITLE_HEIGHT = 27;
        } else {
            TITLE_HEIGHT = 19;
            ext = "_";
            BEGIN_COLOR = new Color(10, 36, 106);
            END_COLOR = new Color(166, 202, 240);
            CONTROL_COLOR = new Color(212, 208, 200);
        }
        MediaTracker tracker = new MediaTracker(new JFrame());
        TOP_BORDER = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("top_border" + ext + ".png"));
        tracker.addImage(TOP_BORDER, 0);
        LEFT_BORDER = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("left_border" + ext + ".png"));
        tracker.addImage(LEFT_BORDER, 1);
        BOTTOM_BORDER = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("bottom_border" + ext + ".png"));
        tracker.addImage(BOTTOM_BORDER, 2);
        RIGHT_BORDER = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("right_border" + ext + ".png"));
        tracker.addImage(RIGHT_BORDER, 3);
        TL_CORNER = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("top_left" + ext + ".png"));
        tracker.addImage(TL_CORNER, 4);
        TOP_RIGHT = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("top_right" + ext + ".png"));
        tracker.addImage(TOP_RIGHT, 5);
        BOTTOM_LEFT = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("bottom_left" + ext + ".png"));
        tracker.addImage(BOTTOM_LEFT, 6);
        BOTTOM_RIGHT = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("bottom_right" + ext + ".png"));
        tracker.addImage(BOTTOM_RIGHT, 7);

        TITLE_PANE = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("title_pane.png"));
        tracker.addImage(TITLE_PANE, 8);
        CONTROL_CLOSE = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("control_close" + ext + ".png"));
        tracker.addImage(CONTROL_CLOSE, 10);
        CONTROL_MIN = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("control_min" + ext + ".png"));
        tracker.addImage(CONTROL_MIN, 11);
        CONTROL_MAX = Toolkit.getDefaultToolkit().getImage(FrameBorder.class.getResource("control_max" + ext + ".png"));
        tracker.addImage(CONTROL_MAX, 12);
        while (!tracker.checkAll()) {
            try {
                tracker.waitForAll();
            } catch (Exception e) {
            }
        }
    }
    private JFrame jframe;
    //private JInternalFrame frame;

    private SwingDesigner designer;

    public FrameBorder(SwingDesigner designer, JFrame jframe) {
        this.designer = designer;
        this.jframe = jframe;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int gx = x + OUTER_PAD;
        int gy = y;
        int gw = jframe.getWidth();
        int gh = jframe.getHeight();
        g.drawImage(TOP_BORDER, gx, gy, gw - 2 * OUTER_PAD, OUTER_PAD, designer);
        gx = x;
        gy = y + OUTER_PAD;
        g.drawImage(LEFT_BORDER, gx, gy, OUTER_PAD, gh - 2 * OUTER_PAD, designer);
        gx = x + OUTER_PAD;
        gy = y + gh - OUTER_PAD;
        g.drawImage(BOTTOM_BORDER, gx, gy, gw - 2 * OUTER_PAD, OUTER_PAD, designer);
        gx = x + gw - OUTER_PAD;
        gy = y + OUTER_PAD;
        g.drawImage(RIGHT_BORDER, gx, gy, OUTER_PAD, gh - 2 * OUTER_PAD, designer);

        gx = x;
        gy = y;
        g.drawImage(TL_CORNER, gx, gy, designer);
        gx = x;
        gy = y + gh - OUTER_PAD;
        g.drawImage(BOTTOM_LEFT, gx, gy, designer);
        gx = x + gw - OUTER_PAD;
        gy = y + gh - OUTER_PAD;
        g.drawImage(BOTTOM_RIGHT, gx, gy, designer);
        gx = x + gw - OUTER_PAD;
        gy = y;
        g.drawImage(TOP_RIGHT, gx, gy, designer);

        gx = x + OUTER_PAD;
        gy = y + OUTER_PAD;
        if (Util.isXP()) {
            g.drawImage(TITLE_PANE, gx, gy, gw - 2 * OUTER_PAD, TITLE_HEIGHT, designer);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(gx, gy, BEGIN_COLOR, gx + gw - 2 * OUTER_PAD, gy, END_COLOR);
            Paint p = g2d.getPaint();
            g2d.setPaint(gp);
            g2d.fillRect(gx, gy, gw - 2 * OUTER_PAD, TITLE_HEIGHT - 1);
            g2d.setPaint(p);
            g2d.setColor(CONTROL_COLOR);
            g2d.drawLine(gx, gy+TITLE_HEIGHT-1, gx+gw-2*OUTER_PAD, gy+TITLE_HEIGHT-1);
        }

        Image image = jframe.getIconImage();
        if (image == null) {
            Window win = SwingUtilities.getWindowAncestor(designer);
            if (win instanceof Frame) {
                image = ((Frame) win).getIconImage();
            }
        }
        if (image != null) {
            gx = x + OUTER_PAD + ICON_PAD;
            gy = y + OUTER_PAD + (TITLE_HEIGHT - image.getHeight(designer)) / 2;
            g.drawImage(image, gx, gy, designer);
        }

        String title = jframe.getTitle();
        if (!Util.isStringNull(title)) {
            int image_width = image == null ? 0 : image.getWidth(designer);
            gx = x + OUTER_PAD + ICON_PAD + image_width + ICON_TEXT_PAD;
            FontMetrics fm = g.getFontMetrics();
            gy = y + OUTER_PAD + (TITLE_HEIGHT - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(title, gx, gy);
        }

        int w = CONTROL_CLOSE.getWidth(designer);
        //int h = CONTROLS.getHeight(designer);
        gx = x + gw - OUTER_PAD - w - 2;
        gy = y + OUTER_PAD + 2;
        g.drawImage(CONTROL_CLOSE, gx, gy, designer);
        w = CONTROL_MAX.getWidth(designer);
        gx = gx - w - 2;
        g.drawImage(CONTROL_MAX, gx, gy, designer);
        w = CONTROL_MIN.getWidth(designer);
        if (Util.isXP()) {
            gx = gx - w - 2;
        } else {
            gx = gx - w;
        }
        g.drawImage(CONTROL_MIN, gx, gy, designer);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(OUTER_PAD + TITLE_HEIGHT, OUTER_PAD, OUTER_PAD, OUTER_PAD);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}