/*
 * ComponentTreeListener.java
 *
 * Created on 2007年8月12日, 下午4:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.treeview;

import java.util.EventListener;


/**
 *
 * @author William Chen
 */
public interface ComponentTreeListener extends EventListener {
    void treeChanged(ComponentTreeEvent evt);
}
