/*
 * JToolBarBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JToolBar;


/**
 *
 * @author William Chen
 */
public class JToolBarBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JToolBarBeanInfo */
    public JToolBarBeanInfo() {
    }

    protected Class getBeanClass() {
        return JToolBar.class;
    }
}
