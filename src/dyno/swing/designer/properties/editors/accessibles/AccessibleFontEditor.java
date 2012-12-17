/*
 * AccessibleFontEditor.java
 *
 * Created on 2007��8��13��, ����9:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.FontWrapper;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author William Chen
 */
public class AccessibleFontEditor extends BaseAccessibleEditor {

    private FontChooserDialog fontDialog;

    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleFontEditor() {
        super(new FontWrapper(), new FontWrapper(), true);
    }

    protected void popupDialog() {
        if (fontDialog == null) {
            Window win = SwingUtilities.getWindowAncestor(this);
            fontDialog = new FontChooserDialog(win, true);
        }

        fontDialog.setFontValue((Font) getValue());
        fontDialog.setLocationRelativeTo(this);
        fontDialog.setVisible(true);

        if (fontDialog.isOK()) {
            setValue(fontDialog.getFontValue());
            fireStateChanged();
        }
    }
}
