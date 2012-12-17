/*
 * BoxLayoutAction.java
 *
 * Created on 2007年5月5日, 上午10:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.BoxLayout;


/**
 *
 * @author William Chen
 */
public abstract class BaseBoxLayoutAction extends LayoutContextAction {

    /** Creates a new instance of BorderLayoutAction */
    public BaseBoxLayoutAction(SwingDesigner designer) {
        super(designer);
    }
    public void actionPerformed(ActionEvent e) {
        Container container = (Container) component;
        BoxLayout layout = new BoxLayout(container, getAxis());
        container.setLayout(layout);
        Util.layoutContainer(container);
        designer.repaint();
    }

    protected int getAxis() {
        return BoxLayout.X_AXIS;
    }

    protected Class<? extends LayoutManager> getLayoutClass() {
        return BoxLayout.class;
    }
}
