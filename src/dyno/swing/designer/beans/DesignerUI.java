/*
 * DesignerUI.java
 *
 * Created on August 1, 2007, 12:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import dyno.swing.designer.beans.models.AddingModel;
import dyno.swing.designer.beans.models.SelectionModel;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;


/**
 * SwingDesigner��UI�࣬��һ����״̬��UI�࣬������SwingDesigner�ĵ�ǰ״̬����
 * �������������õ���ƽ��棬�Լ���ǰ��ƽ����һЩ����״̬������ѡ���ʶ���϶�����
 * �Լ���ǰ������ӵ����
 *
 * @author William Chen
 */
public class DesignerUI extends ComponentUI implements Constants {

    //��ǰ�������

    private SwingDesigner designer;
    private SelectionModel selectionModel;

    /** Creates a new instance of DesignerUI */
    public DesignerUI() {
    }

    @Override
    public void installUI(JComponent c) {
        designer = (SwingDesigner) c;
        selectionModel = designer.getSelectionModel();
    }
    /**
     * ��Ⱦ��ǰ����ƽ����Լ���Ƹ���״̬
     */

    @Override
    public void paint(Graphics g, JComponent c) {
        Component component = designer.getRootComponent();
        if (component != null) {
            //��ƽ���
            paintDesignedComponent(g, component);
        }

        ArrayList<Component> selection = selectionModel.getSelectedComponents();

        if (!selection.isEmpty()) {
            //ѡ����ק
            Rectangle bounds = designer.getRootComponent().getBounds();
            bounds.x = designer.getOuterLeft();
            bounds.y = designer.getOuterTop();
            Graphics clipg = g.create();
            clipg.clipRect(bounds.x, bounds.y, bounds.width + 1, bounds.height + 1);
            paintResizing(clipg, designer);
            clipg.dispose();
        }

        Rectangle hotspot_bounds = selectionModel.getHotspotBounds();

        if (hotspot_bounds != null) {
            //��ǰ����ѡ���
            g.setColor(SELECTION_COLOR);
            g.drawRect(hotspot_bounds.x + designer.getOuterLeft(), hotspot_bounds.y + designer.getOuterTop(), hotspot_bounds.width, hotspot_bounds.height);
        }

        if (designer.getPainter() != null) {
            //ComponentAdapter��LayoutAdapter�ṩ�Ķ����Painter����Painterһ��������ʾ���ã�
            //�൱��һ��������
            designer.getPainter().paint(g);
        }

        AddingModel addingModel = designer.getAddingModel();

        if ((addingModel != null) && (addingModel.getBean() != null)) {
            //��ǰ������ӵ����
            paintAddingBean(g, addingModel);
        }
    }

