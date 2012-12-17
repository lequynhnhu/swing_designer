/*
 * MouseSelectListener.java
 *
 * Created on July 31, 2007, 5:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import dyno.swing.beans.layouts.AbsoluteLayout;
import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.DesignerTransferHandler;
import dyno.swing.designer.beans.SpecialContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.location.Location;
import dyno.swing.designer.beans.models.SelectionModel;
import dyno.swing.designer.beans.models.StateModel;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;


/**
 * 普通模式下的鼠标点击、位置处理器
 * @author William Chen
 */
public class EditingMouseListener implements MouseInputListener, ChangeListener, Constants {

    private SwingDesigner designer;
    /**
     * 普通模式下对应的model
     */
    private StateModel stateModel;
    /**
     * 选择模型，存储当前选择的组件和剪切板
     */
    private SelectionModel selectionModel;
    private Component lastComponent;
    private MouseEvent lastPressEvent;
    private DesignerEditor current_editor;
    private Component current_bean;
    private ComponentAdapter current_adapter;

    /** Creates a new instance of MouseSelectListener */
    public EditingMouseListener(SwingDesigner designer) {
        this.designer = designer;
        stateModel = designer.getStateModel();
        selectionModel = designer.getSelectionModel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!stopEditing()) {
            return;
        }
        if (!designer.isFocusOwner()) {
            //获取焦点，以便获取热键
            designer.requestFocus();
        }

