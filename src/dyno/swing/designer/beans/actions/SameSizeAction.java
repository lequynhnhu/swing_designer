/*
 * EditingAction.java
 * 
 * Created on 2007-9-9, 10:49:14
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author rehte
 */
public class SameSizeAction extends AbstractAction implements ActionCategory{
    protected SwingDesigner designer;
    protected Action[] subactions;

    public SameSizeAction(SwingDesigner designer){
        this.designer = designer;
        putValue(NAME, "Same Size");        
        subactions = new Action[]{
            new SameWidthAction(designer),
            new SameHeightAction(designer)
        };
    }
    public void actionPerformed(ActionEvent e) {
    }
    public Action[] getSubActions() {
        return subactions;
    }
}
