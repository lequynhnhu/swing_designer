/*
 * JToggleButtonBeanInfo.java
 *
 * Created on 2007年8月12日, 下午8:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JToggleButton;


/**
 *
 * @author William Chen
 */
public class JToggleButtonBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JToggleButtonBeanInfo */
    public JToggleButtonBeanInfo() {
    }

    protected Class getBeanClass() {
        return JToggleButton.class;
    }
}
