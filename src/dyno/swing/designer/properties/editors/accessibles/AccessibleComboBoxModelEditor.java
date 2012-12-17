/*
 * AccessibleComboBoxModelEditor.java
 *
 * Created on 2007-8-28, 0:45:25
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.ComboBoxModelWrapper;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.MutableComboBoxModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author William Chen
 */
public class AccessibleComboBoxModelEditor extends UneditableAccessibleEditor {

    private ComboBoxModelDialog dialog;

    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleComboBoxModelEditor() {
        super(new ComboBoxModelWrapper());
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
            dialog = new ComboBoxModelDialog(frame, true);
            dialog.setElementWrapper(((ComboBoxModelWrapper) encoder).getElementEncoder());
            dialog.setLocationRelativeTo(this);
        }
        dialog.setModel((MutableComboBoxModel) getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(dialog.getModel());
            fireStateChanged();
        }
    }
}