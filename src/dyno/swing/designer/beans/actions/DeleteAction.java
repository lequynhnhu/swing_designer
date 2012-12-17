/*
 * CutAction.java
 * 
 * Created on 2007-9-8, 22:44:46
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

/**
 *
 * @author rehte
 */
public class DeleteAction extends AbstractDesignerAction{
    public DeleteAction(SwingDesigner designer){
        super(designer);
        setEnabled(false);
    }

    protected String getName() {
        return "delete";
    }

    public void actionPerformed(ActionEvent e) {
        if (designer.isAddingMode()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        designer.getSelectionModel().deleteSelection();
    }

    protected String getDescription() {
        return "Delete Selected Components";
    }
    public void componentDeleted(DesignerEvent evt) {
        setEnabled(false);
    }
    public void componentCut(DesignerEvent evt) {
        setEnabled(false);
    }
    public void componentSelected(DesignerEvent evt) {
        boolean has = designer.getSelectionModel().hasSelection();
        setEnabled(has && !designer.getSelectionModel().isRootSelected());
    }
    public void startDesigning(DesignerEvent evt) {
        setEnabled(false);
    }
    public void stopDesigning(DesignerEvent evt) {
        boolean hasSelection = designer.getSelectionModel().hasSelection();
        setEnabled(hasSelection);
    }
}
