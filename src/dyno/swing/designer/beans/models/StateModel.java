/*
 * MouseInputModel.java
 *
 * Created on July 31, 2007, 6:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.models;

import dyno.swing.designer.beans.*;
import dyno.swing.designer.beans.location.Location;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * 普通模式下的状态model
 * @author William Chen
 */
public class StateModel implements Constants {

    //对应的selection model
    private SelectionModel selectionModel;
    //当前鼠标进入拖拽区域的位置类型
    private Location location;
    //当前拖拽的起始位置
    private int current_x;
    private int current_y;
    //当前鼠标所在的热点
    private ArrayList<Component> hotspot;
    //当前是否处于拖拽选择状态
    private boolean selecting;
    private SwingDesigner designer;

    /** Creates a new instance of MouseInputModel */
    public StateModel(SwingDesigner designer) {
        this.designer = designer;
        hotspot = new ArrayList<Component>();
        selectionModel = designer.getSelectionModel();
    }

    public Location getLocation() {
        return location;
    }

    public boolean isSelecting() {
        return selecting;
    }

    public boolean isDragging() {
        return (location != Location.outer) && !selecting && !hotspot.isEmpty();
    }

    public ArrayList<Component> getHotspotComponents() {
        return hotspot;
    }

    public void startSelecting(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();

        selecting = true;
        current_x = x;
        current_y = y;
    }

    public void startResizing(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();

        current_x = x;
        current_y = y;
        hotspot.clear();
        hotspot.add(designer.getRootComponent());
    }

    public void startSelectionResizing(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();

        current_x = x;
        current_y = y;
        hotspot.clear();
        hotspot.addAll(selectionModel.getSelectedComponents());
    }

    public void selectComponents(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();

        hotspot.clear();

        Rectangle bounds = createCurrentBounds(x, y);

        hotspot.addAll(getHotspotComponents(bounds, designer.getRootComponent()));

        if (!hotspot.isEmpty() && ((x != current_x) || (y != current_y))) {
            selectionModel.setSelectedComponents(hotspot);
        }

        selectionModel.setHotspotBounds(null);
    }

    private Rectangle createCurrentBounds(int x, int y) {
        Rectangle bounds = new Rectangle();

        bounds.x = Math.min(x, current_x);
        bounds.y = Math.min(y, current_y);
        bounds.width = Math.max(x, current_x) - bounds.x;
        bounds.height = Math.max(y, current_y) - bounds.y;

        return bounds;
    }

    ArrayList<Component> getHotspotComponents(Rectangle selection, Component root) {
        ArrayList<Component> components = new ArrayList<Component>();

        if (!root.isVisible()&&!designer.isRootComponent(root)) {
            return components;
        }

        if (root instanceof Container) {
            Container container = (Container) root;
            int count = container.getComponentCount();
            Rectangle clipped = new Rectangle(selection);

            for (int i = count - 1; i >= 0; i--) {
                Component child = container.getComponent(i);

                if (selection.contains(child.getBounds()) && Util.isDesigning(child)) {
                    components.add(child);
                } else {
                    clipped.x = selection.x - child.getX();
                    clipped.y = selection.y - child.getY();
                    components.addAll(getHotspotComponents(clipped, child));
                }
            }
        }

        return components;
    }

    public void reset() {
        hotspot.clear();
        location = Location.outer;
        selecting = false;
    }

    public void updateLocRel2Root(MouseEvent e) {
        location = designer.getLoc2Root(e);
    }

    public void updateLocRel2Comp(MouseEvent e, Component comp) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();
        Rectangle rect = Util.getRelativeBounds(comp);
        Location loc = getLocRel2Bounds(x, y, rect);

        if (location != loc) {
            location = loc;
        }
    }

    Location getLocRel2Bounds(int x, int y, Rectangle bounds) {
        if (x < (bounds.x - BOX_SIZE)) {
            return Location.outer;
        } else if ((x >= (bounds.x - BOX_SIZE)) && (x <= bounds.x)) {
            if (y < (bounds.y - BOX_SIZE)) {
                return Location.outer;
            } else if ((y >= (bounds.y - BOX_SIZE)) && (y <= bounds.y)) {
                return Location.left_top;
            } else if ((y > bounds.y) && (y < (bounds.y + bounds.height))) {
                return Location.left;
            } else if ((y >= (bounds.y + bounds.height)) && (y <= (bounds.y + bounds.height + BOX_SIZE))) {
                return Location.left_bottom;
            } else {
                return Location.outer;
            }
        } else if ((x > bounds.x) && (x < (bounds.x + bounds.width))) {
            if (y < (bounds.y - BOX_SIZE)) {
                return Location.outer;
            } else if ((y >= (bounds.y - BOX_SIZE)) && (y <= bounds.y)) {
                return Location.top;
            } else if ((y > bounds.y) && (y < (bounds.y + bounds.height))) {
                return Location.inner;
            } else if ((y >= (bounds.y + bounds.height)) && (y <= (bounds.y + bounds.height + BOX_SIZE))) {
                return Location.bottom;
            } else {
                return Location.outer;
            }
        } else if ((x >= (bounds.x + bounds.width)) && (x <= (bounds.x + bounds.width + BOX_SIZE))) {
            if (y < (bounds.y - BOX_SIZE)) {
                return Location.outer;
            } else if ((y >= (bounds.y - BOX_SIZE)) && (y <= bounds.y)) {
                return Location.right_top;
            } else if ((y > bounds.y) && (y < (bounds.y + bounds.height))) {
                return Location.right;
            } else if ((y >= (bounds.y + bounds.height)) && (y <= (bounds.y + bounds.height + BOX_SIZE))) {
                return Location.right_bottom;
            } else {
                return Location.outer;
            }
        } else {
            return Location.outer;
        }
    }

    public void dragging(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();
        int dx = x - current_x;
        int dy = y - current_y;

        for (Component comp : hotspot) {
            location.drag(dx, dy, comp);
            if (designer.isRootComponent(comp)) {
                designer.getParent().doLayout();
            }
        }
        current_x = x;
        current_y = y;
        designer.setInvalidated(true);
    }

    public void releaseDragging(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();
        int dx = x - current_x;
        int dy = y - current_y;

        for (Component comp : hotspot) {
            location.drag(dx, dy, comp);

            if (comp instanceof Container) {
                Container container = (Container) comp;
                Util.layoutContainer(container);
            }
        }

        current_x = x;
        current_y = y;
    }

    public void changeSelection(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();
        Rectangle bounds = createCurrentBounds(x, y);
        selectionModel.setHotspotBounds(bounds);
    }
}