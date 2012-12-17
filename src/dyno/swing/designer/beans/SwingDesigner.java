/*
 * BeanDesigner.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import dyno.swing.designer.beans.actions.ActionCategory;
import dyno.swing.designer.beans.actions.AlignmentAction;
import dyno.swing.designer.beans.actions.EditingAction;
import dyno.swing.designer.beans.actions.SameSizeAction;
import dyno.swing.designer.beans.actions.ViewAction;
import dyno.swing.designer.beans.events.AddingMouseListener;
import dyno.swing.designer.beans.events.DesignerEditAdapter;
import dyno.swing.designer.beans.events.DesignerEditListener;
import dyno.swing.designer.beans.events.DesignerEvent;
import dyno.swing.designer.beans.events.DesignerStateListener;
import dyno.swing.designer.beans.events.EditingMouseListener;
import dyno.swing.designer.beans.events.HotKeyProxy;
import dyno.swing.designer.beans.location.Location;
import dyno.swing.designer.beans.models.AddingModel;
import dyno.swing.designer.beans.models.SelectionModel;
import dyno.swing.designer.beans.models.StateModel;
import dyno.swing.designer.beans.toolkit.BeanInfoToggleButton;
import dyno.swing.designer.beans.events.EditListenerTable;
import dyno.swing.designer.beans.events.StateListenerTable;
import dyno.swing.designer.beans.toolkit.ComponentPalette;
import dyno.swing.designer.treeview.ComponentTree;
import dyno.swing.designer.treeview.ComponentTreeEvent;
import dyno.swing.designer.treeview.ComponentTreeListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.RootPaneContainer;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * ��ƽ��������������ǽ�����ƹ��ߵĺ��ģ���Ҫ������Ǳ���ƽ������ʾ��������Ʋ���״̬��
 * ��ʾ���༭״̬����ʾ�ȵȡ�
 *
 * @author William Chen
 */
public class SwingDesigner extends JComponent implements Constants, TreeSelectionListener, ComponentTreeListener, Scrollable, InvocationHandler {

    /**
     * ��ǰ������Ƶ�������ĸ��ڵ㡣Ŀǰֻ֧��JPanel��Ϊ���ڵ㡣���Ժ����׵��޸�ʹ��֧������
     * ����������Ƶ������name���Զ���Ϊ�գ���ֵΪ������ı������ơ�
     */
    private Component rootComponent;
    /**
     * ��ƽ�������Ͻǵ����ꡣĿ���������ռ���ʾ��ƽ���ı߿�
     */
    private int leftOffset;
    private int topOffset;
    /**
     * ��ǰ��ƽ���״̬�Ƿ������ģʽ��������ƹ��ߵ�״̬���Էֱ༭״̬�����״̬���༭״̬��
     * ��ƽ������ѡ�������ɾ�������С�ճ�������䡢���Ա༭�ȵȲ��������״̬��ָ��ѡ�����
     * ֮����ƽ��������ڵ�״̬�����״̬�ͱ༭״̬����괦��ʽ���ܴ����Է�Ϊ������״̬
     */
    private boolean addingMode;
    /**
     * ����ı������Ƿ����л���Ա������¼���״̬��ֻ��Ϊ���ʱ��ʱ״̬ʹ�á�
     */
    //�༭״̬ʱ��괦����

    private transient EditingMouseListener editingMouseListener;
    //����������ļ����ȼ�����������Ҫ����༭�縴�ơ����С�ɾ����ճ�����ȼ�

    private transient HotKeyProxy keyListener;
    //������״̬�µ���괦����

    private transient AddingMouseListener addingMouseListener;
    //�༭״̬�µ�model���洢�༭״̬�µ���ʱ״̬��������ק��������ȵ�ȵ�

    private transient StateModel stateModel;
    //���״̬�µ�model���洢���״̬�µ���ʱ״̬������Ҫ��ӵ��������ǰ���λ�õȵ�

