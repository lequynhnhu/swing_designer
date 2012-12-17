/*
 *
 * Created on 2007��5��5��, ����9:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;


import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import javax.swing.AbstractAction;


/**
 *
 * @author William Chen
 */
public abstract class AbstractContextAction extends AbstractAction{
    protected Component component;
    protected SwingDesigner designer;
    public AbstractContextAction(SwingDesigner designer) {
        this.designer=designer;
    }
    public void setComponent(Component component) {
        this.component = component;
    }
}
