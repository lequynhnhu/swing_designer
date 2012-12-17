/*
 * AbsoluteLayout.java
 *
 * Created on 2007年5月5日, 上午10:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public class AbsoluteLayout implements LayoutManager {
    /** Creates a new instance of AbsoluteLayout */
    public AbsoluteLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(10, 10);
    }

    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    public void layoutContainer(Container parent) {
    }
}
