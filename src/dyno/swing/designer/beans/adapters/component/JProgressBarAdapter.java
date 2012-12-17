/*
 * JProgressBarAdapter.java
 * 
 * Created on 2007-8-17, 20:10:37
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import javax.swing.JProgressBar;

/**
 *
 * @author William Chen
 */
public class JProgressBarAdapter extends PercentageAdapter{
public JProgressBarAdapter(SwingDesigner designer, Component component){
        super(designer, component);
    }
    @Override
    public Object getBeanValue() {
        JProgressBar progressBar=(JProgressBar)component;
        return progressBar.getValue();
    }
    @Override
    public void setBeanValue(Object value) {
        int v=(Integer)value;
        JProgressBar progressBar=(JProgressBar)component;
        progressBar.setValue(v);
    }
}
