/*
 * XBoxLayoutAction.java
 *
 * Created on 2007��5��5��, ����11:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import javax.swing.BoxLayout;


/**
 *
 * @author William Chen
 */
public class XBoxLayoutAction extends BaseBoxLayoutAction {
    /** Creates a new instance of XBoxLayoutAction */
    public XBoxLayoutAction(SwingDesigner designer){
        super(designer);
        putValue(NAME, getValue(NAME)+"[X_AXIS]");
    }

    protected int getAxis() {
        return BoxLayout.X_AXIS;
    }
}
