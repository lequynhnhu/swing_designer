/*
 * HorizontalAlignmentRenderer.java
 * 
 * Created on 2007-8-19, 16:47:55
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.EventHandlerWrapper;

/**
 *
 * @author William Chen
 */
public class EventHandlerRenderer extends EncoderCellRenderer{
    public EventHandlerRenderer(){
        super(new EventHandlerWrapper());
    }
}
