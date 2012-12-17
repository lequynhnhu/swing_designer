/*
 * LineBorderCoder.java
 *
 * Created on 2007-9-16, 3:11:40
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers.borders;

import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author William Chen
 */
public class EmptyBorderCoder implements SourceCoder {
    @Override
    public String getJavaCode(Object value) {
        EmptyBorder emptyBorder = (EmptyBorder) value;
        Insets insets=emptyBorder.getBorderInsets();
        if(insets.top == 0 && insets.left == 0 && insets.bottom == 0 && insets.right == 0)
            return "javax.swing.BorderFactory.createEmptyBorder()";
        else
            return "javax.swing.BorderFactory.createEmptyBorder("+insets.top+", "+insets.left+", "+insets.bottom+", "+insets.right+")";
    }
}