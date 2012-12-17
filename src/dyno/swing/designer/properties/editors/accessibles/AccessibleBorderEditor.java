/*
 * AccessibleBorderEditor.java
 *
 * Created on August 14, 2007, 6:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.BorderWrapper;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public class AccessibleBorderEditor extends UneditableAccessibleEditor {

    private BorderDefinitionDialog dialog;

    /** Creates a new instance of AccessibleBorderEditor */
    public AccessibleBorderEditor() {
        super(new BorderWrapper());
    }

    protected void popupDialog() {
        if (dialog == null) {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof Frame) {
                dialog = new BorderDefinitionDialog((Frame) win, true);
            } else {
                dialog = new BorderDefinitionDialog(new Frame(), true);
            }
            dialog.setLocationRelativeTo(this);
        }
        dialog.setValue((Border) getValue());
        dialog.setVisible(true);
        if (dialog.isOK()) {
            setValue(dialog.getValue());
            fireStateChanged();
        }
    }

}