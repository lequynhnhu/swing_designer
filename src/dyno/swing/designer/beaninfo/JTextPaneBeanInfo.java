/*
 * JTextPaneBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JTextPane;


/**
 *
 * @author William Chen
 */
public class JTextPaneBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTextPaneBeanInfo */
    public JTextPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTextPane.class;
    }
}
