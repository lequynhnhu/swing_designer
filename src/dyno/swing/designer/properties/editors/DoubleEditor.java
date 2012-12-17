/*
 * DoubleEditor.java
 * 
 * Created on 2007-8-27, 15:26:58
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
public class DoubleEditor extends FormattedEditor {
    public DoubleEditor() {
        super(NumberFormat.getNumberInstance());
    }

    @Override
    public Object getValue() {
        Object v = super.getValue();
        if (v == null) {
            return new Double(0);
        } else if (v instanceof Number) {
            return new Double(((Number)v).doubleValue());
        }
        return v;
    }    
}
