/*
 * NullLayoutAction.java
 *
 * Created on 2007年5月5日, 上午11:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Container;
import java.awt.event.ActionEvent;


/**
 *
 * @author William Chen
 */
public class NullLayoutAction extends AbstractContextAction {

    public NullLayoutAction(SwingDesigner designer) {
        super(designer);
        putValue(NAME, "NullLayout");
    }
    public void actionPerformed(ActionEvent e) {
        Container container = (Container) component;
        container.setLayout(null);
        Util.layoutContainer(container);
        designer.repaint();
        DesignerEvent evt = new DesignerEvent(designer);
        evt.setAffectedComponent(component);
        designer.getEditListenerTable().fireComponentEdited(evt);
    }
}