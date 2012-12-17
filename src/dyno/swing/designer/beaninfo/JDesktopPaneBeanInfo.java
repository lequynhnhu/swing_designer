/*
 * JDesktopPane.java
 * 
 * Created on 2007-9-2, 21:39:06
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

import javax.swing.JDesktopPane;

/**
 *
 * @author William Chen
 */
public class JDesktopPaneBeanInfo  extends GenericBeanInfo {
    /** Creates a new instance of JPanelBeanInfo */
    public JDesktopPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JDesktopPane.class;
    }
}
