/*
 * JButtonBeanInfo.java
 *
 * Created on 2007��8��12��, ����8:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;



import javax.swing.JButton;


/**
 *
 * @author William Chen
 */
public class JButtonBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JButtonBeanInfo */
    public JButtonBeanInfo() {
    }

    protected Class getBeanClass() {
        return JButton.class;
    }
}
