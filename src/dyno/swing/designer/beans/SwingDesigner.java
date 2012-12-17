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
 * 设计界面组件。该组件是界面设计工具的核心，主要负责的是被设计界面的显示，界面设计操作状态的
 * 显示，编辑状态的显示等等。
 *
 * @author William Chen
 */
public class SwingDesigner extends JComponent implements Constants, TreeSelectionListener, ComponentTreeListener, Scrollable, InvocationHandler {

    /**
     * 当前正在设计的组件树的根节点。目前只支持JPanel作为根节点。可以很容易的修改使其支持其他
     * 容器。被设计的组件其name属性都不为空，其值为该组件的变量名称。
     */
    private Component rootComponent;
    /**
     * 设计界面的左上角的坐标。目的是留出空间显示设计界面的边框。
     */
    private int leftOffset;
    private int topOffset;
    /**
     * 当前设计界面状态是否是添加模式。界面设计工具的状态可以分编辑状态和添加状态。编辑状态的
     * 设计界面可以选择组件、删除、剪切、粘帖、对其、属性编辑等等操作。添加状态是指在选择组件
     * 之后设计界面所处于的状态。添加状态和编辑状态下鼠标处理方式相差很大，所以分为此两种状态
     */
    private boolean addingMode;
    /**
     * 下面的变量都是非序列化成员，不记录设计状态，只作为设计时临时状态使用。
     */
    //编辑状态时鼠标处理器

    private transient EditingMouseListener editingMouseListener;
    //界面设计器的键盘热键处理器，主要处理编辑如复制、剪切、删除、粘帖等热键

    private transient HotKeyProxy keyListener;
    //添加组件状态下的鼠标处理器

    private transient AddingMouseListener addingMouseListener;
    //编辑状态下的model，存储编辑状态下的临时状态，比如拖拽区域、鼠标热点等等

    private transient StateModel stateModel;
    //添加状态下的model，存储添加状态下的临时状态，比如要添加的组件、当前鼠标位置等等

    private transient AddingModel addingModel;
    //当前负责额外渲染的painter，主要目的用来渲染添加组件的位置提示，它通常由外部类设置，在
    //设计器渲染时被调用渲染这些位置提示。

    private transient Painter painter;
    private boolean invalidated = true;
    //存储被选择组件和剪切板的model

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
        //初始化
        leftOffset = 20;
        topOffset = 20;
        //为了处理键盘事件，需要SwingDesigner能够获取焦点
        setFocusable(true);
        edit = new EditListenerTable();
        state = new StateListenerTable();

