/*
 * LayoutContextAction.java
 *
 * Created on 2007年5月5日, 上午10:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.Action;


/**
 *
 * @author William Chen
 */
public abstract class LayoutContextAction extends AbstractContextAction {
    /** Creates a new instance of LayoutContextAction */
    public LayoutContextAction(SwingDesigner designer) {
        super(designer);
        Class<? extends LayoutManager> layoutClass = getLayoutClass();
        String name = layoutClass.getName();
        int index = name.lastIndexOf(".");
        if (index != -1) {
            name = name.substring(index + 1);
        }
        putValue(Action.NAME, name);
    }
    public void actionPerformed(ActionEvent e) {
        Class<? extends LayoutManager> layoutClass = getLayoutClass();
        
        if (layoutClass == null) {
            return;
        }
        
        try {
            Container container = (Container) component;
            LayoutManager layout = layoutClass.newInstance();
            container.setLayout(layout);
            initialize();
            Util.layoutContainer(container);
            DesignerEvent evt=new DesignerEvent(designer);
            evt.setAffectedComponent(component);
            designer.getEditListenerTable().fireComponentEdited(evt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected void initialize() {
    }
    
    protected abstract Class<? extends LayoutManager> getLayoutClass();
}
