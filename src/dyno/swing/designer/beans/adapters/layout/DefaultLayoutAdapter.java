/*
 * DefaultLayoutAdapter.java
 *
 * Created on 2007年5月3日, 上午12:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;

import java.awt.Component;
import java.awt.Container;


/**
 *
 * @author William Chen
 */
public class DefaultLayoutAdapter extends AbstractLayoutAdapter {
    /** Creates a new instance of DefaultLayoutAdapter */
    public DefaultLayoutAdapter(SwingDesigner designer, Container c) {
        super(designer, c);
    }

    @Override
    public HoverPainter getPainter() {
        return null;
    }

    @Override
    public boolean addBean(Component child, int x, int y) {
        return false;
    }

    @Override
    public boolean accept(Component bean, int x, int y) {
        return false;
    }

    @Override
    public String getLayoutCode() {
        return null;
    }
}