        //初始化
        selectionModel = new SelectionModel(this);
        stateModel = new StateModel(this);
        //初始化界面设计工具的UI实例
        updateUI();
        //初始化缺省的设计组件
        initRootComponent();
        //初始化事件处理器
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
     * 初始化事件处理器，初始状态为编辑状态，所以下初始化并添加编辑类的事件处理器
     */
    private void initializeListener() {
        //点击
        editingMouseListener = new EditingMouseListener(this);
        //热键
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
     * 开始添加状态，在用户选择面板上的组件时启动
     *
     * @param beanInfo 当前选中组件的BeanInfo对象
     */
    public void startAddingState(BeanInfo beanInfo) {
        //删除编辑状态的事件处理器
        removeMouseListener(editingMouseListener);
        removeMouseMotionListener(editingMouseListener);
        removeKeyListener(keyListener);
        //根据所选择的组件的BeanInfo生成相应的AddingModel
        //AddingModel和StateModel不一样，适合当前选择的组件相关的
        addingModel = new AddingModel(this, beanInfo);
        addingMouseListener = new AddingMouseListener(this, beanInfo);
        //添加事件
        addMouseListener(addingMouseListener);
        addMouseMotionListener(addingMouseListener);
        //设置当前模式位添加模式
        setAddingMode(true);
        //触发状态添加模式事件
        fireStartDesigning();
        repaint();
    }

    /**
     * 停止添加模式、返回编辑模式
     */
    public void stopAddingState() {
        //删除添加模式下的鼠标处理器
        removeMouseListener(addingMouseListener);
        removeMouseMotionListener(addingMouseListener);
        //恢复为空，UI类根据addingModel是否空决定是否停止渲染要添加的组件
        addingModel = null;
        addingMouseListener = null;
        painter = null;
        //添加编辑状态下的鼠标事件处理器和键盘事件处理器
        //由于他们是无状态，是和组件无关的，因此不需要重新生成
        addMouseMotionListener(editingMouseListener);
        addMouseListener(editingMouseListener);
        addKeyListener(keyListener);
        //设置模式为编辑模式
        setAddingMode(false);
        //触发停止添加模式的事件
        fireStopDesigning();
        repaint();
    }

    //设置其UI类为DesignerUI，负责渲染

    @Override
    public void updateUI() {
        setUI(new DesignerUI());
    }

    /**
     * 在拖拽区域选择方式鼠标释放时调用此函数来更新所选择的组件
     * @param e 当前鼠标事件，用来和起始点构成选择框，计算被圈入的组件
     */
    public void selectComponents(MouseEvent e) {
        //调用stateModel的selectComponent更新被选择的组件，stateModel定义了拖拽起始点
        stateModel.selectComponents(e);
        //清除stateModel为非拖拽状态
        stateModel.reset();
        repaint();
    }

    /**
     * 鼠标拖拽组件改变其位置和尺寸，释放鼠标时调用
     */
    public void releaseComponentsDragging(MouseEvent e) {
        setInvalidated(true);

        //恢复初始状态，改变被拖拽组件的位置和尺寸
        stateModel.releaseDragging(e);
        //触发拖拽事件
        fireComponentDragged(stateModel.getHotspotComponents());
        repaint();
    }

    /**
     * 从root组件递归查找x,y所在的组件，注意是正在被设计的组件，因此其name属性必须不为空
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

                    //只有name不为空的组件才是搜索范围，这儿递归下溯调用
                    Component dest = componentAt(x, y, child);

                    if (dest != null) {
                        return dest;
                    }
                }
            }
        }

        Rectangle rect = Util.computeVisibleRect(root);
        if (Util.isDesigning(root) && (x >= rect.getX()) && (x <= (rect.getX() + rect.getWidth())) && (y >= rect.getY()) && (y <= (rect.getY() + rect.getHeight()))) {
            //判断是否处于交叉区域
            return root;
        }

        return null;
    }

    /**
     * 根据当前stateModel中所标识的鼠标位置状态数据更新鼠标的形状
     */
    public void updateCursor() {
        Location location = stateModel.getLocation();

        //调用位置枚举的多态方法getCursor获取鼠标形状
        int type = location.getCursor();

        if (type != getCursor().getType()) {
            //设置当前形状
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

    //是否是添加模式

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

    //各种属性的setter和getter

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

    //计算鼠标事件e所发生的位置相对根组件的位置关系

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

    //获取e所在的组件

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
        //根据所选择的组件的BeanInfo生成相应的AddingModel
        //AddingModel和StateModel不一样，适合当前选择的组件相关的
        addingModel = new AddingModel(this, beanInfo);
        addingMouseListener = new AddingMouseListener(this, beanInfo);
        this.setDropTarget(addingMouseListener);
        //触发状态添加模式事件
        fireStartDesigning();
        repaint();
    }

    public void startDraggingComponent(Component comp, int x, int y) {
        //根据所选择的组件的BeanInfo生成相应的AddingModel
        //AddingModel和StateModel不一样，适合当前选择的组件相关的
        addingModel = new AddingModel(comp, x, y);
        addingMouseListener = new AddingMouseListener(this, addingModel.getBeanInfo());
        Component parent = Util.findDesigningParent(comp);
        selectionModel.removeComponent(comp);
        selectionModel.setSelectedComponent(parent);
        this.setDropTarget(addingMouseListener);
        //触发状态添加模式事件
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