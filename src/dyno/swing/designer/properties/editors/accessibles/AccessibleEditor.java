/*
 * AccessibleEditor.java
 *
 * Created on 2007年8月13日, 下午8:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;

import javax.swing.event.ChangeListener;


/**
 *
 * @author William Chen
 */
public interface AccessibleEditor{
    void validateValue() throws ValidationException;
    
    Object getValue();

    void setValue(Object v);
    
    Component getEditor();

    void addChangeListener(ChangeListener l);

    void removeChangeListener(ChangeListener l);
}
