/*
 * ContainerBeanInfo.java
 *
 * Created on 2007��8��12��, ����10:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import java.awt.Container;


/**
 *
 * @author William Chen
 */
public class ContainerBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of ContainerBeanInfo */
    public ContainerBeanInfo() {
    }

    protected Class getBeanClass() {
        return Container.class;
    }
}
