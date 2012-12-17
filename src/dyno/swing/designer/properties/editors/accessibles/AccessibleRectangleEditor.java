/*
 * AccessibleColorEditor.java
 *
 * Created on 2007��8��13��, ����9:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.RectangleWrapper;


/**
 *
 * @author William Chen
 */
public class AccessibleRectangleEditor extends BaseAccessibleEditor {
    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleRectangleEditor() {
        super(new RectangleWrapper(), new RectangleWrapper(), false);
    }

    protected void popupDialog() {
    }
}
