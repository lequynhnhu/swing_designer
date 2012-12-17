/*
 * JTextAreaAdapter.java
 *
 * Created on 2007年8月15日, 下午11:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.editors.TextAreaEditor;
import dyno.swing.designer.properties.ValidationException;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.text.JTextComponent;


/**
 *
 * @author William Chen
 */
public class J2DAreaAdapter extends CompositeComponentAdapter {
    protected DesignerEditor designerEditor = new TextAreaEditor();

    /** Creates a new instance of JTextAreaAdapter */
    public J2DAreaAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    public DesignerEditor getDesignerEditor(int x, int y) {
        return designerEditor;
    }

    public void validateBeanValue(Object value)
        throws ValidationException {
    }

    public void setBeanValue(Object value) {
        JTextComponent textArea = (JTextComponent) component;
        textArea.setText((String) value);
        textArea.setCaretPosition(0);
    }

    public Object getBeanValue() {
        JTextComponent textArea = (JTextComponent) component;

        return textArea.getText();
    }

    public Rectangle getEditorBounds(int x, int y) {
        Rectangle bounds = component.getBounds();
        bounds.x = 1;
        bounds.y = 1;
        bounds.width -= 1;
        bounds.height -= 1;

        return bounds;
    }
}
