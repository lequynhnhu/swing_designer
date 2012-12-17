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
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author rehte
 */
public class OpenFileAction extends AbstractFileAction {

    public OpenFileAction(DesigningPanel designer) {
        super(designer);
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("open.png")));
    }

    public void actionPerformed(ActionEvent e) {
        if (openDialog == null) {
            openDialog = new JFileChooser();
            openDialog.setFileFilter(new FileFilter() {

                public boolean accept(File f) {
                    String name = f.getName();
                    return f.isDirectory() || name.endsWith(".java");
                }

                public String getDescription() {
                    return "*.java";
                }
            });
            openDialog.setCurrentDirectory(new File("."));
        }
        if (openDialog.showOpenDialog(designer) == JFileChooser.APPROVE_OPTION) {
            new OpenFileWorker(designer, openDialog.getSelectedFile()).execute();
        }
    }
    private JFileChooser openDialog;

    protected String getName() {
        return "Open JPanel From ...";
    }
}