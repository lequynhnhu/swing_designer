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
public class GenericLnfAction  extends AbstractLnfAction {
    private String lnf_class;
    public GenericLnfAction(SwingDesigner designer, String lnf_class, String lnf_name) {
        super(designer, lnf_name);
        this.lnf_class = lnf_class;
    }

    @Override
    protected LookAndFeel createLnf() {
        try{
            Class clazz=Class.forName(lnf_class);
            return (LookAndFeel)clazz.newInstance();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}