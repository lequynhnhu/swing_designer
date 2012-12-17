/*
 * DefaultBoxLayoutAction.java
 *
 * Created on 2007年5月5日, 上午11:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import javax.swing.Action;


/**
 *
 * @author William Chen
 */
public class BoxLayoutAction extends BaseBoxLayoutAction implements ActionCategory{
    private Action[] subBoxLayoutActions;
    public BoxLayoutAction(SwingDesigner designer){
        super(designer);
        subBoxLayoutActions= new Action[]{
            new XBoxLayoutAction(designer) {
                public String getDisplayName() {
                    return "Default BoxLayout";
                }
            }
            , null, new XBoxLayoutAction(designer), new YBoxLayoutAction(designer),
            new LINEBoxLayoutAction(designer), new PAGEBoxLayoutAction(designer)
        };
    }
    public Action[] getSubActions() {
        return subBoxLayoutActions;
    }
}
