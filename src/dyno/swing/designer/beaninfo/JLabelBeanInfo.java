/*
 * JLabelBeanInfo.java
 *
 * Created on 2007年8月12日, 下午8:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JLabel;


/**
 *
 * @author William Chen
 */
public class JLabelBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JLabelBeanInfo */
    public JLabelBeanInfo() {
    }

    protected Class getBeanClass() {
        return JLabel.class;
    }
}
