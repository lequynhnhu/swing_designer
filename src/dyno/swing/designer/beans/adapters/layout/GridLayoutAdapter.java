/*
 * GridLayoutAdapter.java
 *
 * Created on 2007年5月5日, 下午12:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.painters.GridLayoutAnchorPainter;
import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.GridLayoutPainter;
import dyno.swing.designer.properties.GridLayoutProperties;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;


/**
 *
 * @author William Chen
 */
public class GridLayoutAdapter extends AbstractLayoutAdapter
        implements Constants {

    public GridLayoutAdapter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    @Override
    public boolean accept(Component bean, int x, int y) {
        return true;
    }

    @Override
    public boolean addBean(Component bean, int x, int y) {
        container.add(bean);
        Util.layoutContainer(container);

        return true;
    }

    @Override
    public HoverPainter getPainter() {
        return new GridLayoutPainter(designer, container);
    }
    @Override
    public Painter getAnchorPainter() {
        return new GridLayoutAnchorPainter(designer, container);
    }

    @Override
    public GroupModel getLayoutProperties() {
        return new GridLayoutProperties((GridLayout) container.getLayout());
    }

    @Override
    public String getLayoutCode() {
        GridLayout gl = (GridLayout) layout;
        int row = gl.getRows();
        int column = gl.getColumns();
        int hgap = gl.getHgap();
        int vgap = gl.getVgap();
        if (hgap == 0 && vgap == 0) {
            if (row == 1 && column == 0) {
                return Constants.VAR_CONTAINER+".setLayout(new java.awt.GridLayout());\n";
            } else {
                return Constants.VAR_CONTAINER+".setLayout(new java.awt.GridLayout(" + row + ", " + column + "));\n";
            }
        } else {
            return Constants.VAR_CONTAINER+".setLayout(new java.awt.GridLayout(" + row + ", " + column + ", " + hgap + ", " + vgap + "));\n";
        }
    }
}
