/*
 * JTextFieldBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JTextField;


/**
 *
 * @author William Chen
 */
public class JTextFieldBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTextFieldBeanInfo */
    public JTextFieldBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTextField.class;
    }
}
