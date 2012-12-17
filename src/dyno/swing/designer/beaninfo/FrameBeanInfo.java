/*
 * FrameBeanInfo.java
 * 
 * Created on 2007-10-2, 1:24:59
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

import java.awt.Frame;

/**
 *
 * @author rehte
 */
public class FrameBeanInfo extends GenericBeanInfo{

    protected Class getBeanClass() {
        return Frame.class;
    }

}
