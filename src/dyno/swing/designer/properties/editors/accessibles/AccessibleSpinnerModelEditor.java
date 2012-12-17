/*
 * AccessibleSpinnerModelEditor.java
 *
 * Created on 2007-9-1, 11:40:29
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.SpinnerModelWrapper;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author William Chen
 */
public class AccessibleSpinnerModelEditor extends UneditableAccessibleEditor {

    private SpinnerModelDialog dialog;

    /** Creates a new instance of AccessibleBorderEditor */
    public AccessibleSpinnerModelEditor() {
        super(new SpinnerModelWrapper());
    }

    protected void popupDialog() {
        if (dialog == null) {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof Frame) {
                dialog = new SpinnerModelDialog((Frame) win, true);
            } else {
                dialog = new SpinnerModelDialog(new Frame(), true);
            }
            dialog.setLocationRelativeTo(this);
        }
        dialog.setValue(getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            Object value=dialog.getValue();
            setValue(value);
            fireStateChanged();
        }
    }
}