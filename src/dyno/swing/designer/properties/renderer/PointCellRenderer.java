/*
 * PointCellRenderer.java
 *
 * Created on August 14, 2007, 6:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.PointWrapper;



/**
 *
 * @author William Chen
 */
public class PointCellRenderer extends EncoderCellRenderer {
    /** Creates a new instance of PointCellRenderer */
    public PointCellRenderer() {
        super(new PointWrapper());
    }
}
