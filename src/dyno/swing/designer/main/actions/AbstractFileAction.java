/*
 * AbstractFileAction.java
 * 
 * Created on 2007-9-9, 1:25:44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.DesigningPanel;
import javax.swing.AbstractAction;

/**
 *
 * @author William Chen
 */
public abstract class AbstractFileAction extends AbstractAction{
    protected DesigningPanel designer;
    public AbstractFileAction(DesigningPanel designer){
        this.designer = designer;
        putValue(NAME, getName());
    }
    protected abstract String getName();
}
