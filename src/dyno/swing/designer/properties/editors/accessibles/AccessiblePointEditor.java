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
import dyno.swing.designer.properties.wrappers.PointWrapper;


/**
 *
 * @author William Chen
 */
public class AccessiblePointEditor extends BaseAccessibleEditor {
    /** Creates a new instance of AccessibleColorEditor */
    public AccessiblePointEditor() {
        super(new PointWrapper(), new PointWrapper(), false);
    }
}
