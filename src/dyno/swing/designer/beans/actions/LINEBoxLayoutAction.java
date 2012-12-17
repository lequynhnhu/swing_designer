/*
 * XBoxLayoutAction.java
 *
 * Created on 2007年5月5日, 上午11:12
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
public class LINEBoxLayoutAction extends BaseBoxLayoutAction {
    /** Creates a new instance of XBoxLayoutAction */
    public LINEBoxLayoutAction(SwingDesigner designer){
        super(designer);
        putValue(NAME, getValue(NAME)+"[LINE_AXIS]");
    }
    protected int getAxis() {
        return BoxLayout.LINE_AXIS;
    }
}
