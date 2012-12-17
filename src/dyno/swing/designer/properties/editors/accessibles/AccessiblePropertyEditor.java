/*
 * AccessiblePropertyEditor.java
 *
 * Created on 2007年8月13日, 下午9:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author William Chen
 */
public class AccessiblePropertyEditor extends AbstractPropertyEditor {
    protected AccessibleEditor editor;

    /** Creates a new instance of TextEditor */
    public AccessiblePropertyEditor(AccessibleEditor editor) {
        this.editor = editor;
        editor.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    firePropertyChanged();
                }
            });
    }

    public void setValue(Object value) {
        editor.setValue(value);
    }

    public Object getValue() {
        return editor.getValue();
    }

    public Component getCustomEditor() {
        return editor.getEditor();
    }

    @Override
    public void validateValue() throws ValidationException {
        editor.validateValue();
    }
}
