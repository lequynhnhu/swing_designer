/*
 * CopyKey.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.keys;

import dyno.swing.designer.beans.SwingDesigner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author William Chen
 */
public class CopyKey implements KeyListener {
    private SwingDesigner designer;
    /** Creates a new instance of CopyKey */
    public CopyKey(SwingDesigner designer) {
        this.designer=designer;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        designer.getSelectionModel()
         .copySelectedComponent2ClipBoard();
    }
}
