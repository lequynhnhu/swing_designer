/*
 * JSliderAdapter.java
 * 
 * Created on 2007-8-17, 19:25:37
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import javax.swing.JSlider;

/**
 *
 * @author William Chen
 */
public class JSliderAdapter extends PercentageAdapter{
    public JSliderAdapter(SwingDesigner designer, Component component){
        super(designer, component);
    }
    @Override
    public Object getBeanValue() {
        JSlider slider=(JSlider)component;
        return slider.getValue();
    }
    @Override
    public void setBeanValue(Object value) {
        int v=(Integer)value;
        JSlider slider=(JSlider)component;
        slider.setValue(v);
    }
}
