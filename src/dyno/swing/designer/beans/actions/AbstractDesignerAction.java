/*
 * AbstractDesignerAction.java
 * 
 * Created on 2007-9-8, 22:43:41
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.events.DesignerEditListener;
import dyno.swing.designer.beans.events.DesignerEvent;
import dyno.swing.designer.beans.events.DesignerStateListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 *
 * @author rehte
 */
public abstract class AbstractDesignerAction extends AbstractAction implements DesignerEditListener, DesignerStateListener{
    protected SwingDesigner designer;
    public AbstractDesignerAction(SwingDesigner designer){
        this.designer=designer;
        putValue(NAME, getName());
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource(getName()+".gif")));
        putValue(SHORT_DESCRIPTION, getDescription());
    }
    protected abstract String getName();
    protected abstract String getDescription();

    public void actionPerformed(ActionEvent e) {
    }
    
    public void componentAdded(DesignerEvent evt) {
    }

    public void componentCanceled(DesignerEvent evt) {
    }

    public void componentCopyed(DesignerEvent evt) {
    }

    public void componentCut(DesignerEvent evt) {
    }

    public void componentDeleted(DesignerEvent evt) {
    }

    public void componentEdited(DesignerEvent evt) {
    }

    public void componentMoved(DesignerEvent evt) {
    }

    public void componentPasted(DesignerEvent evt) {
    }

    public void componentResized(DesignerEvent evt) {
    }

    public void componentSelected(DesignerEvent evt) {
    }

    public void startDesigning(DesignerEvent evt) {
    }

    public void stopDesigning(DesignerEvent evt) {
    }
}
