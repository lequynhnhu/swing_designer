/*
 * GridBagLayoutAdapter.java
 *
 * Created on 2007年5月5日, 下午1:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;

import dyno.swing.designer.beans.painters.GridBagLayoutAnchorPainter;
import dyno.swing.designer.beans.painters.GridBagLayoutPainter;
import dyno.swing.designer.properties.GridBagLayoutConstraints;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;


/**
 *
 * @author William Chen
 */
public class GridBagLayoutAdapter extends AbstractLayoutAdapter
    implements Constants {
    public GridBagLayoutAdapter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    public boolean accept(Component bean, int x, int y) {
        return true;
    }

    public boolean addBean(Component bean, int x, int y) {
        GridBagLayout gridbag_layout=(GridBagLayout)container.getLayout();
        Point origin = gridbag_layout.getLayoutOrigin();
        int[][] dims = gridbag_layout.getLayoutDimensions();
        int xi = getIndex(origin.x, dims[0], x);
        int yi = getIndex(origin.y, dims[1], y);
        GridBagConstraints constraints=new GridBagConstraints();
        if(xi!=-1)
            constraints.gridx=xi;
        if(yi!=-1)
            constraints.gridy=yi;
        container.add(bean, constraints);
        Util.layoutContainer(container);
        return true;
    }
    private int getIndex(int start, int[] dims, int hotspot) {
        int i;
        for (i = 0; i < dims.length; i++) {
            if (hotspot < start) {
                break;
            }
            start += dims[i];
        }
        if (i == 0) {
            return -1;
        }
        if (i == dims.length) {
            if (hotspot < start) {
                return dims.length - 1;
            } else {
                return dims.length;
            }
        } else {
            return i - 1;
        }
    }
    public HoverPainter getPainter() {
        return new GridBagLayoutPainter(designer, container);
    }

    @Override
    public Painter getAnchorPainter() {
        return new GridBagLayoutAnchorPainter(designer, container);
    }

    @Override
    public String getLayoutCode() {
        return Constants.VAR_CONTAINER+".setLayout(new java.awt.GridBagLayout());\n";
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return new GridBagLayoutConstraints(container, (GridBagLayout)layout, bean);
    }    
}
