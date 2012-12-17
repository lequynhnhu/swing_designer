/*
 * ComponentTree.java
 *
 * Created on 2007年8月11日, 下午9:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.treeview;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEditAdapter;
import dyno.swing.designer.beans.events.DesignerEvent;
import dyno.swing.designer.beans.events.HotKeyProxy;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DropMode;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


/**
 *
 * @author William Chen
 */
public class ComponentTree extends JTree {

    private SwingDesigner designer;
    private ComponentTreeModel model;
    private ArrayList<ComponentTreeListener> listeners = new ArrayList<ComponentTreeListener>();
    private KeyListener hotKeyProxy;

    /** Creates a new instance of ComponentTree */
    public ComponentTree(SwingDesigner designer) {
        this.designer = designer;
        setRootVisible(true);
        setCellRenderer(new ComponentTreeCellRenderer(designer));
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        this.setDragEnabled(true);
        this.setDropMode(DropMode.ON_OR_INSERT);
        this.setTransferHandler(new TreeTransferHandler(designer));
        if (hotKeyProxy != null) {
            this.removeKeyListener(hotKeyProxy);
        }
        hotKeyProxy = new HotKeyProxy(designer);
        this.addKeyListener(hotKeyProxy);
        this.refreshTreeRoot();
        addTreeSelectionListener(designer);
        addComponentTreeListener(designer);
        designer.addDesignerEditListener(new TreeDesignerEditAdapter());
        this.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu(e);
                }
            }

            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu(e);
                }
            }
        });
        setEditable(true);
    }
    public boolean isPathEditable(TreePath path) {
        Object object=path.getLastPathComponent();
        if(object==designer.getRootComponent())
            return false;
        return super.isPathEditable(path);
    }
    public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value != null && value instanceof Component) {
            return Util.getComponentName((Component) value);
        } else {
            return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
        }
    }

    private void popupMenu(MouseEvent e) {
        TreePath path = this.getSelectionPath();
        if (path == null) {
            return;
        }
        Component component = (Component) path.getLastPathComponent();
        if (component == null) {
            return;
        }
        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, component);
        JPopupMenu menu = adapter.getContextPopupMenu(e);
        menu.show(this, e.getX(), e.getY());
    }

    public void refreshUI() {
        updateUI();
    }

    private class TreeDesignerEditAdapter extends DesignerEditAdapter {

        public void componentEdited(DesignerEvent evt) {
            ComponentTree.this.refreshUI();
            ComponentTree.this.repaint();
        }

        public void componentSelected(DesignerEvent evt) {
            ArrayList<Component> components = designer.getSelectionModel().getSelectedComponents();
            TreePath[] paths = new TreePath[components.size()];

            for (int i = 0; i < paths.length; i++) {
                paths[i] = buildTreePath(components.get(i));
            }

            setSelectionPaths(paths);
        }
    }

    public void addComponentTreeListener(ComponentTreeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void removeComponentTreeListener(ComponentTreeListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public void fireTreeChanged() {
        ComponentTreeEvent evt = new ComponentTreeEvent(this);

        for (ComponentTreeListener l : listeners) {
            l.treeChanged(evt);
        }
    }

    public void refreshTreeRoot() {
        model = new ComponentTreeModel(designer, designer.getRootComponent());
        setModel(model);
        setDragEnabled(true);
        setDropMode(DropMode.ON_OR_INSERT);
        setTransferHandler(new TreeTransferHandler(designer));
        repaint();
    }

    private TreePath buildTreePath(Component comp) {
        ArrayList<Component> path = new ArrayList<Component>();
        Component parent = comp;

        while (parent != null) {
            if (Util.isDesigning(parent)) {
                path.add(0, parent);
            }

            parent = parent.getParent();
        }

        Object[] components = path.toArray();

        return new TreePath(components);
    }
}