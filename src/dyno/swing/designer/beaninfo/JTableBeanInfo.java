/*
 * JTableBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JTable;


/**
 *
 * @author William Chen
 */
public class JTableBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTableBeanInfo */
    public JTableBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTable.class;
    }
}
