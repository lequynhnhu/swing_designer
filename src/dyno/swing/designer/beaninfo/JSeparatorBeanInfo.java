/*
 * JSeparatorBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JSeparator;


/**
 *
 * @author William Chen
 */
public class JSeparatorBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JSeparatorBeanInfo */
    public JSeparatorBeanInfo() {
    }

    protected Class getBeanClass() {
        return JSeparator.class;
    }
}
