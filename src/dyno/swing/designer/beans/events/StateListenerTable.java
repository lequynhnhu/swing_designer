/*
 * DesignerStateWhiteBoard.java
 * 
 * Created on 2007-8-17, 14:46:01
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.events;

import java.util.EventListener;

/**
 *
 * @author William Chen
 */
public class StateListenerTable extends EventListenerTable{
    public void fireStartDesigning(DesignerEvent evt){
        for(EventListener listener:listeners){
            ((DesignerStateListener)listener).startDesigning(evt);
        }
    }
    public void fireStopDesigning(DesignerEvent evt){
        for(EventListener listener:listeners){
            ((DesignerStateListener)listener).stopDesigning(evt);
        }
    }
}
