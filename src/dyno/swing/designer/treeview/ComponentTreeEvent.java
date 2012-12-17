/*
 * ComponentTreeEvent.java
 *
 * Created on 2007年8月12日, 下午4:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.treeview;

import java.awt.AWTEvent;


/**
 *
 * @author William Chen
 */
public class ComponentTreeEvent extends AWTEvent {
    /** Creates a new instance of ComponentTreeEvent */
    public ComponentTreeEvent(Object src) {
        super(src, 0);
    }
}
