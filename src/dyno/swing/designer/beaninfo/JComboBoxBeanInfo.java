/*
 * JComboBoxBeanInfo.java
 *
 * Created on 2007��8��12��, ����8:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JComboBox;


/**
 *
 * @author William Chen
 */
public class JComboBoxBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JComboBoxBeanInfo */
    public JComboBoxBeanInfo() {
    }

    protected Class getBeanClass() {
        return JComboBox.class;
    }
}
