/*
 * ColorEditor.java
 *
 * Created on 2007��8��13��, ����7:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessibleRectangleEditor;

/**
 *
 * @author William Chen
 */
public class RectangleEditor extends AccessiblePropertyEditor {
    public RectangleEditor() {
        super(new AccessibleRectangleEditor());
    }
}
