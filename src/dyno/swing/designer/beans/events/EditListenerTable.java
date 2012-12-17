/*
 * DesignerEditWhiteBoard.java
 *
 * Created on 2007-8-17, 14:39:12
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.events;

import java.lang.ref.WeakReference;
import java.util.EventListener;

/**
 *
 * @author William Chen
 */
public class EditListenerTable extends EventListenerTable {

    public void fireComponentAdded(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentAdded(evt);
        }
    }
    public void fireComponentDeleted(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentDeleted(evt);
        }
    }
    public void fireComponentCanceled(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentCanceled(evt);
        }
    }
    public void fireComponentCopyed(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentCopyed(evt);
        }
    }
    public void fireComponentCut(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentCut(evt);
        }
    }
    public void fireComponentPasted(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentPasted(evt);
        }
    }

    public void fireComponentSelected(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentSelected(evt);
        }
    }
    public void fireComponentEdited(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentEdited(evt);
        }
    }
    public void fireComponentResized(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentResized(evt);
        }
    }
    public void fireComponentMoved(DesignerEvent evt) {
        for(EventListener listener:listeners){
            ((DesignerEditListener)listener).componentMoved(evt);
        }
    }
}
