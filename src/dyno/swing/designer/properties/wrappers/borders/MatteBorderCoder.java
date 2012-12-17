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
import dyno.swing.designer.properties.wrappers.IconWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.MatteBorder;

/**
 *
 * @author William Chen
 */
public class MatteBorderCoder implements SourceCoder {

    private static SourceCoder color_coder = new ColorWrapper();
    private static SourceCoder icon_coder = new IconWrapper();

    @Override
    public String getJavaCode(Object value) {
        MatteBorder matteBorder = (MatteBorder) value;
        Color matteColor=matteBorder.getMatteColor();
        Icon icon=matteBorder.getTileIcon();
        String matteColorString=color_coder.getJavaCode(matteColor);
        String iconString=icon_coder.getJavaCode(icon);
        Insets insets=matteBorder.getBorderInsets();
        if(matteColor==null){
            return "javax.swing.BorderFactory.createMatteBorder("+insets.top+", "+insets.left+", "+insets.bottom+", "+insets.right+", "+matteColorString+")";
        }else{
            return "javax.swing.BorderFactory.createMatteBorder("+insets.top+", "+insets.left+", "+insets.bottom+", "+insets.right+", "+iconString+")";
        }
    }
}