/*
 * ComponentTreeCellRenderer.java
 *
 * Created on 2007年8月11日, 下午9:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.treeview;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import java.awt.Component;
import java.awt.Image;
import java.beans.BeanInfo;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 *
 * @author William Chen
 */
public class ComponentTreeCellRenderer extends DefaultTreeCellRenderer {

    private SwingDesigner designer;

    /** Creates a new instance of ComponentTreeCellRenderer */
    public ComponentTreeCellRenderer(SwingDesigner designer) {
        this.designer = designer;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if (value instanceof Component) {
            Component comp = (Component) value;
            setText(Util.getComponentName(comp));

            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);
            Image image = adapter.getIcon(BeanInfo.ICON_COLOR_16x16);

            if (image != null) {
                setIcon(new ImageIcon(image));
            }
        }
        return this;
    }

    @Override
    public Icon getClosedIcon() {
        return getIcon();
    }

    @Override
    public Icon getLeafIcon() {
        return getIcon();
    }

    @Override
    public Icon getOpenIcon() {
        return getIcon();
    }
}