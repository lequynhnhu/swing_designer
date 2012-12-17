/*
 * JComponentBeanInfo.java
 *
 * Created on 2007年8月12日, 下午9:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;



import javax.swing.JComponent;


/**
 *
 * @author William Chen
 */
public class JComponentBeanInfo extends GenericBeanInfo {
    public JComponentBeanInfo() {
    }

    protected Class getBeanClass() {
        return JComponent.class;
    }
}
