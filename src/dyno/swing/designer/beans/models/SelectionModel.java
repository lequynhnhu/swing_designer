/*
 * SelectionModel.java
 *
 * Created on 2007年5月7日, 下午9:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.models;

import dyno.swing.beans.layouts.AbsoluteLayout;
import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;


/**
 * 该model保存当前选择的组件和剪切版信息
 * @author William Chen
 */
public class SelectionModel implements Constants {

    private SwingDesigner designer;
    //当前选中的组件，可以是多选，被选中的组件之间可以存在父子树状关系
    private transient ArrayList<Component> selection;
    //当前被拷贝或粘帖的剪切板
    private transient ArrayList<Component> clip_board;
    //当前拖拽和选择的热点区域
    private transient Rectangle hotspot_bounds;

    public SelectionModel(SwingDesigner designer) {
        this.designer = designer;
        selection = new ArrayList<Component>();
        clip_board = new ArrayList<Component>();
    }
    public void reset(){
        selection.clear();
        clip_board.clear();
        hotspot_bounds=null;
    }
    /**
     * 当前选择的组件是否是可以拖拽的。可以拖拽的条件为：
     * 1.选中的组件不为空（很明显）
     * 2.选中的组件不是根组件
     * 3.选中的组件的父亲容器必须是同一个容器（不同容器内的组件不可以被同时拖拽）
     * 4.选中的组件的父亲容器的布局管理器为空，或者是AbsoluteLayout。这个规则以后可能发生，
     * 如果想在其他布局管理器的情况下支持特殊的拖拽。待定。
     *
     * @return 是否可以拖拽，是为true，否则false
     */
    public boolean isSelectedResizable() {
        if (selection.isEmpty()) {
            //1.选中的组件不为空
            return false;
        }

        Container current = null;
        for (Component comp : selection) {
            Container parent = comp.getParent();
            if ((parent == null) || (comp == designer.getRootComponent())) {
                //2.选中的组件不是根组件
                return false;
            }

            LayoutManager layout = parent.getLayout();

            if ((layout != null) && (layout.getClass() != AbsoluteLayout.class)) {
                //4.选中的组件的父亲容器的布局管理器为空，或者是AbsoluteLayout
                return false;
            }

            if (current == null) {
                //第一个组件的父容器
                current = parent;
            } else if (current != parent) {
                //3.选中的组件的父亲容器必须是同一个容器
                return false;
            }
        }

        return true;
    }

    public void setSelectedComponent(Component comp) {
        selection.clear();
        selection.add(comp);
        fireComponentSelected();
    }

    /**
     * 鼠标点击一下，所选中的单个组件。按下Ctrl或者shift键时鼠标可以进行多选
     */
    public void selectAComponentAtMouseEvent(MouseEvent e) {
        if (!e.isControlDown() && !e.isShiftDown()) {
            //如果Ctrl或者Shift键盘没有按下，则清除已经选择的组件
            selection.clear();
        }

        //获取e所在的组件
        Component comp = designer.getComponentAt(e);

        if (!((comp == null) || selection.contains(comp))) {
            //如果e所在的地方有组件，并且已经选中的组件中不包含此组件，则添加进去
            selection.add(comp);
        }

        //触发鼠标组件选择事件。
        fireComponentSelected();
        designer.repaint();
    }

