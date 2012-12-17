/*
 * BevelBorderCoder.java
 *
 * Created on 2007-9-16, 23:50:42
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers.borders;

import dyno.swing.designer.properties.wrappers.BorderWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author William Chen
 */
public class CompoundBorderCoder implements SourceCoder {

    private static SourceCoder border_coder = new BorderWrapper();

    @Override
    public String getJavaCode(Object value) {
        CompoundBorder border = (CompoundBorder) value;
        Border outsideBorder = border.getOutsideBorder();
        Border insideBorder = border.getInsideBorder();
        String outsideBorderCode=border_coder.getJavaCode(outsideBorder);
        String insideBorderCode=border_coder.getJavaCode(insideBorder);
        return "javax.swing.BorderFactory.createCompoundBorder("+outsideBorderCode+", "+insideBorderCode+")";
    }
}