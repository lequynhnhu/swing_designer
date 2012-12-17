/*
 * JTextComponentBeanInfo.java
 *
 * Created on 2007年8月12日, 下午10:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.text.JTextComponent;


/**
 *
 * @author William Chen
 */
public class JTextComponentBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTextComponentBeanInfo */
    public JTextComponentBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTextComponent.class;
    }
}
