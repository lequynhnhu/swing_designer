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
 * ��ͨģʽ�µ��������λ�ô�����
 * @author William Chen
 */
public class EditingMouseListener implements MouseInputListener, ChangeListener, Constants {

    private SwingDesigner designer;
    /**
     * ��ͨģʽ�¶�Ӧ��model
     */
    private StateModel stateModel;
    /**
     * ѡ��ģ�ͣ��洢��ǰѡ�������ͼ��а�
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
            //��ȡ���㣬�Ա��ȡ�ȼ�
            designer.requestFocus();
        }

        if (e.isPopupTrigger()) {
            //Ϊ���������Ĳ˵�Ԥ��
            trigger_popup(e);
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            //��ȡ������ڱ�ѡ�е����
            Component comp = selectionModel.getSelectionAt(e);
            Component hotspot = designer.getComponentAt(e);
            if (selectionModel.isSelectedResizable() && 
                    (comp != null) && 
                    (hotspot == comp || 
                    isParent(comp, hotspot))) {
                //���������б�ѡ����������������Ǵ���ɱ���ק״̬������µ�ǰ����λ����Ϣ�Լ������״
                stateModel.updateLocRel2Comp(e, comp);
                designer.updateCursor();

                if (stateModel.getLocation() != Location.outer) {
                    //�����ǰ�Ǵ�����קλ�ã���ʼ��ק����¼��ʼ��ק״̬
                    stateModel.startSelectionResizing(e);
                }
            } else {
                //�������������������Ƿ�����ק�������λ��
                stateModel.updateLocRel2Root(e);

                if (stateModel.getLocation() == Location.outer) {
                    //������Ǵ�����קλ�ã����Ƚ��������ӵ���ѡ��������
                    selectionModel.selectAComponentAtMouseEvent(e);
                    designer.repaint();
                    //����ȡ��ǰ������ڵ����
                    comp = designer.getComponentAt(e);

                    if (comp != null) {
                        //��ȡ�����������
                        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);

                        //������������������������ڴ˴����Դ���JTabbedPaneҳ���л����������
                        if (adapter.componentClicked(e)) {
                            //���componentClicked��������Ҫ����������괦��
                            if (adapter instanceof ContainerAdapter && ((((ContainerAdapter) adapter).getChildCount() > 0) || (comp.getParent() == null)) && !(adapter instanceof SpecialContainerAdapter)) {
                                //��ǰ����������������ʼ����������קѡ��ģʽ
                                stateModel.startSelecting(e);
                                //���õ�ǰѡ����ȵ�����
                                selectionModel.setHotspotBounds(new Rectangle());
                            } else if (isComponentResizable(comp)) {
                                //���������ڵ�����ǿ�����ק�ģ�����������Ľ�һ������
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
                    //�������ק�����״̬������������״
                    designer.updateCursor();
                    //��ʼresize�����
                    stateModel.startResizing(e);
                }
            }
        }
    }

    //comp�Ƿ��ǿ�����ק
    private boolean isComponentResizable(Component comp) {
        Container parent = comp.getParent();

        if (parent != null) {
            //����丸��������Ĳ��ֹ�����Ϊ�ջ�����AbsoluteLayout�������ק
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
                //�����ǰ������ѡ��״̬����ѡ����������������
                designer.selectComponents(e);
            } else if (stateModel.isDragging()) {
                //����Ǵ�����ק״̬�����ͷ����
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
     * ���������Ĳ˵���������
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
        //����ƶ�����ȡ������ڱ�ѡ������
        Component comp = selectionModel.getSelectionAt(e);

        if (selectionModel.isSelectedResizable() && (comp != null)) {
            //�����ǰ���ڵı�ѡ������ǿ�����ק�ģ�������λ����Ϣ
            stateModel.updateLocRel2Comp(e, comp);
        } else {
            //����������ڸ������λ����Ϣ
            stateModel.updateLocRel2Root(e);
        }
        //���������״
        designer.updateCursor();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (stateModel.isDragging()) {
            //�����ǰ����ק״̬����ק���
            stateModel.dragging(e);
        } else if (stateModel.isSelecting() && (selectionModel.getHotspotBounds() != null)) {
            //�������קѡ������״̬�������ѡ������
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