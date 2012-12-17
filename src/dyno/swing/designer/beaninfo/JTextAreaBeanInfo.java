/*
 * JTextAreaBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JTextArea;


/**
 *
 * @author William Chen
 */
public class JTextAreaBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JTextAreaBeanInfo */
    public JTextAreaBeanInfo() {
    }

    protected Class getBeanClass() {
        return JTextArea.class;
    }
}
