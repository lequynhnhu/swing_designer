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
public class PasteAction extends AbstractDesignerAction{
    public PasteAction(SwingDesigner designer){
        super(designer);
        setEnabled(false);
    }

    protected String getName() {
        return "paste";
    }

    public void actionPerformed(ActionEvent e) {
        if (designer.isAddingMode()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        designer.getSelectionModel().pasteFromClipBoard();     
    }

    protected String getDescription() {
        return "Paste Components";
    }
    public void componentCopyed(DesignerEvent evt) {
        setEnabled(true);
    }
    public void componentCut(DesignerEvent evt) {
        setEnabled(true);
    }
    public void startDesigning(DesignerEvent evt) {
        setEnabled(false);
    }
    public void stopDesigning(DesignerEvent evt) {
        boolean isPasteable = !designer.getSelectionModel().isClipBoardEmpty();
        setEnabled(isPasteable);
    }
}
