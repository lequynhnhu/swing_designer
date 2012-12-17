/*
 * Left.java
 *
 * Created on August 1, 2007, 11:36 AM
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
public class Left implements Direction {
    /** Creates a new instance of Left */
    public Left() {
    }

    public void drag(int dx, int dy, Component comp) {
        Rectangle current_bounds = comp.getBounds();

        current_bounds.x += dx;
        current_bounds.width -= dx;
        comp.setBounds(current_bounds);
    }

    public int getCursor() {
        return Cursor.W_RESIZE_CURSOR;
    }
}
