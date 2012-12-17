/*
 * JProgressBarBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JProgressBar;


/**
 *
 * @author William Chen
 */
public class JProgressBarBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JProgressBarBeanInfo */
    public JProgressBarBeanInfo() {
    }

    protected Class getBeanClass() {
        return JProgressBar.class;
    }
}
