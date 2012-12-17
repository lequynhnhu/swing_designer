/*
 * JSplitPaneAdapter.java
 *
 * Created on August 3, 2007, 12:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SpecialContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.SplitPanePainter;
import dyno.swing.designer.properties.SplitPaneConstraints;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JSplitPane;


/**
 *
 * @author William Chen
 */
public class JSplitPaneAdapter extends AbstractContainerAdapter
    implements SpecialContainerAdapter {
    /** Creates a new instance of JSplitPaneAdapter */
    public JSplitPaneAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    @Override
    public HoverPainter getPainter() {
        return new SplitPanePainter(designer, (Container)component);
    }

    @Override
    protected boolean addBean(Container container, Component bean, int x, int y) {
        Rectangle rect = container.getBounds();
        rect.x = 0;
        rect.y = 0;

        JSplitPane splitPane = (JSplitPane) container;

        if (isOnLeft(splitPane, new Point(x, y), rect)) {
            if (isLeftAccept(splitPane)) {
                splitPane.setLeftComponent(bean);
                Util.layoutContainer(splitPane);
                return true;
            } else {
                return false;
            }
        } else {
            if (isRightAccept(splitPane)) {
                splitPane.setRightComponent(bean);
                Util.layoutContainer(splitPane);
                return true;
            } else {
                return false;
            }
        }
    }

    private static boolean isHorizontal(JSplitPane splitPane) {
        int orient = splitPane.getOrientation();

        return orient == JSplitPane.HORIZONTAL_SPLIT;
    }

    private static boolean isOnLeft(JSplitPane splitPane, Point hotspot,
        Rectangle hotspot_bounds) {
        int div = splitPane.getDividerLocation();
        int div_size = splitPane.getDividerSize();
        int x = hotspot.x;
        int y = hotspot.y;
        int hx = hotspot_bounds.x;
        int hy = hotspot_bounds.y;

        return isHorizontal(splitPane) ? (x < (hx + div + (div_size / 2)))
                                       : (y < (hy + div + (div_size / 2)));
    }

    private static boolean isLeftAccept(JSplitPane splitPane) {
        return (splitPane.getLeftComponent() == null) ||
        !Util.isDesigning(splitPane.getLeftComponent());
    }

    private static boolean isRightAccept(JSplitPane splitPane) {
        return (splitPane.getRightComponent() == null) ||
        !Util.isDesigning(splitPane.getRightComponent());
    }

    public int getIndexOfChild(Component child) {
        JSplitPane sp = (JSplitPane) component;
        Component left = sp.getLeftComponent();
        Component right = sp.getRightComponent();

        if (child == left) {
            return 0;
        }

        if (child == right) {
            return 1;
        }

        return -1;
    }

    public int getChildCount() {
        JSplitPane sp = (JSplitPane) component;
        Component left = sp.getLeftComponent();
        Component right = sp.getRightComponent();
        int sum = 0;

        if ((left != null) && Util.isDesigning(left)) {
            sum += 1;
        }

        if ((right != null) && Util.isDesigning(right)) {
            sum += 1;
        }

        return sum;
    }

    public Component getChild(int index) {
        JSplitPane sp = (JSplitPane) component;

        if (index == 0) {
            return sp.getLeftComponent();
        }

        if (index == 1) {
            return sp.getRightComponent();
        }

        return null;
    }

    public void addNextComponent(Component dragged) {
        JSplitPane jsp = (JSplitPane) component;
        Component left = jsp.getLeftComponent();
        Component right = jsp.getRightComponent();

        if ((left == null) || !Util.isDesigning(left)) {
            jsp.setLeftComponent(dragged);
        } else if ((right == null) || !Util.isDesigning(right)) {
            jsp.setRightComponent(dragged);
        }
    }

    public void addBefore(Component target, Component added) {
        JSplitPane jsp = (JSplitPane) component;
        Component left = jsp.getLeftComponent();
        Component right = jsp.getRightComponent();

        if (right == target) {
            jsp.setLeftComponent(added);
        }
    }

    public void addAfter(Component target, Component added) {
        JSplitPane jsp = (JSplitPane) component;
        Component left = jsp.getLeftComponent();
        Component right = jsp.getRightComponent();

        if (left == target) {
            jsp.setRightComponent(added);
        }
    }

    public boolean canAcceptMoreComponent() {
        JSplitPane jsp = (JSplitPane) component;
        Component left = jsp.getLeftComponent();
        Component right = jsp.getRightComponent();

        return (left == null) || !Util.isDesigning(left) || (right == null) ||
        !Util.isDesigning(right);
    }

    public boolean canAddBefore(Component hovering) {
        return canAcceptMoreComponent();
    }

    public boolean canAddAfter(Component hovering) {
        return canAcceptMoreComponent();
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return new SplitPaneConstraints((JSplitPane)component, bean);
    }
}