    private transient AddingModel addingModel;
    //��ǰ���������Ⱦ��painter����ҪĿ��������Ⱦ��������λ����ʾ����ͨ�����ⲿ�����ã���
    //�������Ⱦʱ��������Ⱦ��Щλ����ʾ��

    private transient Painter painter;
    private boolean invalidated = true;
    //�洢��ѡ������ͼ��а��model

    private transient SelectionModel selectionModel;
    private EditListenerTable edit;
    private StateListenerTable state;
    private VariableSpace space;
    private Border outline_border;
    private Action[] designer_actions;
    private Border inner_border;
    private ComponentPalette palette;
    private LookAndFeel designLnf;
    private LookAndFeel ideLnf;

    public void setPalette(ComponentPalette palette) {
        this.palette = palette;
    }

    public VariableSpace getVariableSpace() {
        return space;
    }

    public Dimension getPreferredSize() {
        Dimension size = rootComponent.getSize();
        Insets insets = getOutlineInsets();
        size.width = 2 * leftOffset + size.width + insets.left + insets.right;
        size.height = 2 * topOffset + size.height + insets.top + insets.bottom;
        return size;
    }

    /** Creates a new instance of BeanDesigner */
    public SwingDesigner() {
        setBackground(Color.white);
        setDoubleBuffered(true);
        space = new VariableSpace();
        designLnf = UIManager.getLookAndFeel();
        ideLnf = UIManager.getLookAndFeel();
        //��ʼ��
        leftOffset = 20;
        topOffset = 20;
        //Ϊ�˴�������¼�����ҪSwingDesigner�ܹ���ȡ����
        setFocusable(true);
        edit = new EditListenerTable();
        state = new StateListenerTable();

        //��ʼ��
        selectionModel = new SelectionModel(this);
        stateModel = new StateModel(this);
        //��ʼ��������ƹ��ߵ�UIʵ��
        updateUI();
        //��ʼ��ȱʡ��������
        initRootComponent();
        //��ʼ���¼�������
        initializeListener();
    }

    public EditListenerTable getEditListenerTable() {
        return edit;
    }

    public StateListenerTable getStateListenerTable() {
        return state;
    }

