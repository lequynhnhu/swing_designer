/*
 * WindowBeanInfo.java
 * 
 * Created on 2007-10-2, 1:22:43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

import java.awt.Window;

/**
 *
 * @author William Chen
 */
public class WindowBeanInfo extends GenericBeanInfo{

    protected Class getBeanClass() {
        return Window.class;
    }

}