    /**
     * ��Ⱦ��ǰ������ӵ����������Rendererԭ��
     */
    private void paintAddingBean(Graphics g, final AddingModel addingModel) {
        Component bean = addingModel.getBean();
        int x = addingModel.getCurrentX();
        int y = addingModel.getCurrentY();
        int width = bean.getWidth();
        int height = bean.getHeight();
        Graphics clipg = g.create(x, y, width, height);
        ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();
        //��ֹ˫������Ϊ
        Util.disableBuffer(bean, dbcomponents);

        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, bean);
        //����ComponentAdapter��paintComponentMascot������Ⱦ����������ʾ
        adapter.paintComponentMascot(clipg);
        clipg.dispose();
        //�ָ�˫����
        Util.resetBuffer(dbcomponents);
    }

    private void paintAnchorGrid(SwingDesigner designer, Component parent, Graphics g) {
        ContainerAdapter adapter = (ContainerAdapter) AdapterBus.getComponentAdapter(designer, parent);
        Painter painter = adapter.getAnchorPainter();
        if (painter != null) {
            Rectangle hotspot;
            if (parent instanceof RootPaneContainer) {
                hotspot = Util.computeVisibleRectRel2Root(((RootPaneContainer) parent).getContentPane());
            } else {
                hotspot = Util.computeVisibleRectRel2Root(parent);
            }
            hotspot.x += designer.getOuterLeft();
            hotspot.y += designer.getOuterTop();
            painter.setRenderingBounds(hotspot);
            painter.paint(g);
        }
    }
    private ArrayList<Component> painted_list = new ArrayList<Component>();
    /**
     * ������ǰѡ����ק״̬��
     */

    private void paintResizing(Graphics g, SwingDesigner designer) {
        ArrayList<Component> selection = selectionModel.getSelectedComponents();
        boolean resizable = selectionModel.isSelectedResizable();
        painted_list.clear();
        for (Component comp : selection) {
            if (!Util.isComponentVisible(comp) && !designer.isRootComponent(comp)) {
                continue;
            }
            if (Util.isDesigningContainer(designer, comp)) {
                if (!painted_list.contains(comp)) {
                    paintAnchorGrid(designer, comp, g);
                    painted_list.add(comp);
                }
            } else {
                Component parent = Util.findDesigningParent(comp);

                if (parent != null &&
                        Util.isDesigningContainer(designer, parent) &&
                        !painted_list.contains(parent)) {
                    paintAnchorGrid(designer, parent, g);
                    painted_list.add(parent);
                }
            }
            //������ѡ������
            Rectangle bounds = Util.computeVisibleRectRel2Root(comp);

            g.setColor(SELECTION_COLOR);

            int x = bounds.x + designer.getOuterLeft();
            int y = bounds.y + designer.getOuterTop();
            int w = bounds.width;
            int h = bounds.height;

            g.drawRect(x, y, w, h);

            if (resizable) {
                //�����ǰ��ѡ�е�����ǿ�����ק�ߴ�ģ��������ܱߵİ˸���ק��
                drawResizingThumbs(g, x, y, w, h);
            }
        }
    }

    /**
     * �����˸���ק��
     */
    private void drawResizingThumbs(Graphics g, int x, int y, int w, int h) {
        int bx = x - BOX_SIZE;
        int by = y - BOX_SIZE;

        drawBox(g, bx, by);
        bx = x + ((w - BOX_SIZE) / 2);
        drawBox(g, bx, by);
        bx = x + w;
        drawBox(g, bx, by);
        bx = x - BOX_SIZE;
        by = y + ((h - BOX_SIZE) / 2);
        drawBox(g, bx, by);
        by = y + h;
        drawBox(g, bx, by);
        bx = x + ((w - BOX_SIZE) / 2);
        drawBox(g, bx, by);
        bx = x + w;
        drawBox(g, bx, by);
        bx = x + w;
        by = y + ((h - BOX_SIZE) / 2);
        drawBox(g, bx, by);
    }

    /**
     * ��ÿһ��С��ק��
     */
    private void drawBox(Graphics g, int x, int y) {
        g.setColor(BOX_INNER_COLOR);
        g.fillRect(x, y, BOX_SIZE, BOX_SIZE);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(x, y, BOX_SIZE, BOX_SIZE);
    }
    /**
     * ����ǰ������Ƶ����
     */

    private void paintDesignedComponent(Graphics g, Component component) {
        if (designer.isInvalidated()) {
            repaintBuffer(component);
        }
        Graphics clipg = g.create(0, 0, designer.getWidth(), designer.getHeight());
        clipg.drawImage(designer.getDesigerBuffer(), 0, 0, designer);
        clipg.dispose();
    }
    private void repaintBuffer(Component component) {
        designer.setInvalidated(false);
        LookAndFeel system_lnf = designer.getIdeLnf();
        LookAndFeel design_lnf = designer.getDesignLnf();
        try {
            UIManager.setLookAndFeel(design_lnf);
            SwingUtilities.updateComponentTreeUI(component);
        } catch (Exception ex) {
        }
        TopContainerAdapter adapter = (TopContainerAdapter) AdapterBus.getComponentAdapter(designer, component);
        component = adapter.getContent();
        ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();
        //��ֹ˫����
        Util.disableBuffer(component, dbcomponents);

        Border border = designer.getOutlineBorder();
        Rectangle bounds = designer.getOutlineBounds();
        //�����������ı߿�
        Graphics offg = designer.getDesigerBuffer().getGraphics();
        offg.setColor(designer.getBackground());
        offg.fillRect(0, 0, designer.getWidth(), designer.getHeight());
        border.paintBorder(designer, offg, bounds.x, bounds.y, bounds.width, bounds.height);
        bounds = designer.getContentBounds();
        //������Ⱦ����
        Graphics clipg = offg.create(bounds.x, bounds.y, bounds.width, bounds.height);
        //ʹ����Ƹ������Ⱦ����paint����Render
        component.paint(clipg);
        clipg.dispose();
        //�ָ�˫����
        Util.resetBuffer(dbcomponents);
        try {
            UIManager.setLookAndFeel(system_lnf);
        } catch (Exception ex) {
        }
    }
}