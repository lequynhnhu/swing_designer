/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import javax.swing.LookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 *
 * @author William Chen
 */
public class MetalLnfAction extends AbstractLnfAction {
    public MetalLnfAction(SwingDesigner designer) {
        super(designer, "Metal LookAndFeel");
    }

    @Override
    protected LookAndFeel createLnf() {
        return new MetalLookAndFeel();
    }
}
