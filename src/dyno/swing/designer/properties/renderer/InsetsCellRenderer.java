/*
 * InsetsCellRenderer.java
 * 
 * Created on 2007-8-27, 16:25:14
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.InsetsWrapper;

/**
 *
 * @author William Chen
 */
public class InsetsCellRenderer extends EncoderCellRenderer {
    /** Creates a new instance of PointCellRenderer */
    public InsetsCellRenderer() {
        super(new InsetsWrapper());
    }
}
