/*
 * CardLayoutAdapter.java
 *
 * Created on 2007年5月5日, 上午11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.NullPainter;
import dyno.swing.designer.properties.CardLayoutProperties;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;


/**
 *
 * @author William Chen
 */
public class CardLayoutAdapter extends AbstractLayoutAdapter
    implements Constants {
    public CardLayoutAdapter(SwingDesigner designer, Container container){
        super(designer, container);
    }
    @Override
    public boolean accept(Component bean, int x, int y) {
        return true;
    }

    @Override
    public boolean addBean(Component bean, int x, int y) {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        container.add(bean, Util.getComponentName(bean));
        Util.layoutContainer(container);
        return true;
    }

    @Override
    public HoverPainter getPainter() {
        return new NullPainter(designer, container);
    }

    public void showComponent(Component child) {
        LayoutManager layout = container.getLayout();
        CardLayout cardLayout = (CardLayout) layout;
        cardLayout.show(container, Util.getComponentName(child));
    }

    @Override
    public GroupModel getLayoutProperties() {
        return new CardLayoutProperties((CardLayout)container.getLayout());
    }

    @Override
    public String getLayoutCode() {
        CardLayout cardLayout=(CardLayout)layout;
        int hgap=cardLayout.getHgap();
        int vgap=cardLayout.getVgap();
        if(hgap==0 && vgap==0)
            return Constants.VAR_CONTAINER+".setLayout(new java.awt.CardLayout());\n";
        else
            return Constants.VAR_CONTAINER+".setLayout(new java.awt.CardLayout("+hgap+", "+vgap+"));\n";
        
    }
    
}
