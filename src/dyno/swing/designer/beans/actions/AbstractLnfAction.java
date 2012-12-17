/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.event.ActionEvent;
import javax.swing.LookAndFeel;

/**
 *
 * @author William Chen
 */
public abstract class AbstractLnfAction extends AbstractContextAction {
    private LookAndFeel lnf;
    public AbstractLnfAction(SwingDesigner designer, String name) {
        super(designer);
        putValue(NAME, name);
    }        
    protected abstract LookAndFeel createLnf();
    public void actionPerformed(ActionEvent e) {
        if(lnf==null)
            lnf=createLnf();
        designer.setDesignLnf(lnf);
        designer.setRootComponent(designer.getRootComponent());
        designer.repaint();
    }
}
