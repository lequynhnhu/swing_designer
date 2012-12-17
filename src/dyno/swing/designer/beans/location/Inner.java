/*
 * Inner.java
 *
 * Created on August 1, 2007, 11:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.location;

import java.awt.Component;
import java.awt.Cursor;


/**
 *
 * @author William Chen
 */
public class Inner implements Direction {
    /** Creates a new instance of Inner */
    public Inner() {
    }

    public void drag(int dx, int dy, Component comp) {
        comp.setLocation(comp.getX() + dx, comp.getY() + dy);
    }

    public int getCursor() {
        return Cursor.HAND_CURSOR;
    }
}
