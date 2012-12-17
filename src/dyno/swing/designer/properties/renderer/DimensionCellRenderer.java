/*
 * PointCellRenderer.java
 *
 * Created on August 14, 2007, 6:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.DimensionWrapper;



/**
 *
 * @author William Chen
 */
public class DimensionCellRenderer extends EncoderCellRenderer {
    /** Creates a new instance of PointCellRenderer */
    public DimensionCellRenderer() {
        super(new DimensionWrapper());
    }
}
