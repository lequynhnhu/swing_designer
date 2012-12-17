/*
 * JFrameBeanInfo.java
 * 
 * Created on 2007-10-2, 1:27:24
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

import javax.swing.JFrame;

/**
 *
 * @author rehte
 */
public class JFrameBeanInfo extends GenericBeanInfo{

    protected Class getBeanClass() {
        return JFrame.class;
    }

}
