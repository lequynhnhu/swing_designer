/*
 * JPasswordFieldBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JPasswordField;


/**
 *
 * @author William Chen
 */
public class JPasswordFieldBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JPasswordFieldBeanInfo */
    public JPasswordFieldBeanInfo() {
    }

    protected Class getBeanClass() {
        return JPasswordField.class;
    }
}
