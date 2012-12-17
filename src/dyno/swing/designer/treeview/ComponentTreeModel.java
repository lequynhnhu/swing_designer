/*
 * ComponentTreeModel.java
 *
 * Created on 2007年8月11日, 下午8:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.treeview;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Component;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


/**
 *
 * @author William Chen
 */
public class ComponentTreeModel implements TreeModel {

    private ArrayList<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
    private Component root;
    private SwingDesigner designer;

    /** Creates a new instance of ComponentTreeModel */
    public ComponentTreeModel(SwingDesigner designer, Component root) {
        this.designer = designer;
        this.root = root;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        if (parent != null && parent instanceof Component) {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, (Component) parent);
            if (adapter instanceof ContainerAdapter) {
                return ((ContainerAdapter) adapter).getChild(index);
            }
        }
        return null;
    }

    public int getChildCount(Object parent) {
        if (parent != null && parent instanceof Component) {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, (Component) parent);
            if (adapter instanceof ContainerAdapter) {
                return ((ContainerAdapter) adapter).getChildCount();
            }
        }
        return 0;
    }

    public boolean isLeaf(Object node) {
        if (node != null && node instanceof Component) {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, (Component) node);
            return !(adapter instanceof ContainerAdapter);
        }
        return true;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        Object lastObject = path.getLastPathComponent();
        if (lastObject != newValue) {
            if (newValue instanceof String) {
                //Change varaible name
                Component component = (Component) lastObject;
                Util.setComponentName(component, (String) newValue);
                DesignerEvent evt = new DesignerEvent(this);
                evt.setAffectedComponent(component);
                designer.getEditListenerTable().fireComponentEdited(evt);
            }
            TreeModelEvent event = new TreeModelEvent(this, path.getPath());
            fireEvent("treeNodesChanged", event);
        }
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (parent != null && parent instanceof Component) {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, (Component) parent);
            if (adapter instanceof ContainerAdapter) {
                return ((ContainerAdapter) adapter).getIndexOfChild((Component) child);
            }
        }
        return -1;
    }

    public void addTreeModelListener(TreeModelListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void removeTreeModelListener(TreeModelListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    protected void fireEvent(String eventName, TreeModelEvent evt) {
        try {
            Method m = TreeModelListener.class.getMethod(eventName, TreeModelEvent.class);

            for (TreeModelListener listener : listeners) {
                m.invoke(listener, evt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}