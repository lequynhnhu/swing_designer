/*
 * NewFileAction.java
 *
 * Created on 2007-9-9, 1:29:11
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.DesigningPanel;
import dyno.swing.designer.properties.types.Item;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author rehte
 */
public class NewFileAction extends AbstractFileAction {

    private NewPanelDialog dialog;

    public NewFileAction(DesigningPanel designer) {
        super(designer);
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("new.png")));
    }

    public void actionPerformed(ActionEvent e) {
        Window win = SwingUtilities.getWindowAncestor(designer);
        if (win instanceof Frame) {
            dialog = new NewPanelDialog((Frame) win, true);
        } else {
            dialog = new NewPanelDialog(new Frame(), true);
        }
        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);
        if (dialog.isOK()) {
            String className = dialog.getClassName();
            String packager = dialog.getPackageName();
            Item type=dialog.getSelectedType();
            new NewFileWorker(designer, packager, className, type).execute();
        }
    }

    protected String getName() {
        return "New JPanel";
    }
}