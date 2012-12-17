/*
 * JFrameAdapter.java
 *
 * Created on 2007-10-1, 23:38:38
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.LayoutAdapter;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.Painter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.TopContainerAdapter;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.editors.TextEditor;
import dyno.swing.designer.beans.models.AddingModel;
import dyno.swing.designer.properties.BoundsGroupModel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public class JFrameAdapter extends AbstractContainerAdapter implements TopContainerAdapter {

    private JFrame frame;
    private Container contentPane;

    public JFrameAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
        frame = (JFrame) component;
        contentPane = frame.getContentPane();
    }

    @Override
    public GroupModel getLayoutProperties() {
        LayoutManager layout = contentPane.getLayout();
        if (layout != null) {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            return adapter.getLayoutProperties();
        } else {
            return null;
        }
    }
    @Override
    public Painter getAnchorPainter() {
        JFrame jframe = (JFrame) component;
        JPanel panel = (JPanel) jframe.getContentPane();
        LayoutManager layout = panel.getLayout();
        if (layout == null) {
            return null;
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, panel);
            return adapter.getAnchorPainter();
        }
    }
    public int getChildCount() {
        return contentPane.getComponentCount();
    }

    @Override
    public Component getChild(int index) {
        return contentPane.getComponent(index);
    }

    @Override
    public int getIndexOfChild(Component child) {
        return Util.indexOfComponent(contentPane, child);
    }

    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        LayoutManager layout = contentPane.getLayout();
        if (layout == null) {
            return new BoundsGroupModel(contentPane, bean);
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            return adapter.getLayoutConstraints(bean);
        }
    }

    public boolean acceptComponent(int x, int y) {
        AddingModel model = designer.getAddingModel();

        if (model != null) {
            //获取该组件所在的焦点容器
            Rectangle rect = Util.getRelativeBounds(component);
            Component bean = model.getBean();
            if (bean instanceof JInternalFrame) {
                return false;
            }
            //调用其acceptComponent接受组件
            return addBean(bean, x - designer.getOuterLeft() - rect.x, y - designer.getOuterTop() - rect.y);
        } else {
            return false;
        }
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
    private JPopupMenu popupMenu;

    protected boolean addBean(Component bean, int x, int y) {
        LayoutManager layout = contentPane.getLayout();
        Rectangle rect = Util.getRelativeBounds(contentPane);
        x -= rect.x;
        y -= rect.y;

        if (layout == null) {
            bean.setLocation(x - (bean.getWidth() / 2), y - (bean.getHeight() / 2));
            contentPane.add(bean);
            Util.layoutContainer(contentPane);
            return true;
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            if (adapter.accept(bean, x, y)) {
                adapter.addBean(bean, x, y);
                return true;
            } else {
                return false;
            }
        }
    }

    public void paintComponentMascot(Graphics g) {
    }

    public void showComponent(Component child) {
        LayoutManager layout = contentPane.getLayout();

        if (layout != null) {
            LayoutAdapter layoutAdapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            layoutAdapter.showComponent(child);
        }
    }

    public void addNextComponent(Component dragged) {
        LayoutManager layout = contentPane.getLayout();

        if (layout == null) {
            contentPane.add(dragged);
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            adapter.addNextComponent(dragged);
        }
    }

    public void addBefore(Component target, Component added) {
        LayoutManager layout = contentPane.getLayout();

        if (layout == null) {
            int index = Util.indexOfComponent(contentPane, target);

            if (index == -1) {
                contentPane.add(added, 0);
            } else {
                contentPane.add(added, index);
            }
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            adapter.addBefore(target, added);
        }
    }

    public void addAfter(Component target, Component added) {
        int index = Util.indexOfComponent(contentPane, target);
        LayoutManager layout = contentPane.getLayout();

        if (layout == null) {
            if (index == -1) {
                contentPane.add(added);
            } else {
                contentPane.add(added, index);
            }
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            adapter.addAfter(target, added);
        }
    }

    public boolean canAcceptMoreComponent() {
        LayoutManager layout = contentPane.getLayout();

        if (layout == null) {
            return true;
        } else {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
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
    public String getLayoutCode() {
        LayoutManager layout = contentPane.getLayout();
        if (layout != null) {
            if (layout instanceof FlowLayout) {
                return null;
            } else {
                LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
                return adapter.getLayoutCode();
            }
        } else {
            return "null";
        }
    }

    @Override
    public HoverPainter getPainter() {
        LayoutManager layout = contentPane.getLayout();
        if (layout != null) {
            LayoutAdapter adapter = AdapterBus.getLayoutAdapter(designer, contentPane);
            return adapter.getPainter();
        } else {
            return null;
        }
    }

    @Override
    public DesignerEditor getDesignerEditor(int x, int y) {
        Rectangle bounds = getEditorBounds(x, y);
        if (!bounds.contains(x, y)) {
            return null;
        }
        if (designerEditor == null) {
            designerEditor = new TextEditor();
        }
        return designerEditor;
    }

    @Override
    public Rectangle getEditorBounds(int x, int y) {
        return new Rectangle(4, 4, component.getWidth() - 8, 23);
    }

    @Override
    public Object getBeanValue() {
        return frame.getTitle();
    }

    @Override
    public void setBeanValue(Object value) {
        String title = (String) value;
        frame.setTitle(title);
    }

    @Override
    public void preview() {
        final int def_op = frame.getDefaultCloseOperation();
        final Dimension size = frame.getSize();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowAdapter wa = new WindowAdapter() {

            public void windowClosed(WindowEvent e) {
                frame.setDefaultCloseOperation(def_op);
                frame.removeWindowListener(this);
                designer.setRootComponent(frame);
                frame.setSize(size);
                frame.setLocation(0, 0);
                Util.layoutContainer((Container) frame.getContentPane());
                designer.repaint();
            }
        };
        frame.addWindowListener(wa);
        frame.setVisible(true);
    }

    public Border getBorder() {
        return new FrameBorder(designer, (JFrame) component);
    }

    public Component getContent() {
        return frame.getContentPane();
    }
}