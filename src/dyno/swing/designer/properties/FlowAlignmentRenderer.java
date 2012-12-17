/*
 * FlowAlignmentRenderer.java
 * 
 * Created on 2007-8-27, 1:34:02
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.renderer.EncoderCellRenderer;

/**
 *
 * @author William Chen
 */
public class FlowAlignmentRenderer extends EncoderCellRenderer{
    public FlowAlignmentRenderer(){
        super(new FlowAlignmentWrapper());
    }
}
