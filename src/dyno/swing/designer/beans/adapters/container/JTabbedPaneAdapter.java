/*
 * JTabbedPaneAdapter.java
 *
 * Created on 2007年5月4日, 上午12:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.container;

import dyno.swing.designer.beans.ConstraintsGroupModel;
import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SpecialContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.editors.TextEditor;
import dyno.swing.designer.beans.painters.TabbedPaneOutlinePainter;
import dyno.swing.designer.properties.TabbedPaneConstraints;
import dyno.swing.designer.properties.ValidationException;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


import javax.swing.JTabbedPane;


/**
 *
 * @author William Chen
 */
public class JTabbedPaneAdapter extends AbstractContainerAdapter
    implements SpecialContainerAdapter {
    /** Creates a new instance of JTabbedPaneAdapter */
    public JTabbedPaneAdapter(SwingDesigner designer, Component component) {
        super(designer, component);
    }

    @Override
    public void paintComponentMascot(Graphics g) {
        super.paintComponentMascot(g);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(0, 0, component.getWidth() - 1, component.getHeight() - 1);
    }

    @Override
    protected boolean addBean(Container container, Component bean, int x, int y) {
        JTabbedPane tabbedPane = (JTabbedPane) container;
        tabbedPane.addTab(Util.getComponentName(bean), bean);
        Util.layoutContainer(tabbedPane);

        return true;
    }

    @Override
    public HoverPainter getPainter() {
        return new TabbedPaneOutlinePainter(designer, (Container)component);
    }

    @Override
    public boolean componentClicked(MouseEvent e) {
        Rectangle rel = Util.getRelativeBounds(component);
        int x = e.getX() - designer.getOuterLeft() - rel.x;
        int y = e.getY() - designer.getOuterTop() - rel.y;
        JTabbedPane tabbedPane = (JTabbedPane) component;
        int count = tabbedPane.getTabCount();

        for (int i = 0; i < count; i++) {
            Rectangle tabBounds = tabbedPane.getBoundsAt(i);

            if ((tabBounds != null) && tabBounds.contains(x, y)) {
                tabbedPane.setSelectedIndex(i);

                return false;
            }
        }

        return true;
    }

    public int getIndexOfChild(Component child) {
        JTabbedPane tp = (JTabbedPane) component;

        return tp.indexOfComponent(child);
    }

    public int getChildCount() {
        JTabbedPane tp = (JTabbedPane) component;

        return tp.getComponentCount();
    }

    public Component getChild(int index) {
        JTabbedPane tp = (JTabbedPane) component;

        return tp.getComponentAt(index);
    }

    public void showComponent(Component child) {
        JTabbedPane tp = (JTabbedPane) component;
        tp.setSelectedComponent(child);
    }

    public void addNextComponent(Component dragged) {
        ((JTabbedPane) component).addTab(Util.getComponentName(dragged), dragged);
    }

    public void addBefore(Component target, Component added) {
        JTabbedPane tp = (JTabbedPane) component;
        int index = tp.indexOfComponent(target);

        if (index != -1) {
            tp.insertTab(Util.getComponentName(added), null, added, null, index);
        } else {
            tp.addTab(Util.getComponentName(added), added);
        }
    }

    public void addAfter(Component target, Component added) {
        JTabbedPane tp = (JTabbedPane) component;
        int index = tp.indexOfComponent(target);

        if (index != -1) {
            index++;

            if (index > tp.getTabCount()) {
                tp.addTab(Util.getComponentName(added), added);
            } else {
                tp.insertTab(Util.getComponentName(added), null, added, null, index);
            }
        } else {
            tp.addTab(Util.getComponentName(added), added);
        }
    }

    public boolean canAcceptMoreComponent() {
        return true;
    }

    public boolean canAddBefore(Component hovering) {
        return true;
    }

    public boolean canAddAfter(Component hovering) {
        return true;
    }

    public void validateBeanValue(Object value)
        throws ValidationException {
        if ((value == null) || (((String) value).trim().length() == 0)) {
            throw new ValidationException("Tab title cannot be empty!");
        }
    }

    public void setBeanValue(Object value) {
        JTabbedPane jtp = (JTabbedPane) component;
        int index = jtp.getSelectedIndex();

        if (index != -1) {
            jtp.setTitleAt(index, (String) value);
        }
    }

    public Object getBeanValue() {
        JTabbedPane jtp = (JTabbedPane) component;
        int index = jtp.getSelectedIndex();

        if (index != -1) {
            return jtp.getTitleAt(index);
        } else {
            return null;
        }
    }

    public Rectangle getEditorBounds(int x, int y) {
        JTabbedPane jtp = (JTabbedPane) component;
        int index = getTabIndexAt(jtp, x, y);

        if (index != -1) {
            return jtp.getBoundsAt(index);
        }

        return null;
    }

    private int getTabIndexAt(JTabbedPane jtp, int x, int y) {
        int count = jtp.getTabCount();

        for (int i = 0; i < count; i++) {
            Rectangle rect = jtp.getBoundsAt(i);

            if ((rect != null) && rect.contains(x, y)) {
                return i;
            }
        }

        return -1;
    }

    public DesignerEditor getDesignerEditor(int x, int y) {
        JTabbedPane jtp = (JTabbedPane) component;
        int index = getTabIndexAt(jtp, x, y);

        if (index != -1) {
            if (designerEditor == null) {
                designerEditor = new TextEditor();
            }

            return designerEditor;
        } else {
            return null;
        }
    }

    @Override
    public ConstraintsGroupModel getLayoutConstraints(Component bean) {
        return new TabbedPaneConstraints(((JTabbedPane)component), bean);
    }
    
}
