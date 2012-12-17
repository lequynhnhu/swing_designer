
/*
 * BevelBorderModel.java
 *
 * Created on 2007-8-27, 21:47:22
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author William Chen
 */
public class SoftBevelBorderModel extends BevelBorderModel {

    public SoftBevelBorderModel(SoftBevelBorder border) {
        super(border);
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            switch (row) {
                case 0:
                    int v = SoftBevelBorder.RAISED;
                    if (value != null) {
                        v = ((Number) value).intValue();
                    }
                    border = new SoftBevelBorder(v, border.getHighlightOuterColor(), border.getHighlightInnerColor(), border.getShadowOuterColor(), border.getShadowInnerColor());
                    return true;
                case 1:
                    Color c =(Color) value;
                    border = new SoftBevelBorder(border.getBevelType(), c, border.getHighlightInnerColor(), border.getShadowOuterColor(), border.getShadowInnerColor());
                    return true;
                case 2:
                    c = (Color) value;
                    border = new SoftBevelBorder(border.getBevelType(), border.getHighlightOuterColor(), c, border.getShadowOuterColor(), border.getShadowInnerColor());
                    return true;
                case 3:
                    c = (Color) value;
                    border = new SoftBevelBorder(border.getBevelType(), border.getHighlightOuterColor(), border.getHighlightInnerColor(), border.getShadowOuterColor(), c);
                    return true;
                case 4:
                    c = (Color) value;
                    border = new SoftBevelBorder(border.getBevelType(), border.getHighlightOuterColor(), border.getHighlightInnerColor(), c, border.getShadowInnerColor());
                    return true;
                default:
                    return false;
            }
        }
    }
}