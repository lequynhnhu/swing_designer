/*
 * JInternalFrameAdapter.java
 *
 * Created on 2007-9-4, 0:01:53
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.editors.TextEditor;
import dyno.swing.designer.beans.painters.InternalFramePainter;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

/**
 *
 * @author William Chen
 */
public class JInternalFrameAdapter extends AbstractContainerAdapter {

    protected JInternalFrame frame;
    protected Container contentPane;
    protected HoverPainter painter;
    private JPopupMenu popupMenu;

    public JInternalFrameAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
        frame = (JInternalFrame) component;
        contentPane = frame.getContentPane();
        painter = new InternalFramePainter(designer, frame);
    }

    public JPopupMenu getContextPopupMenu(MouseEvent e) {
        if (popupMenu == null) {
            popupMenu = super.getContextPopupMenu(e);
            int count = popupMenu.getComponentCount();

            if (count > 0) {
                popupMenu.add(new JSeparator());
            }

            JMenu setLayout = createLayoutMenu(contentPane);
            popupMenu.add(setLayout);
        }
        return popupMenu;
    }

    @Override
    public Object getBeanValue() {
        return frame.getTitle();
    }

    @Override
    public DesignerEditor getDesignerEditor(int x, int y) {
        Point p = SwingUtilities.convertPoint(frame, x, y, contentPane);
        Rectangle rect = contentPane.getBounds();
        rect.x = 0;
        rect.y = 0;
        if (rect.contains(p)) {
            return null;
        } else {
            if (designerEditor == null) {
                designerEditor = new TextEditor();
            }
            return designerEditor;
        }
    }

    @Override
    public Rectangle getEditorBounds(int x, int y) {
        Rectangle frect = frame.getBounds();
        Rectangle crect = contentPane.getBounds();
        frect.x = 4;
        frect.y = 4;
        frect.width -= 8;
        frect.height = frect.height - crect.height - 12;
        return frect;
    }

    @Override
    public void setBeanValue(Object value) {
        frame.setTitle((String) value);
    }

    public void addNextComponent(Component dragged) {
    }

    @Override
    public HoverPainter getPainter() {
        return painter;
    }

    public void addBefore(Component target, Component added) {
    }

    public void addAfter(Component target, Component added) {
    }

    public boolean canAcceptMoreComponent() {
        return true;
    }

    @Override
    public int getChildCount() {
        return contentPane.getComponentCount();
    }

    @Override
    public Component getChild(int index) {
        return contentPane.getComponent(index);
    }

    @Override
    public void removeComponent(Component bean) {
        contentPane.remove(bean);
    }

    @Override
    public boolean acceptComponent(int x, int y) {
        Point p = new Point(x, y);
        Rectangle contentRect = Util.getRelativeBounds(contentPane);
        contentRect.x += designer.getOuterLeft();
        contentRect.y += designer.getOuterTop();
        if (!contentRect.contains(p)) {
            return false;
        } else {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, contentPane);
            if (adapter instanceof ContainerAdapter) {
                return ((ContainerAdapter) adapter).acceptComponent(x, y);
            } else {
                return false;
            }
        }
    }

    @Override
    public int getIndexOfChild(Component child) {
        int count = contentPane.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component component = contentPane.getComponent(i);
            if (component == child) {
                return i;
            }
        }
        return -1;
    }

    public boolean canAddBefore(Component hovering) {
        return true;
    }

    public boolean canAddAfter(Component hovering) {
        return true;
    }

    @Override
    public String getAddComponentCode(Component bean) {
        ContainerAdapter adapter=(ContainerAdapter)AdapterBus.getComponentAdapter(designer, contentPane);
        String code=adapter.getAddComponentCode(bean);
        return code;
    }
}