/*
 * RightTop.java
 *
 * Created on August 1, 2007, 11:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.location;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;


/**
 *
 * @author William Chen
 */
public class RightTop implements Direction {
    /** Creates a new instance of RightTop */
    public RightTop() {
    }

    public void drag(int dx, int dy, Component comp) {
        Rectangle current_bounds = comp.getBounds();

        current_bounds.y += dy;
        current_bounds.height -= dy;
        current_bounds.width += dx;
        comp.setBounds(current_bounds);
    }

    public int getCursor() {
        return Cursor.NE_RESIZE_CURSOR;
    }
}
