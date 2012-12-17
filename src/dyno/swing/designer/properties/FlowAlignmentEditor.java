/*
 * BorderConstraintsEditor.java
 * 
 * Created on 2007-8-26, 22:33:13
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.items.FlowAlignmentItems;

/**
 *
 * @author William Chen
 */
public class FlowAlignmentEditor extends EnumerationEditor{
    public FlowAlignmentEditor(){
        super(new FlowAlignmentItems());
    }
}
