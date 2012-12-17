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
public class AlignmentAction extends AbstractAction implements ActionCategory {

    protected SwingDesigner designer;
    protected Action[] subactions;

    public AlignmentAction(SwingDesigner designer) {
        this.designer = designer;
        putValue(NAME, "Alignment");
        subactions = new Action[]{
            new LeftAlignmentAction(designer), 
            new CenterAlignmentAction(designer), 
            new RightAlignmentAction(designer), 
            new TopAlignmentAction(designer), 
            new MiddleAlignmentAction(designer), 
            new BottomAlignmentAction(designer)
        };
    }

    public void actionPerformed(ActionEvent e) {
    }

    public Action[] getSubActions() {
        return subactions;
    }
}