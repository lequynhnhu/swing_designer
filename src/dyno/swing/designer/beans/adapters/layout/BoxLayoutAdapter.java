/*
 * BoxLayoutAdapter.java
 *
 * Created on 2007年5月5日, 上午10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;

import java.awt.Component;
import java.awt.Container;
import javax.swing.BoxLayout;


/**
 *
 * @author William Chen
 */
public class BoxLayoutAdapter extends AbstractLayoutAdapter implements Constants {
    public BoxLayoutAdapter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    @Override
    public boolean addBean(Component child, int x, int y) {
        container.add(child);
        Util.layoutContainer(container);

        return true;
    }

    @Override
    public boolean accept(Component bean, int x, int y) {
        return true;
    }

    @Override
    public HoverPainter getPainter() {
        return null;
    }

    @Override
    public String getLayoutCode() {
        BoxLayout boxLayout=(BoxLayout)layout;
        int axis=boxLayout.getAxis();
        
        String parentName=Util.getComponentName(container);
        
        return Constants.VAR_CONTAINER+".setLayout(new javax.swing.BoxLayout("+parentName+", "+axis+"));\n";
    }
}
