/*
 * JSplitPaneBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JSplitPane;


/**
 *
 * @author William Chen
 */
public class JSplitPaneBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JSplitPaneBeanInfo */
    public JSplitPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JSplitPane.class;
    }
}
