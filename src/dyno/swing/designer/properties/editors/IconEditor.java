/*
 * ColorEditor.java
 *
 * Created on 2007��8��13��, ����7:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessibleIconEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;

/**
 *
 * @author William Chen
 */
public class IconEditor extends AccessiblePropertyEditor {
    public IconEditor() {
        super(new AccessibleIconEditor());
    }
}
