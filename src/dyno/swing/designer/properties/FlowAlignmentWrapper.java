/*
 * FlowAlignmentWrapper.java
 * 
 * Created on 2007-8-27, 1:31:04
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.items.FlowAlignmentItems;
import dyno.swing.designer.properties.wrappers.ItemWrapper;

/**
 *
 * @author William Chen
 */
public class FlowAlignmentWrapper extends ItemWrapper{
    public FlowAlignmentWrapper() {
        super(new FlowAlignmentItems());
    }
}
