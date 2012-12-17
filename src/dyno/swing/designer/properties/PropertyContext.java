/*
 * PropertyContext.java
 * 
 * Created on 2007-8-27, 21:38:38
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.SwingDesigner;

/**
 *
 * @author William Chen
 */
public interface PropertyContext {
    void setSwingDesigner(SwingDesigner designer);
    void setBean(Object bean);
}
