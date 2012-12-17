/*
 * AbstractButtonBeanInfo.java
 *
 * Created on 2007年8月12日, 下午10:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.AbstractButton;


/**
 *
 * @author William Chen
 */
public class AbstractButtonBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of AbstractButtonBeanInfo */
    public AbstractButtonBeanInfo() {
    }

    protected Class getBeanClass() {
        return AbstractButton.class;
    }
}
