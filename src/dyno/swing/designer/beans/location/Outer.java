/*
 * Outer.java
 *
 * Created on August 1, 2007, 11:28 AM
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
public class Outer implements Direction {
    /** Creates a new instance of Outer */
    public Outer() {
    }

    public void drag(int dx, int dy, Component comp) {
    }

    public int getCursor() {
        return Cursor.DEFAULT_CURSOR;
    }
}
