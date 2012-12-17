/*
 * BorderLayoutAction.java
 *
 * Created on 2007��5��5��, ����9:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public class GridBagLayoutAction extends LayoutContextAction {
    /** Creates a new instance of BorderLayoutAction */
    public GridBagLayoutAction(SwingDesigner designer) {
        super(designer);
    }

    protected Class<?extends LayoutManager> getLayoutClass() {
        return GridBagLayout.class;
    }
}
