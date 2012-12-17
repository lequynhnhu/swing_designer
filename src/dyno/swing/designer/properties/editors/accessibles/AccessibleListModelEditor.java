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
import dyno.swing.designer.properties.wrappers.ListModelWrapper;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author William Chen
 */
public class AccessibleListModelEditor extends UneditableAccessibleEditor {

    private ListModelDialog dialog;

    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleListModelEditor() {
        super(new ListModelWrapper());
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
            dialog = new ListModelDialog(frame, true);
            dialog.setElementWrapper(((ListModelWrapper) encoder).getElementWrapper());
            dialog.setLocationRelativeTo(this);
        }
        dialog.setModel((ListModel) getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(dialog.getModel());
            fireStateChanged();
        }
    }
}