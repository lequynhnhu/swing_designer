/*
 * SelectionModel.java
 *
 * Created on 2007��5��7��, ����9:58
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
 * ��model���浱ǰѡ�������ͼ��а���Ϣ
 * @author William Chen
 */
public class SelectionModel implements Constants {

    private SwingDesigner designer;
    //��ǰѡ�е�����������Ƕ�ѡ����ѡ�е����֮����Դ��ڸ�����״��ϵ
    private transient ArrayList<Component> selection;
    //��ǰ��������ճ���ļ��а�
    private transient ArrayList<Component> clip_board;
    //��ǰ��ק��ѡ����ȵ�����
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
     * ��ǰѡ�������Ƿ��ǿ�����ק�ġ�������ק������Ϊ��
     * 1.ѡ�е������Ϊ�գ������ԣ�
     * 2.ѡ�е�������Ǹ����
     * 3.ѡ�е�����ĸ�������������ͬһ����������ͬ�����ڵ���������Ա�ͬʱ��ק��
     * 4.ѡ�е�����ĸ��������Ĳ��ֹ�����Ϊ�գ�������AbsoluteLayout����������Ժ���ܷ�����
     * ��������������ֹ������������֧���������ק��������
     *
     * @return �Ƿ������ק����Ϊtrue������false
     */
    public boolean isSelectedResizable() {
        if (selection.isEmpty()) {
            //1.ѡ�е������Ϊ��
            return false;
        }

        Container current = null;
        for (Component comp : selection) {
            Container parent = comp.getParent();
            if ((parent == null) || (comp == designer.getRootComponent())) {
                //2.ѡ�е�������Ǹ����
                return false;
            }

            LayoutManager layout = parent.getLayout();

            if ((layout != null) && (layout.getClass() != AbsoluteLayout.class)) {
                //4.ѡ�е�����ĸ��������Ĳ��ֹ�����Ϊ�գ�������AbsoluteLayout
                return false;
            }

            if (current == null) {
                //��һ������ĸ�����
                current = parent;
            } else if (current != parent) {
                //3.ѡ�е�����ĸ�������������ͬһ������
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
     * �����һ�£���ѡ�еĵ������������Ctrl����shift��ʱ�����Խ��ж�ѡ
     */
    public void selectAComponentAtMouseEvent(MouseEvent e) {
        if (!e.isControlDown() && !e.isShiftDown()) {
            //���Ctrl����Shift����û�а��£�������Ѿ�ѡ������
            selection.clear();
        }

        //��ȡe���ڵ����
        Component comp = designer.getComponentAt(e);

        if (!((comp == null) || selection.contains(comp))) {
            //���e���ڵĵط�������������Ѿ�ѡ�е�����в����������������ӽ�ȥ
            selection.add(comp);
        }

        //����������ѡ���¼���
        fireComponentSelected();
        designer.repaint();
    }

    private void fireComponentSelected() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setSelectedComponents(selection);
        designer.getEditListenerTable().fireComponentSelected(evt);
    }

    /**
     * �ڵ�ǰѡ�е�������ҳ�����e����¼����ڵ������
     */
    public Component getSelectionAt(MouseEvent e) {
        int x = e.getX() - designer.getOuterLeft();
        int y = e.getY() - designer.getOuterTop();

        for (Component comp : selection) {
            //��ȡ��ǰ�������ڸ���������λ��
            Rectangle rect = Util.getRelativeBounds(comp);

            if ((x >= (rect.x - BOX_SIZE)) && (x <= (rect.x + rect.width + BOX_SIZE)) && (y >= (rect.y - BOX_SIZE)) && (y <= (rect.y + rect.height + BOX_SIZE))) {
                return comp;
            }
        }

        return null;
    }

    /**
     * ����ѡ������е����а���
     */
    public void cutSelectedComponent2ClipBoard() {
        if (!selection.isEmpty()) {
            //��ռ��а�
            clip_board.clear();

            //�ҵ���ѡ������ĸ�
            ArrayList<Component> roots = getSelectedRoots();

            for (Component root : roots) {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, root);
                //������ǵĸ����
                clip_board.add(adapter.clone());
            }

            for (Component component : roots) {
                removeBeanFromContainer(component);
            }

            //�����¼�
            fireComponentCut();
            //�����ѡ������
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
     * ��ȡ��ǰ��ѡ������ĸ��б�
     */
    public ArrayList<Component> getSelectedRoots() {
        ArrayList<Component> roots = new ArrayList<Component>();
        for (Component current : selection) {
            //�����ǰ��Ϊ����������ҵ�ǰ�����selection�Ǹ����������Ӹ����
            if ((current != designer.getRootComponent()) && isTopContainer(current, selection)) {
                roots.add(current);
            }
        }

        return roots;
    }

    /**
     *�ж�comonent1������б�selection�Ƿ��Ǹ�������������������
     */
    private boolean isTopContainer(Component component1, ArrayList<Component> selection) {
        for (Component component2 : selection) {
            //����ÿ�����
            if ((component1 != component2) && SwingUtilities.isDescendingFrom(component1, component2) && (component2 != designer.getRootComponent())) {
                //����������������component1��component2���ӻ������������component2���Ǹ��������ô���Ƕ�������
                return false;
            }
        }

        return true;
    }

    /**
     * ���Ƶ�ǰѡ�е���������а�
     */
    public void copySelectedComponent2ClipBoard() {
        if (!selection.isEmpty()) {
            //��ռ��а�
            clip_board.clear();

            //��ȡ��ѡ������ĸ����
            ArrayList<Component> roots = getSelectedRoots();

            for (Component root : roots) {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, root);
                //�ŵ����а���
                clip_board.add(adapter.clone());
            }
            //�����¼�
            fireComponentCopyed();
        }
    }

    private void fireComponentCopyed() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setCopyedComponents(clip_board);
        designer.getEditListenerTable().fireComponentCopyed(evt);
    }

    /**
     * �Ӽ��а�ճ�����
     */
    public void pasteFromClipBoard() {
        if (!clip_board.isEmpty()) {
            if (!isPasteable()) {
                //�����ǰ��ѡ�����������������߼��а�����ݲ�����ճ��
                Toolkit.getDefaultToolkit().beep();
            } else {
                //Ψһ�������������ճ������ĵط�
                Component root = selection.get(0);
                Container container = (Container) root;

                for (Component comp : clip_board) {
                    //����λ�ã��ƶ�20x20����ֹ��ճ��������ص����չ˿ղ��������
                    comp.setLocation(comp.getX() + 20, comp.getY() + 20);

                    //ʹ����������¡���
                    ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);
                    Component clone = adapter.clone();
                    clone.setName(clone.getName() + "_copy");
                    //����������Ӹ����
                    container.add(clone);
                }
                clip_board.clear();
                //���²���
                Util.layoutContainer(container);
                //�����¼�
                fireComponentPaste();
                designer.repaint();
            }
        } else {
            //������ճ��������
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void fireComponentPaste() {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setPastedComponents(clip_board);
        designer.getEditListenerTable().fireComponentPasted(evt);
    }

    /**
     * ��ǰ��ѡ�е���������Ƿ����ճ���������ڵ����
     */
    public boolean isPasteable() {
        if (selection.size() != 1) {
            //����ֻ��һ���������
            return false;
        }

        Component comp = selection.get(0);

        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);

        //���Ҹ�Ψһ������Ǹ�����
        return adapter instanceof ContainerAdapter;
    }

    //�������
    public void adjustLeftAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int x = comp.getX();

            for (Component current : selection) {
                current.setLocation(x, current.getY());
            }
        }
    }

    //���Ҷ���
    public void adjustRightAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int right_x = comp.getX() + comp.getWidth();

            for (Component current : selection) {
                current.setLocation(right_x - current.getWidth(), current.getY());
            }
        }
    }

    //���ж���
    public void adjustCenterAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int center_x = comp.getX() + (comp.getWidth() / 2);

            for (Component current : selection) {
                current.setLocation(center_x - (current.getWidth() / 2), current.getY());
            }
        }
    }

    //���϶���
    public void adjustTopAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int y = comp.getY();

            for (Component current : selection) {
                current.setLocation(current.getX(), y);
            }
        }
    }

    //���¶���
    public void adjustBottomAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int bottom_y = comp.getY() + comp.getHeight();

            for (Component current : selection) {
                current.setLocation(current.getX(), bottom_y - current.getHeight());
            }
        }
    }

    //���ж���
    public void adjustMiddleAlignment() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int middle_y = comp.getY() + (comp.getHeight() / 2);

            for (Component current : selection) {
                current.setLocation(current.getX(), middle_y - (current.getHeight() / 2));
            }
        }
    }

    //ͬһ���
    public void adjustSameWidth() {
        if (isBoundsAdjustable()) {
            Component comp = selection.get(0);
            int width = comp.getWidth();

            for (Component current : selection) {
                current.setSize(width, current.getHeight());
            }
        }
    }

    //ͬһ�߶�
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
     * ɾ����ǰ����ѡ������
     */
    public void deleteSelection() {
        //�ҳ�����ѡ������ĸ���
        ArrayList<Component> roots = getSelectedRoots();

        if (!roots.isEmpty()) {
            for (Component component : roots) {
                removeBeanFromContainer(component);
            }
            //�����¼�
            fireComponentDeleted();
            //�����ѡ�е����
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

        //�Ӷ��������������Щ�����
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
            //ɾ����������ͬʱ��ɾ����ͬʱ��ѡ���Ҷ�����
            parent.remove(component);
        }
        LayoutManager layout = parent.getLayout();

        if (layout != null) {
            //ˢ����������Ĳ���
            Util.layoutContainer(parent);
        }
    }
}