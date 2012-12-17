/*
 * JTableAdapter.java
 *
 * Created on 2007-9-2, 16:13:51
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author William Chen
 */
public class JTableAdapter extends AbstractComponentAdapter {

    private static String[] columnNames = {"title1", "title2", "title3", "title4"};
    private static Object[][] data = {{"1x1", "1x2", "1x3", "1x4"}, {"2x1", "2x2", "2x3", "2x4"}, {"3x1", "3x2", "3x3", "3x4"}, {"4x1", "4x2", "4x3", "4x4"}};
    protected JTable table;

    public JTableAdapter(SwingDesigner designer, Component c) {
        super(designer, c);
        table = (JTable) c;
        DefaultTableModel dataModel = new DefaultTableModel(data, columnNames);
        table.setModel(dataModel);
        Dimension initialSize = table.getPreferredSize();
        table.setSize(initialSize);
        Util.layoutContainer(table);        
    }
}