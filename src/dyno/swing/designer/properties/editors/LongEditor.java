/*
 * LongEditor.java
 *
 * Created on 2007-8-17, 0:08:54
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
public class LongEditor extends FormattedEditor {

    public LongEditor() {
        super(NumberFormat.getIntegerInstance());
    }

    @Override
    public Object getValue() {
        Object v = super.getValue();
        if (v == null) {
            return new Long(0);
        } else if (v instanceof Number) {
            return new Long(((Number)v).longValue());
        }
        return v;
    }
}
