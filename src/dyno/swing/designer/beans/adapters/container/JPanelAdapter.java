/*
 * JPanelAdapter.java
 *
 * Created on 2007年5月2日, 下午11:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.LayoutAdapter;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.TopContainerAdapter;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.NullPainter;
import dyno.swing.designer.properties.BoundsGroupModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.border.Border;


/**
 *
 * @author William Chen
 */
public class JPanelAdapter extends AbstractContainerAdapter implements TopContainerAdapter {

    private JPopupMenu popupMenu;

    public JPanelAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();
        if (layout == null) {
            return new BoundsGroupModel(panel, bean);
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            return adapter.getLayoutConstraints(bean);
        }
    }

    @Override
    public HoverPainter getPainter() {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();
        if (layout == null) {
            return new NullPainter(designer, panel);
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            return adapter.getPainter();
        }
    }

    @Override
    public Painter getAnchorPainter() {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();
        if (layout == null) {
            return null;
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            return adapter.getAnchorPainter();
        }
    }

    protected boolean addBean(Container container, Component bean, int x, int y) {
        LayoutManager layout = container.getLayout();

        if (layout == null) {
            return super.addBean(container, bean, x, y);
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, container);
            if (adapter.accept(bean, x, y)) {
                adapter.addBean(bean, x, y);
                return true;
            } else {
                return false;
            }
        }
    }

    public void paintComponentMascot(Graphics g) {
        super.paintComponentMascot(g);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(0, 0, component.getWidth() - 1, component.getHeight() - 1);
    }

    public JPopupMenu getContextPopupMenu(MouseEvent e) {
        if (popupMenu == null) {
            popupMenu = super.getContextPopupMenu(e);
            int count = popupMenu.getComponentCount();

            if (count > 0) {
                popupMenu.add(new JSeparator());
            }

            JMenu setLayout = createLayoutMenu(component);
            popupMenu.add(setLayout);
        }
        return popupMenu;
    }

    public void showComponent(Component child) {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();

        if (layout != null) {
            LayoutAdapter layoutAdapter = AdapterBus.getLayoutAdapter(designer, panel);
            layoutAdapter.showComponent(child);
        }
    }

    public void addNextComponent(Component dragged) {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();

        if (layout == null) {
            panel.add(dragged);
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            adapter.addNextComponent(dragged);
        }
    }

    public void addBefore(Component target, Component added) {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();

        if (layout == null) {
            int index = Util.indexOfComponent(panel, target);

            if (index == -1) {
                panel.add(added, 0);
            } else {
                panel.add(added, index);
            }
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            adapter.addBefore(target, added);
        }
    }

    public void addAfter(Component target, Component added) {
        JPanel panel = (JPanel) component;
        int index = Util.indexOfComponent(panel, target);
        LayoutManager layout = panel.getLayout();

        if (layout == null) {
            if (index == -1) {
                panel.add(added);
            } else {
                panel.add(added, index);
            }
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            adapter.addAfter(target, added);
        }
    }

    public boolean canAcceptMoreComponent() {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();

        if (layout == null) {
            return true;
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);

            return adapter.canAcceptMoreComponent();
        }
    }

    public boolean canAddBefore(Component hovering) {
        return true;
    }

    public boolean canAddAfter(Component hovering) {
        return true;
    }

    @Override
    public GroupModel getLayoutProperties() {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();
        if (layout != null) {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            return adapter.getLayoutProperties();
        } else {
            return null;
        }
    }

    @Override
    public String getLayoutCode() {
        JPanel panel = (JPanel) component;
        LayoutManager layout = panel.getLayout();
        if (layout != null) {
            if (layout instanceof FlowLayout) {
                return null;
            } else {
                LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
                return adapter.getLayoutCode();
            }
        } else {
            return "null";
        }
    }

    @Override
    public void preview() {
        final JFrame frame = new JFrame();
        final Rectangle bounds = component.getBounds();
        frame.add(component, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosed(WindowEvent e) {
                frame.remove(component);
                designer.setRootComponent(component);
                component.setBounds(bounds);
                Util.layoutContainer((Container) component);
                designer.repaint();
            }
        });
        frame.setSize(bounds.width, bounds.height);
        frame.setVisible(true);
    }

    public Border getBorder() {
        return null;
    }

    public Component getContent() {
        return component;
    }
}