    private void initActionListener(Action[] actions) {
        for (Action action : actions) {
            if (action != null) {
                if (action instanceof DesignerEditListener) {
                    addDesignerEditListener((DesignerEditListener) action);
                }
                if (action instanceof DesignerStateListener) {
                    addDesignerStateListener((DesignerStateListener) action);
                }
                if (action instanceof ActionCategory) {
                    Action[] sub = ((ActionCategory) action).getSubActions();
                    initActionListener(sub);
                }
            }
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Container container = (Container) rootComponent;
        Util.layoutContainer(container);
        repaint();
        return null;
    }

    /**
     * ��ʼ���¼�����������ʼ״̬Ϊ�༭״̬�������³�ʼ������ӱ༭����¼�������
     */
    private void initializeListener() {
        //���
        editingMouseListener = new EditingMouseListener(this);
        //�ȼ�
        keyListener = new HotKeyProxy(this);
        addMouseMotionListener(editingMouseListener);
        addMouseListener(editingMouseListener);
        addKeyListener(keyListener);
        addInvocationHandler(this);
        addDesignerEditListener(new DesignerEditAdapter() {

                    public void componentMoved(DesignerEvent evt) {
                        setInvalidated(true);
                        repaint();
                    }

                    public void componentResized(DesignerEvent evt) {
                        setInvalidated(true);
                        repaint();
                    }

                    @Override
            public void componentCut(DesignerEvent evt) {
                        setInvalidated(true);
                    }

                    @Override
            public void componentDeleted(DesignerEvent evt) {
                        setInvalidated(true);
                    }

                    @Override
            public void componentPasted(DesignerEvent evt) {
                        setInvalidated(true);
                    }

                    public void componentAdded(DesignerEvent evt) {
                        setInvalidated(true);
                    }

                    public void componentEdited(DesignerEvent evt) {
                        setInvalidated(true);
                        Component comp = evt.getAffectedComponents().get(0);
                        Container par = comp.getParent();
                        if (par != null) {
                            LayoutManager layout = par.getLayout();
                            if (layout != null) {
                                Util.layoutContainer(par);
                            }
                        }
                    }
                });
    }

    public void addInvocationHandler(InvocationHandler h) {
        ClassLoader loader = getClass().getClassLoader();
        Class[] interfaces = new Class[]{DesignerEditListener.class, DesignerStateListener.class};
        Object proxyListener = Proxy.newProxyInstance(loader, interfaces, h);
        addDesignerEditListener((DesignerEditListener) proxyListener);
        addDesignerStateListener((DesignerStateListener) proxyListener);
    }

    /**
     * ��ʼ���״̬�����û�ѡ������ϵ����ʱ����
     *
     * @param beanInfo ��ǰѡ�������BeanInfo����
     */
    public void startAddingState(BeanInfo beanInfo) {
        //ɾ���༭״̬���¼�������
        removeMouseListener(editingMouseListener);
        removeMouseMotionListener(editingMouseListener);
        removeKeyListener(keyListener);
        //������ѡ��������BeanInfo������Ӧ��AddingModel
        //AddingModel��StateModel��һ�����ʺϵ�ǰѡ��������ص�
        addingModel = new AddingModel(this, beanInfo);
        addingMouseListener = new AddingMouseListener(this, beanInfo);
        //����¼�
        addMouseListener(addingMouseListener);
        addMouseMotionListener(addingMouseListener);
        //���õ�ǰģʽλ���ģʽ
        setAddingMode(true);
        //����״̬���ģʽ�¼�
        fireStartDesigning();
        repaint();
    }

    /**
     * ֹͣ���ģʽ�����ر༭ģʽ
     */
    public void stopAddingState() {
        //ɾ�����ģʽ�µ���괦����
        removeMouseListener(addingMouseListener);
        removeMouseMotionListener(addingMouseListener);
        //�ָ�Ϊ�գ�UI�����addingModel�Ƿ�վ����Ƿ�ֹͣ��ȾҪ��ӵ����
        addingModel = null;
        addingMouseListener = null;
        painter = null;
        //��ӱ༭״̬�µ�����¼��������ͼ����¼�������
        //������������״̬���Ǻ�����޹صģ���˲���Ҫ��������
        addMouseMotionListener(editingMouseListener);
        addMouseListener(editingMouseListener);
        addKeyListener(keyListener);
        //����ģʽΪ�༭ģʽ
        setAddingMode(false);
        //����ֹͣ���ģʽ���¼�
        fireStopDesigning();
        repaint();
    }

    //������UI��ΪDesignerUI��������Ⱦ

    @Override
    public void updateUI() {
        setUI(new DesignerUI());
    }

    /**
     * ����ק����ѡ��ʽ����ͷ�ʱ���ô˺�����������ѡ������
     * @param e ��ǰ����¼�����������ʼ�㹹��ѡ��򣬼��㱻Ȧ������
     */
    public void selectComponents(MouseEvent e) {
        //����stateModel��selectComponent���±�ѡ��������stateModel��������ק��ʼ��
        stateModel.selectComponents(e);
        //���stateModelΪ����ק״̬
        stateModel.reset();
        repaint();
    }

    /**
     * �����ק����ı���λ�úͳߴ磬�ͷ����ʱ����
     */
    public void releaseComponentsDragging(MouseEvent e) {
        setInvalidated(true);

        //�ָ���ʼ״̬���ı䱻��ק�����λ�úͳߴ�
        stateModel.releaseDragging(e);
        //������ק�¼�
        fireComponentDragged(stateModel.getHotspotComponents());
        repaint();
    }

    /**
     * ��root����ݹ����x,y���ڵ������ע�������ڱ���Ƶ�����������name���Ա��벻Ϊ��
     */
    private Component componentAt(int x, int y, Component root) {
        if (!(root instanceof RootPaneContainer) && !root.isVisible()) {
            return null;
        }

        x -= root.getX();
        y -= root.getY();

        if (root instanceof Container) {
            Container rootContainer = (Container) root;
            int count = rootContainer.getComponentCount();

            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    Component child = rootContainer.getComponent(i);

                    //ֻ��name��Ϊ�յ��������������Χ������ݹ����ݵ���
                    Component dest = componentAt(x, y, child);

                    if (dest != null) {
                        return dest;
                    }
                }
            }
        }

