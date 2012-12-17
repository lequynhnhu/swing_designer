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
 * SwingDesigner的UI类，是一个有状态的UI类，它根据SwingDesigner的当前状态画出
 * 具有所见即所得的设计界面，以及当前设计界面的一些辅助状态，比如选择标识、拖动区域
 * 以及当前正在添加的组件
 *
 * @author William Chen
 */
public class DesignerUI extends ComponentUI implements Constants {

    //当前的设计器

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
     * 渲染当前的设计界面以及设计辅助状态
     */

    @Override
    public void paint(Graphics g, JComponent c) {
        Component component = designer.getRootComponent();
        if (component != null) {
            //设计界面
            paintDesignedComponent(g, component);
        }

        ArrayList<Component> selection = selectionModel.getSelectedComponents();

        if (!selection.isEmpty()) {
            //选择及拖拽
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
            //当前区域选择框
            g.setColor(SELECTION_COLOR);
            g.drawRect(hotspot_bounds.x + designer.getOuterLeft(), hotspot_bounds.y + designer.getOuterTop(), hotspot_bounds.width, hotspot_bounds.height);
        }

        if (designer.getPainter() != null) {
            //ComponentAdapter和LayoutAdapter提供的额外的Painter，该Painter一般用于提示作用，
            //相当于一个浮动层
            designer.getPainter().paint(g);
        }

        AddingModel addingModel = designer.getAddingModel();

        if ((addingModel != null) && (addingModel.getBean() != null)) {
            //当前正在添加的组件
            paintAddingBean(g, addingModel);
        }
    }

    /**
     * 渲染当前正在添加的组件，采用Renderer原理
     */
    private void paintAddingBean(Graphics g, final AddingModel addingModel) {
        Component bean = addingModel.getBean();
        int x = addingModel.getCurrentX();
        int y = addingModel.getCurrentY();
        int width = bean.getWidth();
        int height = bean.getHeight();
        Graphics clipg = g.create(x, y, width, height);
        ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();
        //禁止双缓冲行为
        Util.disableBuffer(bean, dbcomponents);

        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, bean);
        //调用ComponentAdapter的paintComponentMascot方法渲染该组件添加提示
        adapter.paintComponentMascot(clipg);
        clipg.dispose();
        //恢复双缓冲
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
     * 画出当前选择、拖拽状态框
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
            //遍历被选择的组件
            Rectangle bounds = Util.computeVisibleRectRel2Root(comp);

            g.setColor(SELECTION_COLOR);

            int x = bounds.x + designer.getOuterLeft();
            int y = bounds.y + designer.getOuterTop();
            int w = bounds.width;
            int h = bounds.height;

            g.drawRect(x, y, w, h);

            if (resizable) {
                //如果当前被选中的组件是可以拖拽尺寸的，画出其周边的八个拖拽框
                drawResizingThumbs(g, x, y, w, h);
            }
        }
    }

    /**
     * 画出八个拖拽框
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
     * 画每一个小拖拽框
     */
    private void drawBox(Graphics g, int x, int y) {
        g.setColor(BOX_INNER_COLOR);
        g.fillRect(x, y, BOX_SIZE, BOX_SIZE);
        g.setColor(BOX_BORDER_COLOR);
        g.drawRect(x, y, BOX_SIZE, BOX_SIZE);
    }
    /**
     * 画当前正在设计的组件
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
        //禁止双缓冲
        Util.disableBuffer(component, dbcomponents);

        Border border = designer.getOutlineBorder();
        Rectangle bounds = designer.getOutlineBounds();
        //画出设计区域的边框
        Graphics offg = designer.getDesigerBuffer().getGraphics();
        offg.setColor(designer.getBackground());
        offg.fillRect(0, 0, designer.getWidth(), designer.getHeight());
        border.paintBorder(designer, offg, bounds.x, bounds.y, bounds.width, bounds.height);
        bounds = designer.getContentBounds();
        //剪切渲染区域
        Graphics clipg = offg.create(bounds.x, bounds.y, bounds.width, bounds.height);
        //使用设计根组件渲染方法paint进行Render
        component.paint(clipg);
        clipg.dispose();
        //恢复双缓冲
        Util.resetBuffer(dbcomponents);
        try {
            UIManager.setLookAndFeel(system_lnf);
        } catch (Exception ex) {
        }
    }
}