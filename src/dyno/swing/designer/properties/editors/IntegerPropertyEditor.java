/*
 * TextEditor.java
 *
 * Created on 2007年8月13日, 上午9:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import java.text.NumberFormat;


/**
 *
 * @author William Chen
 */
public class IntegerPropertyEditor extends FormattedEditor {

    public IntegerPropertyEditor() {
        super(NumberFormat.getIntegerInstance());
    }

    @Override
    public Object getValue() {
        Object v = super.getValue();
        if (v == null) {
            return 0;
        }
        return ((Number) v).intValue();
    }
}