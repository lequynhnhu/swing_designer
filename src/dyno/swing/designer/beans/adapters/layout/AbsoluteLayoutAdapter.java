/*
 * AbsoluteLayoutAdapter.java
 *
 * Created on 2007年5月5日, 上午10:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.NullPainter;

import java.awt.Component;
import java.awt.Container;


/**
 *
 * @author William Chen
 */
public class AbsoluteLayoutAdapter extends AbstractLayoutAdapter
    implements Constants {
    public AbsoluteLayoutAdapter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    @Override
    public boolean accept(Component bean, int x, int y) {
        return true;
    }

    @Override
    public boolean addBean(Component bean, int x, int y) {
        int w = bean.getWidth() / 2;
        int h = bean.getHeight() / 2;
        bean.setLocation(x - w, y - h);
        container.add(bean);
        Util.layoutContainer(container);

        return true;
    }

    @Override
    public HoverPainter getPainter() {
        return new NullPainter(designer, container);
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return null;
    }

    @Override
    public String getLayoutCode() {        
        return Constants.VAR_CONTAINER+".setLayout(new dyno.swing.beans.layouts.AbsoluteLayout());\n";
    }
}
