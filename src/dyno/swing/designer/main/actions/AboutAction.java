/*
 * AboutAction.java
 * 
 * Created on 2007-9-9, 1:56:04
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
public class AboutAction extends AbstractFileAction{
    public AboutAction(DesigningPanel designer){
        super(designer);
    }
    public void actionPerformed(ActionEvent e) {
    }
    protected String getName() {
        return "About";
    }
}