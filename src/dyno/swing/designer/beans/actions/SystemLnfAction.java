/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import javax.swing.LookAndFeel;

/**
 *
 * @author William Chen
 */
public class SystemLnfAction extends AbstractLnfAction {

    public SystemLnfAction(SwingDesigner designer) {
        super(designer, "System LookAndFeel");
    }

    @Override
    protected LookAndFeel createLnf() {
        return designer.getIdeLnf();
    }
}
