/*
 * JSliderBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JSlider;


/**
 *
 * @author William Chen
 */
public class JSliderBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JSliderBeanInfo */
    public JSliderBeanInfo() {
    }

    protected Class getBeanClass() {
        return JSlider.class;
    }
}
