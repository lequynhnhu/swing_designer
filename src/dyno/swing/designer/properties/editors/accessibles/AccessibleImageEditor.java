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
import dyno.swing.designer.properties.types.DesignTimeImage;
import dyno.swing.designer.properties.wrappers.ImageWrapper;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import javax.swing.SwingUtilities;


/**
 *
 * @author William Chen
 */
public class AccessibleImageEditor extends BaseAccessibleEditor {

    private ImageChooserDialog dialog;

    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleImageEditor() {
        super(new ImageWrapper(), new ImageWrapper(), true);
    }

    protected void popupDialog() {
        if (dialog == null) {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof Frame) {
                dialog = new ImageChooserDialog((Frame) win, true);
            } else if(win instanceof Dialog) {
                dialog = new ImageChooserDialog((Dialog)win, true);
            } else {
                dialog = new ImageChooserDialog(new Frame(), true);
            }
        }
        dialog.setLocationRelativeTo(this);
        dialog.setValue((Image)getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(((DesignTimeImage)dialog.getValue()).getImage());
            fireStateChanged();
        }
    }
}