        if (e.isPopupTrigger()) {
            //为触发上下文菜单预留
            trigger_popup(e);
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            //获取鼠标所在被选中的组件
            Component comp = selectionModel.getSelectionAt(e);
            Component hotspot = designer.getComponentAt(e);
            if (selectionModel.isSelectedResizable() && 
                    (comp != null) && 
                    (hotspot == comp || 
                    isParent(comp, hotspot))) {
                //如果在鼠标有被选择的组件，并且组件是处理可被拖拽状态，则更新当前鼠标的位置信息以及鼠标形状
                stateModel.updateLocRel2Comp(e, comp);
                designer.updateCursor();

                if (stateModel.getLocation() != Location.outer) {
                    //如果当前是处于拖拽位置，则开始拖拽，记录初始拖拽状态
                    stateModel.startSelectionResizing(e);
                }
            } else {
                //如果是其他情况，计算是否在拖拽根组件的位置
                stateModel.updateLocRel2Root(e);

                if (stateModel.getLocation() == Location.outer) {
                    //如果不是处于拖拽位置，则先将该组件添加到被选择的组件中
                    selectionModel.selectAComponentAtMouseEvent(e);
                    designer.repaint();
                    //并获取当前鼠标所在的组件
                    comp = designer.getComponentAt(e);

                    if (comp != null) {
                        //获取组件的适配器
                        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);

                        //调用其适配器处理鼠标点击，在此处可以处理JTabbedPane页面切换的特殊情况
                        if (adapter.componentClicked(e)) {
                            //如果componentClicked处理结果需要继续后续鼠标处理
                            if (adapter instanceof ContainerAdapter && ((((ContainerAdapter) adapter).getChildCount() > 0) || (comp.getParent() == null)) && !(adapter instanceof SpecialContainerAdapter)) {
                                //当前组件如果是容器，则开始进入区域拖拽选择模式
                                stateModel.startSelecting(e);
                                //设置当前选择的热点区域
                                selectionModel.setHotspotBounds(new Rectangle());
                            } else if (isComponentResizable(comp)) {
                                //如果鼠标所在的组件是可以拖拽的，则继续该鼠标的进一步处理
                                mousePressed(e);
                            } else if (comp.getParent() != null) {
                                lastPressEvent = e;
                                lastComponent = comp;
                            }
                        } else {
                            designer.setInvalidated(true);
                        }
                    }
                } else {
                    //如果是拖拽根组件状态，则更新鼠标形状
                    designer.updateCursor();
                    //开始resize根组件
                    stateModel.startResizing(e);
                }
            }
        }
    }

    //comp是否是可以拖拽
    private boolean isComponentResizable(Component comp) {
        Container parent = comp.getParent();

        if (parent != null) {
            //如果其父容器组件的布局管理器为空或者是AbsoluteLayout则可以拖拽
            LayoutManager layout = parent.getLayout();

            if ((layout == null) || layout instanceof AbsoluteLayout) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            trigger_popup(e);
        } else {
            if (stateModel.isSelecting()) {
                //如果当前是区域选择状态，则选择该区域所含的组件
                designer.selectComponents(e);
            } else if (stateModel.isDragging()) {
                //如果是处于拖拽状态，则释放组件
                designer.releaseComponentsDragging(e);
            } else {
            }
        }

        lastPressEvent = null;
        lastComponent = null;
    }

    private boolean isParent(Component child, Component parent) {
        if (parent == child.getParent()) {
            return true;
        }
        if(parent instanceof RootPaneContainer){
            RootPaneContainer rootPane=(RootPaneContainer)parent;
            return rootPane.getContentPane() == child.getParent();
        }
        return false;
    }

    /**
     * 激活上下文菜单，待完善
     */
    private void trigger_popup(MouseEvent e) {
        ArrayList<Component> selection = selectionModel.getSelectedComponents();

        if ((selection == null) || selection.isEmpty()) {
            return;
        }

        JPopupMenu popupMenu = null;

        if (selection.size() > 0) {
            Component component = selection.get(0);
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, component);
            popupMenu = adapter.getContextPopupMenu(e);
        } else {
            return;
        }

        if (popupMenu != null) {
            popupMenu.show(designer, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //鼠标移动，获取鼠标所在被选择的组件
        Component comp = selectionModel.getSelectionAt(e);

        if (selectionModel.isSelectedResizable() && (comp != null)) {
            //如果当前所在的被选择组件是可以拖拽的，更新其位置信息
            stateModel.updateLocRel2Comp(e, comp);
        } else {
            //更新其相对于根组件的位置信息
            stateModel.updateLocRel2Root(e);
        }
        //更新鼠标形状
        designer.updateCursor();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (stateModel.isDragging()) {
            //如果当前是拖拽状态，拖拽组件
            stateModel.dragging(e);
        } else if (stateModel.isSelecting() && (selectionModel.getHotspotBounds() != null)) {
            //如果是拖拽选择区域状态，则更新选择区域
            stateModel.changeSelection(e);
        } else {
            if ((lastPressEvent == null) || (lastComponent == null)) {
                return;
            }

            if (e.getPoint().distance(lastPressEvent.getPoint()) > 5) {
                TransferHandler handler = new DesignerTransferHandler();
                designer.setTransferHandler(handler);
                handler.exportAsDrag(designer, lastPressEvent, TransferHandler.COPY);
                designer.startDraggingComponent(lastComponent, e.getX(), e.getY());
                e.consume();
                lastPressEvent = null;
            }
        }

        designer.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() <= 1) {
            return;
        }
        if (stopEditing()) {
            Component bean = designer.getComponentAt(e);
            if (bean != null) {
                int x = e.getX();
                int y = e.getY();
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, bean);
                x -= designer.getOuterLeft();
                y -= designer.getOuterTop();

                Rectangle rect = Util.getRelativeBounds(bean);
                x -= rect.x;
                y -= rect.y;

                DesignerEditor editor = adapter.getDesignerEditor(x, y);

                if (editor != null) {
                    current_editor = editor;
                    current_bean = bean;
                    current_adapter = adapter;
                    current_editor.addChangeListener(this);
                    Rectangle bounds = current_adapter.getEditorBounds(x, y);
                    bounds.x += (rect.x + designer.getOuterLeft());
                    bounds.y += (rect.y + designer.getOuterTop());
                    Component editorComponent = current_editor.getEditorComponent();
                    editorComponent.setBounds(bounds);
                    if (editorComponent instanceof Container) {
                        Util.layoutContainer((Container) editorComponent);
                    }
                    designer.add(editorComponent);
                    designer.invalidate();
                    Object v = current_adapter.getBeanValue();
                    current_editor.setValue(v);
                    editorComponent.requestFocus();
                    designer.repaint();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private boolean stopEditing() {
        if (current_editor != null) {
            try {
                Object value = current_editor.getValue();
                current_adapter.validateBeanValue(value);
                Object old_value = current_adapter.getBeanValue();
                if (Util.isChanged(old_value, value)) {
                    current_adapter.setBeanValue(value);
                    DesignerEvent evt = new DesignerEvent(designer);
                    evt.setAffectedComponent(current_bean);
                    designer.getEditListenerTable().fireComponentEdited(evt);
                }
                designer.remove(current_editor.getEditorComponent());

                Container container = current_bean.getParent();

                if (container != null) {
                    Util.layoutContainer(container);
                }

                designer.invalidate();
                designer.repaint();
                current_editor.removeChangeListener(this);

                current_adapter = null;
                current_bean = null;
                current_editor = null;
                return true;
            } catch (ValidationException e) {
                JOptionPane.showMessageDialog(designer, e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        return true;
    }

    public void stateChanged(ChangeEvent e) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                stopEditing();
            }
        });
    }
}