/*
 * AbsoluteLayoutAction.java
 *
 * Created on 2007年5月5日, 上午10:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.beans.layouts.AbsoluteLayout;
import dyno.swing.designer.beans.SwingDesigner;

import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public class AbsoluteLayoutAction extends LayoutContextAction {
    /** Creates a new instance of AbsoluteLayoutAction */
    public AbsoluteLayoutAction(SwingDesigner designer) {
        super(designer);
    }

    public Class<?extends LayoutManager> getLayoutClass() {
        return AbsoluteLayout.class;
    }
}
