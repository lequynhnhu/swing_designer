/*
 * FloatEditor.java
 * 
 * Created on 2007-8-27, 15:28:29
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import java.text.NumberFormat;

/**
 *
 * @author William Chen
 */
public class FloatEditor extends FormattedEditor {
    public FloatEditor() {
        super(NumberFormat.getNumberInstance());
    }

    @Override
    public Object getValue() {
        Object v = super.getValue();
        if (v == null) {
            return new Float(0);
        } else if (v instanceof Number) {
            return new Float(((Number)v).floatValue());
        }
        return v;
    }    
}
