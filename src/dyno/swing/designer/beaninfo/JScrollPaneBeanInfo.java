/*
 * JScrollPaneBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JScrollPane;


/**
 *
 * @author William Chen
 */
public class JScrollPaneBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JScrollPaneBeanInfo */
    public JScrollPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JScrollPane.class;
    }
}
