/*
 * JRadioButtonBeanInfo.java
 *
 * Created on 2007��8��12��, ����8:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JRadioButton;


/**
 *
 * @author William Chen
 */
public class JRadioButtonBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JRadioButtonBeanInfo */
    public JRadioButtonBeanInfo() {
    }

    protected Class getBeanClass() {
        return JRadioButton.class;
    }
}
