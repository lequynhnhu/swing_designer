/*
 * AccessibleEventCodeEditor.java
 * 
 * Created on 2007-9-5, 22:17:11
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.types.EventHandler;
import dyno.swing.designer.properties.wrappers.EventHandlerWrapper;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author William Chen
 */
public class AccessibleEventCodeEditor extends UneditableAccessibleEditor {

    private EventHandlerCodeDialog dialog;
    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleEventCodeEditor() {
        super(new EventHandlerWrapper());
    }

    protected void popupDialog() {
        if (dialog == null) {
            Frame frame;
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof Frame) {
                frame = (Frame) win;
            } else {
                frame = new Frame();
            }
            dialog = new EventHandlerCodeDialog(frame, true);
            dialog.setLocationRelativeTo(this);
        }
        dialog.setEventCode((EventHandler) getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(dialog.getEventCode());
            fireStateChanged();
        }
    }
}
