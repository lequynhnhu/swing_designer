/*
 * BorderEditor.java
 *
 * Created on August 14, 2007, 6:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessibleInsetsEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;

/**
 *
 * @author William Chen
 */
public class InsetsEditor extends AccessiblePropertyEditor {

    public InsetsEditor() {
        super(new AccessibleInsetsEditor());
    }
}