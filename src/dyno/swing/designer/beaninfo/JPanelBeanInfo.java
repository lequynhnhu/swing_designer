/*
 * JPanelBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JPanel;


/**
 *
 * @author William Chen
 */
public class JPanelBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JPanelBeanInfo */
    public JPanelBeanInfo() {
    }

    protected Class getBeanClass() {
        return JPanel.class;
    }
}
