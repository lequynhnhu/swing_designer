/*
 * RectangleCellRenderer.java
 *
 * Created on August 14, 2007, 6:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.wrappers.RectangleWrapper;



/**
 *
 * @author William Chen
 */
public class RectangleCellRenderer extends EncoderCellRenderer {
    /** Creates a new instance of PointCellRenderer */
    public RectangleCellRenderer() {
        super(new RectangleWrapper());
    }
}
