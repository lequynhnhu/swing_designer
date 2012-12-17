/*
 * CardLayoutAction.java
 *
 * Created on 2007年5月5日, 上午11:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public class CardLayoutAction extends LayoutContextAction {
    /** Creates a new instance of BorderLayoutAction */
    public CardLayoutAction(SwingDesigner designer) {
        super(designer);
    }

    protected Class<?extends LayoutManager> getLayoutClass() {
        return CardLayout.class;
    }

    @Override
    protected void initialize() {
        Container container = (Container) component;
        int count = container.getComponentCount();

        for (int i = 0; i < count; i++) {
            Component child = container.getComponent(i);
            container.add(child, Util.getComponentName(child));
        }
    }
}
