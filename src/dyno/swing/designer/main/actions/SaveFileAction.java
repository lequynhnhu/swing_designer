/*
 * SaveFileAction.java
 *
 * Created on 2007-9-9, 1:42:47
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.DesigningPanel;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author William Chen
 */
public class SaveFileAction extends AbstractFileAction {

    public SaveFileAction(DesigningPanel designer) {
        super(designer);
        putValue(SMALL_ICON, new ImageIcon(getClass().getResource("save.png")));
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if(designer.isDesigningGUI()){
            new SourceWorker(designer).execute();
        }else{
            new SourceParser(designer).execute();
        }
    }

    protected String getName() {
        return "Save JPanel";
    }
}