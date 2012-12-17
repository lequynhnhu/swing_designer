/*
 * LineBorderCoder.java
 *
 * Created on 2007-9-16, 3:11:40
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers.borders;

import dyno.swing.designer.properties.wrappers.ColorWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.Color;
import javax.swing.border.LineBorder;

/**
 *
 * @author William Chen
 */
public class LineBorderCoder implements SourceCoder {
    private static SourceCoder colorCoder=new ColorWrapper();
    @Override
    public String getJavaCode(Object value) {
        LineBorder lineBorder = (LineBorder) value;
        Color color = lineBorder.getLineColor();
        int thickness = lineBorder.getThickness();
        boolean rounded = lineBorder.getRoundedCorners();
        String cString = colorCoder.getJavaCode(color);
        if (!rounded) {
            if (thickness != 1) {
                return "javax.swing.BorderFactory.createLineBorder(" + cString + ", " + thickness + ")";
            } else {
                return "javax.swing.BorderFactory.createLineBorder(" + cString + ")";
            }
        } else {
            return "new javax.swing.border.LineBorder("+cString+", "+thickness+", "+rounded+")";
        }
    }
}