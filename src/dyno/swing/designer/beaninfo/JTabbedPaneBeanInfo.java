/*
 * JTabbedPaneBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JTabbedPane;


/**
 *
 * @author William Chen
 */
public class JTabbedPaneBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTabbedPaneBeanInfo */
    public JTabbedPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTabbedPane.class;
    }
}
