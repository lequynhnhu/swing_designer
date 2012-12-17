/*
 * TreeTransferHandler.java
 *
 * Created on 2007年8月12日, 下午1:54
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.DropLocation;
import javax.swing.tree.TreePath;


/**
 *
 * @author William Chen
 */
public class TreeTransferHandler extends TransferHandler {
    private static int PAD = 4;
    private SwingDesigner designer;
    /** Creates a new instance of TreeTransferHandler */
    public TreeTransferHandler(SwingDesigner designer) {
        super("selectionPath");
        this.designer=designer;
    }

    public boolean canImport(TransferHandler.TransferSupport support) {
        if(!support.isDrop())
            return false;
        DataFlavor[] flavors = support.getDataFlavors();

        if ((flavors != null) && (flavors.length > 0)) {
            ComponentTree tree = (ComponentTree) support.getComponent();

            try {
                TreePath path = (TreePath) support.getTransferable()
                                                  .getTransferData(flavors[0]);
                Component draggedComponent = (Component) path.getLastPathComponent();
                DropLocation loc = support.getDropLocation();
                Point p = loc.getDropPoint();
                TreePath newpath = tree.getPathForLocation(p.x, p.y);

                if (newpath == null) {
                    TreePath closestPath = tree.getClosestPathForLocation(p.x,
                            p.y);

                    if (closestPath != null) {
                        return canPathAccept(tree, closestPath,
                            draggedComponent, p);
                    } else {
                        return false;
                    }
                } else {
                    return canPathAccept(tree, newpath, draggedComponent, p);
                }
            } catch (Exception ex) {
                ex.printStackTrace();

                return false;
            }
        } else {
            return false;
        }
    }

    private boolean canPathAccept(ComponentTree tree, TreePath path,
        Component dragged, Point p) {
        Component hovering = (Component) path.getLastPathComponent();

        if (SwingUtilities.isDescendingFrom(hovering, dragged)) {
            return false;
        } else {
            if (SwingUtilities.isDescendingFrom(dragged, hovering)) {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, hovering);

                if (adapter instanceof ContainerAdapter) {
                    return ((ContainerAdapter) adapter).canAcceptMoreComponent();
                } else {
                    return false;
                }
            } else {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, hovering);

                if (adapter instanceof ContainerAdapter) {
                    return ((ContainerAdapter) adapter).canAcceptMoreComponent();
                }

                Rectangle bounds = tree.getRowBounds(tree.getRowForLocation(
                            p.x, p.y));

                if (bounds == null) {
                    return false;
                } else {
                    bounds.y += PAD;
                    bounds.height -= (2 * PAD);

                    if (bounds.contains(p)) {
                        return false;
                    } else {
                        Component parent = hovering.getParent();
                        ContainerAdapter containerAdapter = (ContainerAdapter) AdapterBus.getComponentAdapter(designer, parent);

                        if (p.y < bounds.y) {
                            return containerAdapter.canAddBefore(hovering);
                        } else if (p.y > (bounds.y + bounds.height)) {
                            return containerAdapter.canAddAfter(hovering);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }

    public int getSourceActions(JComponent c) {
        return COPY;
    }

    public boolean importData(TransferHandler.TransferSupport support) {
        if (canImport(support)) {
            DataFlavor[] flavors = support.getDataFlavors();
            ComponentTree tree = (ComponentTree) support.getComponent();

            try {
                TreePath path = (TreePath) support.getTransferable()
                                                  .getTransferData(flavors[0]);
                Component draggedComponent = (Component) path.getLastPathComponent();
                DropLocation loc = support.getDropLocation();
                Point p = loc.getDropPoint();
                TreePath newpath = tree.getPathForLocation(p.x, p.y);

                if (newpath == null) {
                    newpath = tree.getClosestPathForLocation(p.x, p.y);
                }

                accept(tree, newpath, draggedComponent, p);
                tree.refreshUI();
                tree.fireTreeChanged();

                return true;
            } catch (Exception ex) {
                ex.printStackTrace();

                return false;
            }
        } else {
            return false;
        }
    }

    private void accept(ComponentTree tree, TreePath path, Component dragged,
        Point p) {
        Component hovering = (Component) path.getLastPathComponent();

        if (SwingUtilities.isDescendingFrom(dragged, hovering)) {
            if (dragged.getParent() != hovering) {
                removeComponent(dragged);

            ContainerAdapter adapter = (ContainerAdapter) AdapterBus.getComponentAdapter(designer, hovering);
                adapter.addNextComponent(dragged);
            }
        } else {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, hovering);

            if (adapter instanceof ContainerAdapter) {
                removeComponent(dragged);

                ContainerAdapter containerAdapter = (ContainerAdapter) adapter;
                containerAdapter.addNextComponent(dragged);
            } else {
                Rectangle bounds = tree.getRowBounds(tree.getRowForLocation(
                            p.x, p.y));
                Container container = dragged.getParent();
                ContainerAdapter containerAdapter = (ContainerAdapter) AdapterBus.getComponentAdapter(designer, container);

                if (p.y < (bounds.y + PAD)) {
                    removeComponent(dragged);
                    containerAdapter.addBefore(hovering, dragged);
                } else if (p.y > ((bounds.y + bounds.height) - PAD)) {
                    removeComponent(dragged);
                    containerAdapter.addAfter(hovering, dragged);
                }
            }
        }
    }

    private void removeComponent(final Component dragged) {
        Container container = dragged.getParent();
        container.remove(dragged);
        container.invalidate();
        Util.layoutContainer(container);
    }
}
