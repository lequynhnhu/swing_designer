/*
 * BorderLayoutAction.java
 *
 * Created on 2007年5月5日, 上午9:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.FlowLayout;
import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public class FlowLayoutAction extends LayoutContextAction {
    /** Creates a new instance of BorderLayoutAction */
    public FlowLayoutAction(SwingDesigner designer) {
        super(designer);
    }


    protected Class<?extends LayoutManager> getLayoutClass() {
        return FlowLayout.class;
    }
}
