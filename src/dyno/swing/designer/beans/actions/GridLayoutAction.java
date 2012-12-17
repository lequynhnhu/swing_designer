/*
 * GridLayoutAction.java
 *
 * Created on 2007年5月5日, 下午12:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Action;


/**
 *
 * @author William Chen
 */
public class GridLayoutAction extends AbstractContextAction implements ActionCategory{

    private static final int MAX = 3;
    private Action[] gridLayoutActions;
    static {
    }
    private int columns;
    private int rows;
    private class DefaultGridLayoutAction extends GridLayoutAction{
        public DefaultGridLayoutAction(SwingDesigner designer){
            super(designer, 1, 0);
            putValue(NAME, "Default GridLayout");
        }
    }
    /** Creates a new instance of GridLayoutAction */
    public GridLayoutAction(SwingDesigner designer) {
        super(designer);
        putValue(NAME, "GridLayout");
        ArrayList<Action> list = new ArrayList<Action>();
        list.add(new DefaultGridLayoutAction(designer));
        list.add(null);

        for (int column = 0; column <= MAX; column++) {
            for (int row = 0; row <= MAX; row++) {
                if ((row == 0) && (column == 0)) {
                    continue;
                }

                list.add(new GridLayoutAction(designer, row, column));
            }
        }

        gridLayoutActions = list.toArray(new Action[0]);
    }

    public GridLayoutAction(SwingDesigner designer, int r, int c) {
        super(designer);
        rows = r;
        columns = c;
        putValue(NAME, "GridLayout[" + rows + "x" + columns + "]");
    }
    public Action[] getSubActions() {
        if ((rows == 0) && (columns == 0)) {
            return gridLayoutActions;
        }

        return null;
    }

    public void actionPerformed(ActionEvent e) {
        Container container = (Container) component;
        container.setLayout(new GridLayout(rows, columns));
        Util.layoutContainer(container);
        designer.repaint();
        DesignerEvent evt = new DesignerEvent(designer);
        evt.setAffectedComponent(component);
        designer.getEditListenerTable().fireComponentEdited(evt);
    }
}