        Rectangle rect = Util.computeVisibleRect(root);
        if (Util.isDesigning(root) && (x >= rect.getX()) && (x <= (rect.getX() + rect.getWidth())) && (y >= rect.getY()) && (y <= (rect.getY() + rect.getHeight()))) {
            //�ж��Ƿ��ڽ�������
            return root;
        }

        return null;
    }

    /**
     * ���ݵ�ǰstateModel������ʶ�����λ��״̬���ݸ���������״
     */
    public void updateCursor() {
        Location location = stateModel.getLocation();

        //����λ��ö�ٵĶ�̬����getCursor��ȡ�����״
        int type = location.getCursor();

        if (type != getCursor().getType()) {
            //���õ�ǰ��״
            setCursor(Cursor.getPredefinedCursor(type));
        }
    }

    private void initRootComponent() {
        try {
            BeanInfo panelInfo = Introspector.getBeanInfo(JPanel.class);
            JPanel panel = (JPanel) Util.instantiateBean(this, panelInfo, "root");
            panel.setSize(400, 300);
            setRootComponent(panel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //�Ƿ������ģʽ

    public boolean isAddingMode() {
        return addingMode;
    }

    public void setAddingMode(boolean addingMode) {
        this.addingMode = addingMode;
    }

    public int getLeftOffset() {
        Insets insets = getOutlineInsets();
        return leftOffset + insets.left;
    }

    public int getOuterLeft() {
        return leftOffset + getOuterInsets().left;
    }

    public int getTopOffset() {
        Insets insets = getOutlineInsets();
        return topOffset + insets.top;
    }

    public int getOuterTop() {
        return topOffset + getOuterInsets().top;
    }

    public void setLeftOffset(int left) {
        leftOffset = left;
    }

    public void setTopOffset(int top) {
        topOffset = top;
    }

    //�������Ե�setter��getter

    public Painter getPainter() {
        return painter;
    }

    public void setPainter(Painter p) {
        painter = p;
    }

    public Component getRootComponent() {
        return rootComponent;
    }

    public boolean isRootComponent(Component comp) {
        return comp == rootComponent;
    }

    Insets getOutlineInsets() {
        return getOutlineBorder().getBorderInsets(this);
    }

    Insets getOuterInsets() {
        return getOuterBorder().getBorderInsets(this);
    }

    //��������¼�e��������λ����Ը������λ�ù�ϵ

    public Location getLoc2Root(MouseEvent e) {
        int x = e.getX() - leftOffset;
        int y = e.getY() - topOffset;
        int width = rootComponent.getWidth();
        int height = rootComponent.getHeight();

        Insets insets = getOutlineInsets();
        if (x < width) {
            if ((y >= height) && (y <= (height + insets.bottom))) {
                return Location.bottom;
            } else {
                return Location.outer;
            }
        } else if (x <= (width + insets.right)) {
            if ((y >= 0) && (y < height)) {
                return Location.right;
            } else if ((y >= height) && (y <= (height + insets.bottom))) {
                return Location.right_bottom;
            } else {
                return Location.outer;
            }
        } else {
            return Location.outer;
        }
    }

    public void setRootComponent(Component component) {
        this.rootComponent = component;
        Util.addContainerNotify((Container) component);
        Util.initComponentAdapter(this, (Container) component);
        Util.layoutContainer((Container) rootComponent);
        TopContainerAdapter rootContainerAdapter = (TopContainerAdapter) AdapterBus.getComponentAdapter(this, component);
        inner_border = rootContainerAdapter.getBorder();
        outline_border = inner_border == null ? areaBorder : BorderFactory.createCompoundBorder(areaBorder, inner_border);
        selectionModel.reset();
        invalidateLayout();
        setInvalidated(true);
    }

    public StateModel getStateModel() {
        return stateModel;
    }

    public AddingModel getAddingModel() {
        return addingModel;
    }

    //��ȡe���ڵ����

    public Component getComponentAt(MouseEvent e) {
        int x = e.getX() - getOuterLeft();
        int y = e.getY() - getOuterTop();
        return componentAt(x, y, rootComponent);
    }

    public Component getComponentAt(int x, int y) {
        return componentAt(x - getOuterLeft(), y - getOuterTop(), rootComponent);
    }

    public SelectionModel getSelectionModel() {
        return selectionModel;
    }

    private void fireComponentDragged(ArrayList<Component> dragged) {
        DesignerEvent evt = new DesignerEvent(this);
        evt.setDraggedComponents(dragged);
        getEditListenerTable().fireComponentMoved(evt);
    }

    private void fireStartDesigning() {
        DesignerEvent evt = new DesignerEvent(this);
        getStateListenerTable().fireStartDesigning(evt);
    }

    private void fireStopDesigning() {
        DesignerEvent evt = new DesignerEvent(this);
        getStateListenerTable().fireStopDesigning(evt);
    }

    public void startDraggingComponent(BeanInfoToggleButton tb) {
        BeanInfo beanInfo = tb.getBeanInfo();
        //������ѡ��������BeanInfo������Ӧ��AddingModel
        //AddingModel��StateModel��һ�����ʺϵ�ǰѡ��������ص�
        addingModel = new AddingModel(this, beanInfo);
        addingMouseListener = new AddingMouseListener(this, beanInfo);
        this.setDropTarget(addingMouseListener);
        //����״̬���ģʽ�¼�
        fireStartDesigning();
        repaint();
    }

    public void startDraggingComponent(Component comp, int x, int y) {
        //������ѡ��������BeanInfo������Ӧ��AddingModel
        //AddingModel��StateModel��һ�����ʺϵ�ǰѡ��������ص�
        addingModel = new AddingModel(comp, x, y);
        addingMouseListener = new AddingMouseListener(this, addingModel.getBeanInfo());
        Component parent = Util.findDesigningParent(comp);
        selectionModel.removeComponent(comp);
        selectionModel.setSelectedComponent(parent);
        this.setDropTarget(addingMouseListener);
        //����״̬���ģʽ�¼�
        fireStartDesigning();
        setInvalidated(true);
        repaint();
    }

    public void valueChanged(TreeSelectionEvent e) {
        ComponentTree tree = (ComponentTree) e.getSource();
        TreePath[] paths = tree.getSelectionPaths();

        if (paths != null) {
            ArrayList<Component> selected = new ArrayList<Component>();

            for (TreePath path : paths) {
                selected.add((Component) path.getLastPathComponent());
            }

            selectionModel.setSelectedComponents(selected);

            TreePath path = e.getNewLeadSelectionPath();
            Component comp = (Component) path.getLastPathComponent();

            if (!Util.isComponentVisible(comp) && !isRootComponent(comp)) {
                makeVisible(comp);
            } else {
                Component parent = comp;
                while (!(parent instanceof JInternalFrame || parent == null)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    ((JInternalFrame) parent).toFront();
                }
            }
        }
    }

    private void invalidateLayout() {
        Container parent = this.getParent();
        if (parent != null) {
            parent.doLayout();
            parent.repaint();
        }
    }

    private void makeVisible(Component comp) {
        Component parent = Util.getFirstInvisibleParent(comp);
        if (isRootComponent(parent)) {
            parent = comp;
            while (!(parent instanceof JInternalFrame || parent == null)) {
                parent = parent.getParent();
            }
            if (parent != null) {
                JInternalFrame jif = (JInternalFrame) parent;
                jif.toFront();
            }
            return;
        }
        while (parent != null) {
            Container parentContainer = parent.getParent();

            if (parentContainer == null) {
                //parent.setVisible(true);
                break;
            } else {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(this, parentContainer);

                if (adapter instanceof ContainerAdapter) {
                    ContainerAdapter containerAdapter = (ContainerAdapter) adapter;
                    containerAdapter.showComponent(parent);
                } else {
                    parent.setVisible(true);
                }

                parent = Util.getFirstInvisibleParent(parent);
            }
        }
    }

    public void addDesignerStateListener(DesignerStateListener listener) {
        getStateListenerTable().addListener(listener);
    }

    public void addDesignerEditListener(DesignerEditListener listener) {
        getEditListenerTable().addListener(listener);
    }

    public void treeChanged(ComponentTreeEvent evt) {
        Util.layoutContainer((Container) getRootComponent());
        this.repaint();
    }

    public void refreshDesignerUI() {
        Util.layoutContainer((Container) getRootComponent());
        repaint();
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 100;
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public Action[] getActions() {
        if (designer_actions == null) {
            initializeActions();
        }
        return designer_actions;
    }

    private void initializeActions() {
        designer_actions = new Action[]{new EditingAction(this), new ViewAction(this, palette), new AlignmentAction(this), new SameSizeAction(this)};
        initActionListener(designer_actions);
    }

    Border getOutlineBorder() {
        return outline_border;
    }

    Border getOuterBorder() {
        return areaBorder;
    }

    Rectangle getOutlineBounds() {
        Insets insets = getOuterBorder().getBorderInsets(this);
        int w = rootComponent.getWidth() + insets.left + insets.right;
        int h = rootComponent.getHeight() + insets.top + insets.bottom;
        return new Rectangle(leftOffset, topOffset, w, h);
    }

    Rectangle getContentBounds() {
        Insets insets = getOuterInsets();
        int x = leftOffset + insets.left;
        int y = topOffset + insets.top;
        int w = rootComponent.getWidth();
        int h = rootComponent.getHeight();
        if (rootComponent instanceof RootPaneContainer) {
            if (inner_border != null) {
                insets = inner_border.getBorderInsets(rootComponent);
                x += insets.left;
                y += insets.top;
            }
            Component component = ((RootPaneContainer) rootComponent).getContentPane();
            w = component.getWidth();
            h = component.getHeight();
        }
        return new Rectangle(x, y, w, h);
    }

    public LookAndFeel getDesignLnf() {
        return designLnf;
    }

    public void setDesignLnf(LookAndFeel designLnf) {
        this.designLnf = designLnf;
        setInvalidated(true);
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(boolean invalidated) {
        this.invalidated = invalidated;
    }

    public Image getDesigerBuffer() {
        if (buffer == null) {
            int w = getWidth() > BUFFER_WIDTH ? getWidth() : BUFFER_WIDTH;
            int h = getHeight() > BUFFER_HEIGHT ? getHeight() : BUFFER_HEIGHT;
            buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        } else if (buffer.getWidth() < getWidth() ||
                buffer.getHeight() < getHeight()) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        return buffer;
    }
    private static int BUFFER_WIDTH = 1024;
    private static int BUFFER_HEIGHT = 1024;
    private BufferedImage buffer;

    public LookAndFeel getIdeLnf() {
        return ideLnf;
    }

    public void setIdeLnf(LookAndFeel ideLnf) {
        this.ideLnf = ideLnf;
    }
}