/*
 * ComponentBeanInfo.java
 *
 * Created on 2007年8月12日, 下午10:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import java.awt.Component;

import java.beans.PropertyDescriptor;

import java.util.ArrayList;


/**
 *
 * @author William Chen
 */
public class ComponentBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of ComponentBeanInfo */
    public ComponentBeanInfo() {
    }

    protected Class getBeanClass() {
        return Component.class;
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        ArrayList<PropertyDescriptor> properties = getDeclaredProperties();

        return properties.toArray(new PropertyDescriptor[0]);
    }
}
