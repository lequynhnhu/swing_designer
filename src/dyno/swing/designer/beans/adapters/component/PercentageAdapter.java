/*
 * PercentageComponentAdapter.java
 *
 * Created on 2007-8-17, 20:01:50
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.editors.IntegerDesignerEditor;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Rectangle;

/**
 *
 * @author William Chen
 */
public class PercentageAdapter extends AbstractComponentAdapter {
    
    public PercentageAdapter(SwingDesigner designer, Component component){
        super(designer, component);
    }
    @Override
    public DesignerEditor getDesignerEditor(int x, int y) {
        if (designerEditor == null) {
            designerEditor = new IntegerDesignerEditor();
        }
        return designerEditor;
    }

    @Override
    public void validateBeanValue(Object value) throws ValidationException {
        int v = (Integer) value;
        if (v < 0 || v > 100) {
            throw new ValidationException("Percentage value should be between 0 and 100!");
        }
    }

    @Override
    public Rectangle getEditorBounds(int x, int y) {
        int w = component.getWidth();
        int h = component.getHeight();
        FontMetrics fm = component.getFontMetrics(component.getFont());
        int l100 = fm.stringWidth("100") + 4;
        int th = fm.getHeight() + 4;
        int bx = (w - l100) / 2;
        int by = (h - th) / 2;
        return new Rectangle(bx, by, l100, th);
    }
}
