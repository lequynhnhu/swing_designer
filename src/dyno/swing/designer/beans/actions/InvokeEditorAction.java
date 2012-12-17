/*
 * InvokeEditorAction.java
 *
 * Created on 2007-9-9, 11:50:18
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author rehte
 */
public class InvokeEditorAction extends AbstractContextAction {

    private MouseEvent event;

    public InvokeEditorAction(SwingDesigner designer) {
        super(designer);
        putValue(NAME, "Edit Text ...");
    }

    public void actionPerformed(ActionEvent e) {
        event = new MouseEvent(event.getComponent(), event.MOUSE_CLICKED, e.getWhen(), event.getModifiers(), event.getX(), event.getY(), 2, false, event.BUTTON1);
        designer.dispatchEvent(event);
    }

    public void setMouseEvent(MouseEvent e) {
        event = e;
    }
}