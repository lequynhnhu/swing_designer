/*
 * JInternalFrameBeanInfo.java
 * 
 * Created on 2007-9-3, 23:21:12
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

import javax.swing.JInternalFrame;

/**
 *
 * @author William Chen
 */
public class JInternalFrameBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JLabelBeanInfo */
    public JInternalFrameBeanInfo() {
    }

    protected Class getBeanClass() {
        return JInternalFrame.class;
    }
}
