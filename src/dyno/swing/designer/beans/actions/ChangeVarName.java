/*
 * RenameVarNameAction.java
 *
 * Created on 2007-8-16, 17:26:09
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author William Chen
 */
public class ChangeVarName extends AbstractContextAction {
    public ChangeVarName(SwingDesigner designer){
        super(designer);
        putValue(NAME, "Change variable name ...");
    }
    public void actionPerformed(ActionEvent e) {
        String value = JOptionPane.showInputDialog(designer, "Please enter a new variable name for this component.", Util.getComponentName(component));
        if (value != null) {
            Util.setComponentName(component, value);
            DesignerEvent evt = new DesignerEvent(designer);
            evt.setAffectedComponent(component);
            designer.getEditListenerTable().fireComponentEdited(evt);
        }
    }
}
