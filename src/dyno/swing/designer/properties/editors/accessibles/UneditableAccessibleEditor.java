/*
 * UneditableAccessibleEditor.java
 *
 * Created on August 14, 2007, 6:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.wrappers.Encoder;


/**
 *
 * @author William Chen
 */
public abstract class UneditableAccessibleEditor extends BaseAccessibleEditor {
    protected Object value;
    /** Creates a new instance of UneditableAccessibleEditor */
    public UneditableAccessibleEditor(Encoder enc) {
        super(enc, null, true);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object v) {
        this.value = v;
        super.setValue(v);
    }
}
