/*
 * BorderLayoutAdapter.java
 *
 * Created on 2007-8-2, 15:45:31
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.BorderLayoutPainter;
import dyno.swing.designer.properties.BorderLayoutConstraints;
import dyno.swing.designer.properties.BorderLayoutProperties;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;


/**
 *
 * @author William Chen
 */
public class BorderLayoutAdapter extends AbstractLayoutAdapter implements Constants {

    private HoverPainter painter;

    public BorderLayoutAdapter(SwingDesigner designer, Container container) {
        super(designer, container);
        painter = new BorderLayoutPainter(designer, container);
    }

    @Override
    public HoverPainter getPainter() {
        return painter;
    }

    @Override
    public boolean addBean(Component child, int x, int y) {
        String placement = getPlacement(child, x, y);
        container.add(child, placement);
        Util.layoutContainer(container);

        return true;
    }

    @Override
    public boolean accept(Component bean, int x, int y) {
        
        String placement = getPlacement(bean, x, y);
        BorderLayout layout = (BorderLayout) container.getLayout();
        Component comp = layout.getLayoutComponent(placement);

        return comp == null;
    }

    public Dimension getPreferredSize(Component component) {
        int hw = container.getWidth();
        int hh = container.getHeight();
        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, component);
        Dimension prefSize = component.getSize();

        if (prefSize.width > (hw / 3)) {
            prefSize.width = hw / 3;
        }

        if (prefSize.height > (hh / 3)) {
            prefSize.height = hh / 3;
        }

        return prefSize;
    }

    private String getPlacement(Component bean, int x, int y) {
        int width = container.getWidth();
        int height = container.getHeight();
        BorderLayout layout = (BorderLayout) container.getLayout();
        Dimension prefSize = getPreferredSize(bean);

        if (y < prefSize.height) {
            return BorderLayout.NORTH;
        } else if ((y >= prefSize.height) && (y < (height - prefSize.height))) {
            if (x < prefSize.width) {
                return BorderLayout.WEST;
            } else if ((x >= prefSize.width) && (x < (width - prefSize.width))) {
                return BorderLayout.CENTER;
            } else {
                return BorderLayout.EAST;
            }
        } else {
            return BorderLayout.SOUTH;
        }
    }

    public void addNextComponent(Component dragged) {
        BorderLayout layout = (BorderLayout) container.getLayout();
        Component north = layout.getLayoutComponent(BorderLayout.NORTH);
        Component south = layout.getLayoutComponent(BorderLayout.SOUTH);
        Component west = layout.getLayoutComponent(BorderLayout.WEST);
        Component east = layout.getLayoutComponent(BorderLayout.EAST);
        Component center = layout.getLayoutComponent(BorderLayout.CENTER);

        if (north == null) {
            container.add(dragged, BorderLayout.NORTH);
        } else if (south == null) {
            container.add(dragged, BorderLayout.SOUTH);
        } else if (west == null) {
            container.add(dragged, BorderLayout.WEST);
        } else if (east == null) {
            container.add(dragged, BorderLayout.EAST);
        } else if (center == null) {
            container.add(dragged, BorderLayout.CENTER);
        }

        Util.layoutContainer(container);
    }

    public void addBefore(Component target, Component added) {
        addNextComponent(added);
    }

    public void addAfter(Component target, Component added) {
        addNextComponent(added);
    }

    public boolean canAcceptMoreComponent() {
        BorderLayout layout = (BorderLayout) container.getLayout();
        Component north = layout.getLayoutComponent(BorderLayout.NORTH);
        Component south = layout.getLayoutComponent(BorderLayout.SOUTH);
        Component west = layout.getLayoutComponent(BorderLayout.WEST);
        Component east = layout.getLayoutComponent(BorderLayout.EAST);
        Component center = layout.getLayoutComponent(BorderLayout.CENTER);

        return (north == null) || (south == null) || (west == null) || (east == null) || (center == null);
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        BorderLayout layout = (BorderLayout) container.getLayout();
        return new BorderLayoutConstraints(container, layout, bean);
    }

    @Override
    public GroupModel getLayoutProperties() {
        return new BorderLayoutProperties((BorderLayout) container.getLayout());
    }

    @Override
    public String getLayoutCode() {
        BorderLayout border_layout=(BorderLayout)this.layout;
        int hgap=border_layout.getHgap();
        int vgap=border_layout.getVgap();
        if(hgap==0 && vgap==0)
            return Constants.VAR_CONTAINER+".setLayout(new java.awt.BorderLayout());\n";
        else
            return Constants.VAR_CONTAINER+".setLayout(new java.awt.BorderLayout("+hgap+", "+vgap+"));\n";
    }
}