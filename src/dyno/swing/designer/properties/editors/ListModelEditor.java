/*
 * ComboBoxModelEditor.java
 * 
 * Created on 2007-8-28, 0:43:17
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessibleListModelEditor;

/**
 *
 * @author William Chen
 */
public class ListModelEditor extends AccessiblePropertyEditor {
    public ListModelEditor() {
        super(new AccessibleListModelEditor());
    }
}
