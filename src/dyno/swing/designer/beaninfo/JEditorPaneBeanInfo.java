/*
 * JEditorPaneBeanInfo.java
 *
 * Created on 2007��8��12��, ����9:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import javax.swing.JEditorPane;


/**
 *
 * @author William Chen
 */
public class JEditorPaneBeanInfo extends GenericBeanInfo {
    /** Creates a new instance of JEditorPaneBeanInfo */
    public JEditorPaneBeanInfo() {
    }

    protected Class getBeanClass() {
        return JEditorPane.class;
    }
}
