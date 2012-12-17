/*
 * BevelBorderCoder.java
 *
 * Created on 2007-9-16, 23:50:42
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers.borders;

import dyno.swing.designer.properties.items.TitleJustificationItems;
import dyno.swing.designer.properties.items.TitlePositionItems;
import dyno.swing.designer.properties.wrappers.BorderWrapper;
import dyno.swing.designer.properties.wrappers.ColorWrapper;
import dyno.swing.designer.properties.wrappers.FontWrapper;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import dyno.swing.designer.properties.wrappers.primitive.StringWrapper;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author William Chen
 */
public class TitledBorderCoder implements SourceCoder {

    private static SourceCoder border_coder = new BorderWrapper();
    private static SourceCoder color_coder = new ColorWrapper();
    private static SourceCoder font_coder = new FontWrapper();
    private static SourceCoder justification_coder = new ItemWrapper(new TitleJustificationItems());
    private static SourceCoder position_coder = new ItemWrapper(new TitlePositionItems());
    private static SourceCoder string_coder = new StringWrapper();
    @Override
    public String getJavaCode(Object value) {
        TitledBorder titledBorder = (TitledBorder) value;
        Border border = titledBorder.getBorder();
        String bString = border_coder.getJavaCode(border);
        String title = titledBorder.getTitle();
        String tString = string_coder.getJavaCode(title);
        Color titleColor = titledBorder.getTitleColor();
        String cString = color_coder.getJavaCode(titleColor);
        Font titleFont = titledBorder.getTitleFont();
        String fString = font_coder.getJavaCode(titleFont);
        int titleJustification = titledBorder.getTitleJustification();
        String jString = justification_coder.getJavaCode(titleJustification);
        int titlePosition = titledBorder.getTitlePosition();
        String pString = position_coder.getJavaCode(titlePosition);
        if (border != null &&
                title.equals("") &&
                titleJustification == TitledBorder.LEADING &&
                titlePosition == TitledBorder.TOP &&
                titleFont == null &&
                titleColor == null) {
            return "javax.swing.BorderFactory.createTitledBorder(" + bString + ")";
        } else if (border == null &&
                title != null &&
                titleJustification == TitledBorder.LEADING &&
                titlePosition == TitledBorder.TOP &&
                titleFont == null &&
                titleColor == null) {
            return "javax.swing.BorderFactory.createTitledBorder(" + tString +")";
        } else if (border != null &&
                title != null &&
                !title.equals("") &&
                titleJustification == TitledBorder.LEADING &&
                titlePosition == TitledBorder.TOP &&
                titleFont == null &&
                titleColor == null) {
            return "javax.swing.BorderFactory.createTitledBorder(" + bString + ", " + tString +")";
        }else if (titleFont == null &&
                titleColor == null) {
            return "javax.swing.BorderFactory.createTitledBorder(" + bString + ", " + tString +", "+jString+", "+pString+")";
        }else if(titleColor==null){
            return "javax.swing.BorderFactory.createTitledBorder(" + bString + ", " + tString +", "+jString+", "+pString+", "+fString+")";
        }else{
            return "javax.swing.BorderFactory.createTitledBorder(" + bString + ", " + tString +", "+jString+", "+pString+", "+fString+", "+cString+")";
        }
    }
}