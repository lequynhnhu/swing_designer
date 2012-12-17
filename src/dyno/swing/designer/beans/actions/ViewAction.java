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
import dyno.swing.designer.beans.toolkit.ComponentPalette;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author rehte
 */
public class ViewAction extends AbstractAction implements ActionCategory{
    protected SwingDesigner designer;
    protected Action[] subactions;

    public ViewAction(SwingDesigner designer, ComponentPalette palette){
        this.designer = designer;
        putValue(NAME, "View");        
        subactions = new Action[]{
            new PointerAction(designer, palette),
            new PreviewAction(designer),
        };
    }
    public void actionPerformed(ActionEvent e) {
    }
    public Action[] getSubActions() {
        return subactions;
    }
}
