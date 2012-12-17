/*
 * Top.java
 *
 * Created on August 1, 2007, 11:32 AM
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
public class Top implements Direction {
    /** Creates a new instance of Top */
    public Top() {
    }

    public void drag(int dx, int dy, Component comp) {
        Rectangle current_bounds = comp.getBounds();

        current_bounds.y += dy;
        current_bounds.height -= dy;
        comp.setBounds(current_bounds);
    }

    public int getCursor() {
        return Cursor.N_RESIZE_CURSOR;
    }
}
