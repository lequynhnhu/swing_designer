/*
 * JScrollBarAdapter.java
 *
 * Created on 2007-8-17, 20:05:55
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;
import javax.swing.JScrollBar;

/**
 *
 * @author William Chen
 */
public class JScrollBarAdapter extends PercentageAdapter {
    public JScrollBarAdapter(SwingDesigner designer, Component component){
        super(designer, component);
    }
    @Override
    public Object getBeanValue() {
        JScrollBar scrollbar = (JScrollBar) component;
        return scrollbar.getValue();
    }

    @Override
    public void setBeanValue(Object value) {
        JScrollBar scrollbar = (JScrollBar) component;
        int v = (Integer) value;
        scrollbar.setValue(v);
    }

    @Override
    public void validateBeanValue(Object value) throws ValidationException {
        JScrollBar scrollbar = (JScrollBar) component;
        int v = (Integer) value;
        if (v < scrollbar.getMinimum() || v > scrollbar.getMaximum()) {
            throw new ValidationException("Percentage value should be between " + scrollbar.getMinimum() + " and " + scrollbar.getMaximum() + "!");
        }
    }
}