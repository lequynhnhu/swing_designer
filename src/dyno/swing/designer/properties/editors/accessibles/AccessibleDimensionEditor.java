/*
 * AccessibleColorEditor.java
 *
 * Created on 2007��8��13��, ����9:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.accessibles.BaseAccessibleEditor;
import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.DimensionWrapper;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;


/**
 *
 * @author William Chen
 */
public class AccessibleDimensionEditor extends BaseAccessibleEditor {

    private DimensionEditingDialog dialog;

    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleDimensionEditor() {
        super(new DimensionWrapper(), new DimensionWrapper(), true);
    }

    protected void popupDialog() {
        if (dialog == null) {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof Frame) {
                dialog = new DimensionEditingDialog((Frame) win, true);
            } else {
                dialog = new DimensionEditingDialog(new Frame(), true);
            }
            dialog.setLocationRelativeTo(this);
        }
        dialog.setValue((Dimension)getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(dialog.getValue());
            fireStateChanged();
        }
    }
}