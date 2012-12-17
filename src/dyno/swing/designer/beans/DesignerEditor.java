/*
 * DesignerEditor.java
 *
 * Created on 2007年8月13日, 下午1:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import java.awt.Component;

import javax.swing.event.ChangeListener;


/**
 *
 * @author William Chen
 */
public interface DesignerEditor {
    Component getEditorComponent();

    void setValue(Object value);

    Object getValue();

    void addChangeListener(ChangeListener l);

    void removeChangeListener(ChangeListener l);
}
