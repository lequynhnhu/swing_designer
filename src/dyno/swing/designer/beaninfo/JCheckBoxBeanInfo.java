/*
 * JCheckBoxBeanInfo.java
 *
 * Created on 2007��8��12��, ����8:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JCheckBox;


/**
 *
 * @author William Chen
 */
public class JCheckBoxBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JCheckBoxBeanInfo */
    public JCheckBoxBeanInfo() {
    }

    protected Class getBeanClass() {
        return JCheckBox.class;
    }
}
