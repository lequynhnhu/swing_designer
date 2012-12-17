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
import dyno.swing.designer.properties.wrappers.InsetsWrapper;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.SwingUtilities;


/**
 *
 * @author William Chen
 */
public class AccessibleInsetsEditor extends BaseAccessibleEditor {

    private InsetsEditDialog dialog;
    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleInsetsEditor() {
        super(new InsetsWrapper(), new InsetsWrapper(), true);
    }

    protected void popupDialog() {
        if(dialog==null){
            Window win=SwingUtilities.getWindowAncestor(this);
            if(win instanceof Frame){
                dialog=new InsetsEditDialog((Frame)win, true);
            }else{
                dialog=new InsetsEditDialog(new Frame(), true);
            }
            dialog.setLocationRelativeTo(this);
        }
        dialog.setValue((Insets)getValue());
        dialog.setVisible(true);
        if(dialog.isOK()){
            setValue(dialog.getValue());
            fireStateChanged();
        }
    }
}