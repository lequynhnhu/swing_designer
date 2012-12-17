/*
 * SpinnerModelEditor.java
 * 
 * Created on 2007-9-1, 11:46:12
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessibleSpinnerModelEditor;

/**
 *
 * @author William Chen
 */
public class SpinnerModelEditor extends AccessiblePropertyEditor {
    public SpinnerModelEditor() {
        super(new AccessibleSpinnerModelEditor());
    }
}

