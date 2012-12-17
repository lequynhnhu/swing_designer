/*
 * LineBorderCoder.java
 *
 * Created on 2007-9-16, 3:11:40
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers.borders;

import dyno.swing.designer.properties.items.EtchedBorderTypeItems;
import dyno.swing.designer.properties.wrappers.ColorWrapper;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author William Chen
 */
public class EtchedBorderCoder implements SourceCoder {

    private static SourceCoder color_coder = new ColorWrapper();
    private static SourceCoder coder = new ItemWrapper(new EtchedBorderTypeItems());

    @Override
    public String getJavaCode(Object value) {
        EtchedBorder etchedBorder = (EtchedBorder) value;
        int etchedType = etchedBorder.getEtchType();
        Color highlightColor = etchedBorder.getHighlightColor();
        Color shadowColor = etchedBorder.getShadowColor();
        if (highlightColor == null && shadowColor == null) {
            if (etchedType == EtchedBorder.LOWERED) {
                return "javax.swing.BorderFactory.createEtchedBorder()";
            } else {
                return "javax.swing.BorderFactory.createEtchedBorder(" + coder.getJavaCode(etchedType) + ")";
            }
        } else {
            if (etchedType == EtchedBorder.LOWERED) {
                return "javax.swing.BorderFactory.createEtchedBorder(" + color_coder.getJavaCode(highlightColor) + ", " + color_coder.getJavaCode(shadowColor) + ")";
            } else {
                return "javax.swing.BorderFactory.createEtchedBorder(" + coder.getJavaCode(etchedType) + ", " + color_coder.getJavaCode(highlightColor) + ", " + color_coder.getJavaCode(shadowColor) + ")";
            }
        }
    }
}