/*
 * JScrollBarBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JScrollBar;


/**
 *
 * @author William Chen
 */
public class JScrollBarBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JScrollBarBeanInfo */
    public JScrollBarBeanInfo() {
    }

    protected Class getBeanClass() {
        return JScrollBar.class;
    }
}
