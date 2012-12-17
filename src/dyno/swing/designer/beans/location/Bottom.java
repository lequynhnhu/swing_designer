/*
 * Bottom.java
 *
 * Created on August 1, 2007, 11:35 AM
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
public class Bottom implements Direction {
    /** Creates a new instance of Bottom */
    public Bottom() {
    }

    public void drag(int dx, int dy, Component comp) {
        Rectangle current_bounds = comp.getBounds();

        current_bounds.height += dy;
        comp.setBounds(current_bounds);
    }

    public int getCursor() {
        return Cursor.S_RESIZE_CURSOR;
    }
}