    private void fireComponentSelected() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setSelectedComponents(selection);
        designer.getEditListenerTable().fireComponentSelected(evt);
    }

    /**
     * 在当前选中的组件中找出处于e鼠标事件所在的组件。
     */
    public Component getSelectionAt(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();

        for (Component comp : selection) {
            //获取当前组件相对于根组件的相对位置
            Rectangle rect = Util.getRelativeBounds(comp);

            if ((x >= (rect.x - BOX_SIZE)) && (x <= (rect.x + rect.width + BOX_SIZE)) && (y >= (rect.y - BOX_SIZE)) && (y <= (rect.y + rect.height + BOX_SIZE))) {
                return comp;
            }
        }

        return null;
    }

    /**
     * 将所选组件剪切到剪切板上
     */
    public void cutSelectedComponent2ClipBoard() {
        if (!selection.isEmpty()) {
            //清空剪切板
            clip_board.clear();

            //找到所选择组件的根
            ArrayList<Component> roots = getSelectedRoots();

            for (Component root : roots) {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, root);
                //添加他们的根组件
                clip_board.add(adapter.clone());
            }

            for (Component component : roots) {
                removeBeanFromContainer(component);
            }

            //触发事件
            fireComponentCut();
            //清除被选择的组件
            selection.clear();
            designer.repaint();
        }
    }

    private void fireComponentCut() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setCutComponents(clip_board);
        designer.getEditListenerTable().fireComponentCut(evt);
    }

    /**
     * 获取当前被选中组件的根列表
     */
    public ArrayList<Component> getSelectedRoots() {
        ArrayList<Component> roots = new ArrayList<Component>();
        for (Component current : selection) {
            //如果当前不为根组件，并且当前组件在selection是根组件，则添加该组件
            if ((current != designer.getRootComponent()) && isTopContainer(current, selection)) {
                roots.add(current);
            }
        }

        return roots;
    }

    /**
     *判断comonent1在组件列表selection是否是根组件，即顶层容器组件
     */
    private boolean isTopContainer(Component component1, ArrayList<Component> selection) {
        for (Component component2 : selection) {
            //查找每个组件
            if ((component1 != component2) && SwingUtilities.isDescendingFrom(component1, component2) && (component2 != designer.getRootComponent())) {
                //如果不是组件自身，切component1是component2的子或孙组件，并且component2不是根组件，那么不是顶层容器
                return false;
            }
        }

        return true;
    }

    /**
     * 复制当前选中的组件到剪切板
     */
    public void copySelectedComponent2ClipBoard() {
        if (!selection.isEmpty()) {
            //清空剪切板
            clip_board.clear();

            //获取被选中组件的根组件
            ArrayList<Component> roots = getSelectedRoots();

            for (Component root : roots) {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, root);
                //放到剪切板中
                clip_board.add(adapter.clone());
            }
            //触发事件
            fireComponentCopyed();
        }
    }

    private void fireComponentCopyed() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setCopyedComponents(clip_board);
        designer.getEditListenerTable().fireComponentCopyed(evt);
    }

    /**
     * 从剪切板粘帖组件
     */
    public void pasteFromClipBoard() {
        if (!clip_board.isEmpty()) {
            if (!isPasteable()) {
                //如果当前被选择的组件不是容器或者剪切板的内容不可以粘帖
                Toolkit.getDefaultToolkit().beep();
            } else {
                //唯一的容器组件就是粘帖组件的地方
                Component root = selection.get(0);
                Container container = (Container) root;

                for (Component comp : clip_board) {
                    //设置位置，移动20x20，防止被粘帖的组件重叠，照顾空布局情况下
                    comp.setLocation(comp.getX() + 20, comp.getY() + 20);

                    //使用适配器克隆组件
                    ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);
                    Component clone = adapter.clone();
                    clone.setName(clone.getName() + "_copy");
                    //在容器中添加该组件
                    container.add(clone);
                }
                clip_board.clear();
                //重新布局
                Util.layoutContainer(container);
                //触发事件
                fireComponentPaste();
                designer.repaint();
            }
        } else {
            //不可以粘帖，警告
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void fireComponentPaste() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setPastedComponents(clip_board);
        designer.getEditListenerTable().fireComponentPasted(evt);
    }

    /**
     * 当前所选中的组件容器是否可以粘帖剪贴版内的组件
     */
    public boolean isPasteable() {
        if (selection.size() != 1) {
            //必须只有一个容器组件
            return false;
        }

        Component comp = selection.get(0);

        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);

        //并且该唯一的组件是个容器
        return adapter instanceof ContainerAdapter;
    }

    //向左对齐
    public void adjustLeftAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int x = comp.getX();

            for (Component current : selection) {
                current.setLocation(x, current.getY());
            }
        }
    }

    //向右对齐
    public void adjustRightAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int right_x = comp.getX() + comp.getWidth();

            for (Component current : selection) {
                current.setLocation(right_x - current.getWidth(), current.getY());
            }
        }
    }

    //向中对齐
    public void adjustCenterAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int center_x = comp.getX() + (comp.getWidth() / 2);

            for (Component current : selection) {
                current.setLocation(center_x - (current.getWidth() / 2), current.getY());
            }
        }
    }

    //向上对齐
    public void adjustTopAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int y = comp.getY();

            for (Component current : selection) {
                current.setLocation(current.getX(), y);
            }
        }
    }

    //向下对齐
    public void adjustBottomAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int bottom_y = comp.getY() + comp.getHeight();

            for (Component current : selection) {
                current.setLocation(current.getX(), bottom_y - current.getHeight());
            }
        }
    }

    //向中对齐
    public void adjustMiddleAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int middle_y = comp.getY() + (comp.getHeight() / 2);

            for (Component current : selection) {
                current.setLocation(current.getX(), middle_y - (current.getHeight() / 2));
            }
        }
    }

    //同一宽度
    public void adjustSameWidth() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int width = comp.getWidth();

            for (Component current : selection) {
                current.setSize(width, current.getHeight());
            }
        }
    }

    //同一高度
    public void adjustSameHeight() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int height = comp.getHeight();

            for (Component current : selection) {
                current.setSize(current.getWidth(), height);
            }
        }
    }

    public void clearSelection() {
        selection.clear();
    }

    public void clearClipBoard() {
        clip_board.clear();
    }

    public void addComp2ClipBoard(Component comp) {
        clip_board.add(comp);
    }

    public void removeCompFromClipBoard(Component comp) {
        clip_board.remove(comp);
    }

    public void addComp2ClipBoard(Collection<? extends Component> comps) {
        clip_board.addAll(comps);
    }

    public boolean hasSelection() {
        return !selection.isEmpty();
    }

    public boolean isRootSelected() {
        return hasSelection() && selection.contains(designer.getRootComponent());
    }

    public boolean isClipBoardEmpty() {
        return clip_board.isEmpty();
    }

    public boolean isBoundsAdjustable() {
        return isSelectedResizable() && (selection.size() > 1);
    }

    /**
     * 删除当前所有选择的组件
     */
    public void deleteSelection() {
        //找出所有选中组件的根部
        ArrayList<Component> roots = getSelectedRoots();

        if (!roots.isEmpty()) {
            for (Component component : roots) {
                removeBeanFromContainer(component);
            }
            //触发事件
            fireComponentDeleted();
            //清除被选中的组件
            clearSelection();
            designer.repaint();
        }
    }

    private void fireComponentDeleted() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setDeletedComponents(selection);
        designer.getEditListenerTable().fireComponentDeleted(evt);
    }

    public void removeComponent(Component component) {
        if (selection.contains(component)) {
            selection.remove(component);
        }

        //从顶层容器中清除这些根组件
        removeBeanFromContainer(component);
        designer.repaint();
    }

    public void setHotspotBounds(Rectangle rect) {
        hotspot_bounds = rect;
    }

    public Rectangle getHotspotBounds() {
        return hotspot_bounds;
    }

    public void setSelectedComponents(Collection<Component> components) {
        selection.clear();
        selection.addAll(components);
        fireComponentSelected();
    }

    public ArrayList<Component> getSelectedComponents() {
        return selection;
    }

    public ArrayList<Component> getClipBoard() {
        return clip_board;
    }

    private void removeBeanFromContainer(Component component) {
        Container parent = component.getParent();
        if(parent instanceof JViewport){
            parent=parent.getParent();
        }
        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, parent);
        if (adapter instanceof ContainerAdapter) {
            ((ContainerAdapter)adapter).removeComponent(component);
        } else {
            //删除其根组件，同时就删除了同时被选择的叶子组件
            parent.remove(component);
        }
        LayoutManager layout = parent.getLayout();

        if (layout != null) {
            //刷新组件容器的布局
            Util.layoutContainer(parent);
        }
    }
}