/*
 * BeanInfoToggleButton.java
 *
 * Created on 2007年8月10日, 上午12:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.toolkit;

import java.beans.BeanInfo;

import javax.swing.JToggleButton;


/**
 *
 * @author William Chen
 */
public class BeanInfoToggleButton extends JToggleButton {
    private BeanInfo beanInfo;

    /** Creates a new instance of BeanInfoToggleButton */
    public BeanInfoToggleButton(BeanInfo info) {
        beanInfo = info;
    }

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    public void setBeanInfo(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }
}
