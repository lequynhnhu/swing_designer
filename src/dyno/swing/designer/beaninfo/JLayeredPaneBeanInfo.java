/*
 * JLayeredPaneBeanInfo.java
 * 
 * Created on 2007-9-2, 22:06:06
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

import javax.swing.JLayeredPane;

/**
 *
 * @author William Chen
 */
public class JLayeredPaneBeanInfo  extends GenericBeanInfo {
    /** Creates a new instance of JPanelBeanInfo */
    public JLayeredPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JLayeredPane.class;
    }
}
