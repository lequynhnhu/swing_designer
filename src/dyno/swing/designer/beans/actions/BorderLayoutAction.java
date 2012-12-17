/*
 * BorderLayoutAction.java
 *
 * Created on 2007年5月5日, 上午9:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

import java.util.ArrayList;
import javax.swing.RootPaneContainer;


/**
 *
 * @author William Chen
 */
public class BorderLayoutAction extends LayoutContextAction {
    /** Creates a new instance of BorderLayoutAction */
    public BorderLayoutAction(SwingDesigner designer) {
        super(designer);
    }
    protected Class<?extends LayoutManager> getLayoutClass() {
        return BorderLayout.class;
    }

    protected void initialize() {
        Container container = (Container) component;
        
        if(container instanceof RootPaneContainer){
            container = ((RootPaneContainer)container).getContentPane();
        }
        
        int count = container.getComponentCount();

        if (count > 0) {
            ArrayList<Component> components = new ArrayList<Component>();

            for (int i = 0; i < count; i++) {
                components.add(container.getComponent(i));
            }

            if (count > 0) {
                container.add(components.get(0), BorderLayout.NORTH);
            }

            if (count > 1) {
                container.add(components.get(1), BorderLayout.SOUTH);
            }

            if (count > 2) {
                container.add(components.get(2), BorderLayout.WEST);
            }

            if (count > 3) {
                container.add(components.get(3), BorderLayout.EAST);
            }

            if (count > 4) {
                container.add(components.get(4), BorderLayout.CENTER);
            }
        }
    }
}
