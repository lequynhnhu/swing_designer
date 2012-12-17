/*
 * ComboBoxModelRenderer.java
 * 
 * Created on 2007-8-28, 0:13:57
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.ListModelWrapper;

/**
 *
 * @author William Chen
 */
public class ListModelRenderer extends EncoderCellRenderer {
    public ListModelRenderer(){
        super(new ListModelWrapper());
    }
}
