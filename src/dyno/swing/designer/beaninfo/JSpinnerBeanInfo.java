/*
 * JSpinnerBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JSpinner;


/**
 *
 * @author William Chen
 */
public class JSpinnerBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JSpinnerBeanInfo */
    public JSpinnerBeanInfo() {
    }

    protected Class getBeanClass() {
        return JSpinner.class;
    }
}
