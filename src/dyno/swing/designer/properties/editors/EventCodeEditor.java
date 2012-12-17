/*
 * EventCodeEditor.java
 * 
 * Created on 2007-9-5, 22:21:01
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessibleEventCodeEditor;

/**
 *
 * @author William Chen
 */
public class EventCodeEditor extends AccessiblePropertyEditor {
    public EventCodeEditor() {
        super(new AccessibleEventCodeEditor());
    }
}
