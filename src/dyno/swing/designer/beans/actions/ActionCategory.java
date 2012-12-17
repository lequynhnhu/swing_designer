/*
 * ActionCategory.java
 * 
 * Created on 2007-9-9, 10:57:33
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import javax.swing.Action;

/**
 *
 * @author William Chen
 */
public interface ActionCategory {
    Action[] getSubActions();
}
