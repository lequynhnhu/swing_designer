/*
 * TopContainerAdapter.java
 * 
 * Created on 2007-10-13, 23:39:17
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans;

import java.awt.Component;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public interface TopContainerAdapter extends ContainerAdapter{
    void preview();
    Border getBorder();
    Component getContent();
}
