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
public class EditingAction extends AbstractAction implements ActionCategory{
    protected SwingDesigner designer;
    protected Action[] subactions;

    public EditingAction(SwingDesigner designer){
        this.designer = designer;
        putValue(NAME, "Edit");        
        subactions = new Action[]{
            new CutAction(designer), 
            new CopyAction(designer), 
            new PasteAction(designer), 
            new DeleteAction(designer)         
        };
    }
    public void actionPerformed(ActionEvent e) {
    }
    public Action[] getSubActions() {
        return subactions;
    }
}
