/*
 * JListBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JList;


/**
 *
 * @author William Chen
 */
public class JListBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JListBeanInfo */
    public JListBeanInfo() {
    }

    protected Class getBeanClass() {
        return JList.class;
    }
}
