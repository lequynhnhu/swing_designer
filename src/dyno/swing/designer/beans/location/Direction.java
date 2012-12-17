/*
 * Location.java
 *
 * Created on August 1, 2007, 11:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.location;

import java.awt.Component;


/**
 *
 * @author William Chen
 */
public interface Direction {
    void drag(int dx, int dy, Component comp);

    int getCursor();
}
