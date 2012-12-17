/*
 * SaveAsFileAction.java
 * 
 * Created on 2007-9-9, 1:44:34
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.DesigningPanel;

/**
 *
 * @author rehte
 */
public class SaveAsFileAction extends SaveFileAction{
    public SaveAsFileAction(DesigningPanel designer){
        super(designer);
        putValue(SMALL_ICON, null);
    }
    protected String getName() {
        return "Save JPanel As ...";
    }    
}
