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
import dyno.swing.designer.beans.toolkit.ComponentPalette;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

/**
 *
 * @author rehte
 */
public class PointerAction extends AbstractDesignerAction{
    private ComponentPalette palette;
    public PointerAction(SwingDesigner designer, ComponentPalette palette){
        super(designer);
        this.palette = palette;
        setEnabled(false);
    }

    protected String getName() {
        return "pointer";
    }

    public void actionPerformed(ActionEvent e) {
        if (!designer.isAddingMode()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        designer.stopAddingState();
        palette.reset();
    }
    protected String getDescription() {
        return "Selection Mode";
    }
    public void componentDeleted(DesignerEvent evt) {
        setEnabled(false);
    }
    public void startDesigning(DesignerEvent evt) {
        setEnabled(true);
    }
    public void stopDesigning(DesignerEvent evt) {
        setEnabled(false);
    }
}
