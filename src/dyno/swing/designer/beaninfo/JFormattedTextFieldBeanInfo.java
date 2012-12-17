/*
 * JFormattedTextFieldBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JFormattedTextField;


/**
 *
 * @author William Chen
 */
public class JFormattedTextFieldBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JFormattedTextFieldBeanInfo */
    public JFormattedTextFieldBeanInfo() {
    }

    protected Class getBeanClass() {
        return JFormattedTextField.class;
    }
}
