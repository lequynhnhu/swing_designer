/*
 * JTreeBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JTree;


/**
 *
 * @author William Chen
 */
public class JTreeBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTreeBeanInfo */
    public JTreeBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTree.class;
    }
}
