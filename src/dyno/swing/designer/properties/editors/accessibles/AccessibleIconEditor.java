/*
 * AccessibleColorEditor.java
 *
 * Created on 2007��8��13��, ����9:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.IconWrapper;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.Icon;
import javax.swing.SwingUtilities;


/**
 *
 * @author William Chen
 */
public class AccessibleIconEditor extends BaseAccessibleEditor {

    private IconChooserDialog dialog;

    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleIconEditor() {
        super(new IconWrapper(), new IconWrapper(), true);
    }

    protected void popupDialog() {
        if (dialog == null) {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof Frame) {
                dialog = new IconChooserDialog((Frame) win, true);
            } else {
                dialog = new IconChooserDialog(new Frame(), true);
            }
        }
        dialog.setLocationRelativeTo(this);
        dialog.setValue((Icon)getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(dialog.getValue());
            fireStateChanged();
        }
    }
}