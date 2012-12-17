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
public class BottomAlignmentAction extends AbstractDesignerAction {

    public BottomAlignmentAction(SwingDesigner designer) {
        super(designer);
        setEnabled(false);
    }

    protected String getName() {
        return "bottom_align";
    }

    public void actionPerformed(ActionEvent e) {
        if (designer.isAddingMode()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        if (designer.getSelectionModel().isBoundsAdjustable()) {
            designer.getSelectionModel().adjustBottomAlignment();
            designer.setInvalidated(true);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
        designer.repaint();
    }

    protected String getDescription() {
        return "Bottom Alignment";
    }

    public void componentDeleted(DesignerEvent evt) {
        setEnabled(false);
    }

    public void componentSelected(DesignerEvent evt) {
        boolean adjustable = designer.getSelectionModel().isBoundsAdjustable();
        setEnabled(adjustable);
    }

    public void startDesigning(DesignerEvent evt) {
        setEnabled(false);
    }

    public void stopDesigning(DesignerEvent evt) {
        boolean isAdjustable = designer.getSelectionModel().isBoundsAdjustable();
        setEnabled(isAdjustable);
    }
}
