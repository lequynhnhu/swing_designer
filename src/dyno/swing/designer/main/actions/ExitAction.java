/*
 * ExitAction.java
 * 
 * Created on 2007-9-9, 1:49:13
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.DesigningPanel;
import java.awt.event.ActionEvent;

/**
 *
 * @author rehte
 */
public class ExitAction extends AbstractFileAction{
    public ExitAction(DesigningPanel designer){
        super(designer);
    }
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    protected String getName() {
        return "Exit";
    }
}