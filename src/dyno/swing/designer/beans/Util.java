/*
 * Util.java
 *
 * Created on August 1, 2007, 1:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import dyno.swing.designer.beaninfo.DefaultValue;
import dyno.swing.designer.beans.painters.NullLayoutPainter;
import dyno.swing.designer.properties.editors.BooleanEditor;
import dyno.swing.designer.properties.editors.BorderEditor;
import dyno.swing.designer.properties.editors.ColorEditor;
import dyno.swing.designer.properties.editors.DimensionEditor;
import dyno.swing.designer.properties.editors.DoubleEditor;
import dyno.swing.designer.properties.editors.FloatEditor;
import dyno.swing.designer.properties.editors.FontEditor;
import dyno.swing.designer.properties.editors.InsetsEditor;
import dyno.swing.designer.properties.editors.IntegerPropertyEditor;
import dyno.swing.designer.properties.editors.LongEditor;
import dyno.swing.designer.properties.editors.PointEditor;
import dyno.swing.designer.properties.editors.RectangleEditor;
import dyno.swing.designer.properties.editors.StringEditor;
import dyno.swing.designer.properties.renderer.BorderCellRenderer;
import dyno.swing.designer.properties.renderer.ColorCellRenderer;
import dyno.swing.designer.properties.renderer.DimensionCellRenderer;
import dyno.swing.designer.properties.renderer.FontCellRenderer;
import dyno.swing.designer.properties.renderer.PointCellRenderer;
import dyno.swing.designer.properties.renderer.RectangleCellRenderer;
import dyno.swing.designer.properties.editors.RectangleEditor;
import dyno.swing.designer.properties.renderer.InsetsCellRenderer;
import dyno.swing.designer.properties.types.Item;
import dyno.swing.designer.properties.wrappers.BorderWrapper;
import dyno.swing.designer.properties.wrappers.ColorWrapper;
import dyno.swing.designer.properties.wrappers.ComboBoxModelWrapper;
import dyno.swing.designer.properties.wrappers.DimensionWrapper;
import dyno.swing.designer.properties.wrappers.FontWrapper;
import dyno.swing.designer.properties.wrappers.IconWrapper;
import dyno.swing.designer.properties.wrappers.ImageWrapper;
import dyno.swing.designer.properties.wrappers.InsetsWrapper;
import dyno.swing.designer.properties.wrappers.ListModelWrapper;
import dyno.swing.designer.properties.wrappers.PointWrapper;
import dyno.swing.designer.properties.wrappers.RectangleWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import dyno.swing.designer.properties.wrappers.SpinnerModelWrapper;
import dyno.swing.designer.properties.wrappers.primitive.StringWrapper;
import dyno.swing.designer.properties.wrappers.TableModelSourceCoder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.EventSetDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.LookAndFeel;
import javax.swing.SpinnerModel;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * 工具类，提供常用的工具方法
 * @author William Chen
 */
public class Util implements Constants {

    private static Item[] GUI_TYPES = new Item[]{new Item("JFrame", "NewJFrame", "javax.swing.JFrame"),
            new Item("JPanel", "NewJPanel", "javax.swing.JPanel")};

    public static Item[] getGuiTypes() {
        return GUI_TYPES;
    }

    public static boolean isXP() {
        Boolean xp = (Boolean) Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive");
        return xp != null && xp.booleanValue();
    }

    public static Item getDefaultGuiType() {
        return GUI_TYPES[0];
    }
    private static HashMap<Class, Class<? extends PropertyEditor>> propertyEditorClasses;
    private static HashMap<Class, Class<? extends TableCellRenderer>> cellRendererClasses;
    private static HashMap<Class, Class<? extends SourceCoder>> sourceCoders;
    static {
        propertyEditorClasses = new HashMap<Class, Class<? extends PropertyEditor>>();
        propertyEditorClasses.put(String.class, StringEditor.class);
        propertyEditorClasses.put(boolean.class, BooleanEditor.class);
        propertyEditorClasses.put(Color.class, ColorEditor.class);
        propertyEditorClasses.put(Font.class, FontEditor.class);
        propertyEditorClasses.put(Point.class, PointEditor.class);
        propertyEditorClasses.put(Dimension.class, DimensionEditor.class);
        propertyEditorClasses.put(Rectangle.class, RectangleEditor.class);
        propertyEditorClasses.put(int.class, IntegerPropertyEditor.class);
        propertyEditorClasses.put(long.class, LongEditor.class);
        propertyEditorClasses.put(float.class, FloatEditor.class);
        propertyEditorClasses.put(double.class, DoubleEditor.class);
        propertyEditorClasses.put(Border.class, BorderEditor.class);
        propertyEditorClasses.put(Insets.class, InsetsEditor.class);

        cellRendererClasses = new HashMap<Class, Class<? extends TableCellRenderer>>();
        cellRendererClasses.put(Color.class, ColorCellRenderer.class);
        cellRendererClasses.put(Font.class, FontCellRenderer.class);
        cellRendererClasses.put(String.class, DefaultTableCellRenderer.class);
        cellRendererClasses.put(int.class, DefaultTableCellRenderer.class);
        cellRendererClasses.put(long.class, DefaultTableCellRenderer.class);
        cellRendererClasses.put(float.class, DefaultTableCellRenderer.class);
        cellRendererClasses.put(double.class, DefaultTableCellRenderer.class);
        cellRendererClasses.put(Point.class, PointCellRenderer.class);
        cellRendererClasses.put(Dimension.class, DimensionCellRenderer.class);
        cellRendererClasses.put(Rectangle.class, RectangleCellRenderer.class);
        cellRendererClasses.put(Border.class, BorderCellRenderer.class);
        cellRendererClasses.put(Insets.class, InsetsCellRenderer.class);

        sourceCoders = new HashMap<Class, Class<? extends SourceCoder>>();
        sourceCoders.put(String.class, StringWrapper.class);
        sourceCoders.put(Color.class, ColorWrapper.class);
        sourceCoders.put(Font.class, FontWrapper.class);
        sourceCoders.put(Icon.class, IconWrapper.class);
        sourceCoders.put(Image.class, ImageWrapper.class);
        sourceCoders.put(Dimension.class, DimensionWrapper.class);
        sourceCoders.put(Point.class, PointWrapper.class);
        sourceCoders.put(Rectangle.class, RectangleWrapper.class);
        sourceCoders.put(Border.class, BorderWrapper.class);
        sourceCoders.put(Insets.class, InsetsWrapper.class);
        sourceCoders.put(ListModel.class, ListModelWrapper.class);
        sourceCoders.put(ComboBoxModel.class, ComboBoxModelWrapper.class);
        sourceCoders.put(TableModel.class, TableModelSourceCoder.class);
        sourceCoders.put(SpinnerModel.class, SpinnerModelWrapper.class);
        sourceCoders.put(Insets.class, InsetsWrapper.class);
        sourceCoders.put(Point.class, PointWrapper.class);
    }

    public static boolean isDesigning(Component component) {
        String name = component.getName();
        return name != null && name.startsWith("_$$");
    }

    public static String getComponentName(Component component) {
        String name = component.getName();
        if (name != null && name.startsWith("_$$")) {
            return name.substring(3);
        } else {
            return name;
        }
    }

    public static void addContainerNotify(Container container) {
        container.addNotify();
        int count = container.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component comp = container.getComponent(i);
            if (comp instanceof Container) {
                addContainerNotify((Container) comp);
            } else {
                comp.addNotify();
            }
        }
    }

    public static void initComponentAdapter(SwingDesigner designer, Container container) {
        if (isDesigning(container)) {
            AdapterBus.getComponentAdapter(designer, container);
        }

        int count = container.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component comp = container.getComponent(i);
            if (comp instanceof Container) {
                initComponentAdapter(designer, (Container) comp);
            } else if (isDesigning(comp)) {
                AdapterBus.getComponentAdapter(designer, comp);
            }
        }
    }

    public static void setComponentName(Component component, String newName) {
        component.setName("_$$" + newName);
    }

    public static Component findComponentWithName(String name, Component root) {
        String rootName = getComponentName(root);
        if (rootName != null && rootName.equals(name)) {
            return root;
        }
        if (root instanceof Container) {
            Container container = (Container) root;
            int count = container.getComponentCount();
            for (int i = 0; i < count; i++) {
                Component child = container.getComponent(i);
                if (isDesigning(child)) {
                    Component comp = findComponentWithName(name, child);
                    if (comp != null) {
                        return comp;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 重新布局容器，递归调用完成设计界面的更新。主要用于布局管理器改变时，或者组件尺寸被拖拽
     * 发生变化时。
     *
     */
    public static void layoutContainer(Container container) {
        Container parent = getTopContainer(container);
        _layoutContainer(parent);
    }

    /**
     * 由container开始上溯到第一个没有布局管理器的组件容器
     */
    private static Container getTopContainer(Container container) {
        Container parent = container.getParent();

        if (parent == null) {
            return container;
        }

        LayoutManager layout = parent.getLayout();

        if (layout == null) {
            return container;
        }

        return getTopContainer(parent);
    }

    public static boolean isComponentVisible(Component comp) {
        if (!comp.isVisible() && !isRootComponent(comp)) {
            return false;
        }
        if (comp instanceof JInternalFrame) {
            JInternalFrame jif = (JInternalFrame) comp;
            if (jif.isIcon()) {
                return false;
            }
        }
        Component parent = comp.getParent();

        if (parent != null) {
            return isComponentVisible(parent);
        }

        return true;
    }

    /**
     * 递归方式布局整个由container开始的容器
     */
    private static void _layoutContainer(Container container) {
        LayoutManager layout = container.getLayout();
        if (layout != null) {
            container.doLayout();
        }

        int count = container.getComponentCount();

        for (int i = 0; i < count; i++) {
            Component child = container.getComponent(i);

            if (child instanceof Container) {
                _layoutContainer((Container) child);
            }
        }
    }

    /**
     * 获取component所在的容器的绝对位置
     */
    public static Rectangle getRelativeBounds(Component component) {
        Rectangle bounds = new Rectangle(0, 0, component.getWidth(), component.getHeight());
        Container parent = component.getParent();

        while (parent != null) {
            bounds.x += component.getX();
            bounds.y += component.getY();
            component = parent;
            parent = component.getParent();
        }

        return bounds;
    }

    /**
     * 使用beanInfo初始化一个组件，并赋予beanNamed的变量名
     */
    public static Component instantiateBean(SwingDesigner designer, BeanInfo beanInfo, String beanName) {
        LookAndFeel idelnf = designer.getIdeLnf();
        try {
            UIManager.setLookAndFeel(designer.getDesignLnf());
            Class bean_class = beanInfo.getBeanDescriptor().getBeanClass();
            Component comp = (Component) bean_class.newInstance();
            Util.setComponentName(comp, beanName);
            ComponentAdapter adapter = AdapterBus.createComponentAdapter(designer, comp);
            adapter.initialize();
            comp.addNotify();
            ((JComponent) comp).putClientProperty("component.adapter", adapter);
            return comp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                UIManager.setLookAndFeel(idelnf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取焦点组件所在的顶层容器
     */
    public static Container getHotspotContainer(SwingDesigner designer, Component comp) {
        while (comp != null) {
            ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);

            if (adapter instanceof ContainerAdapter && Util.isDesigning(comp)) {
                return (Container) comp;
            }

            comp = comp.getParent();
        }

        return null;
    }

    public static HoverPainter getContainerPainter(SwingDesigner designer, Component container) {

        //容器组件的适配器
        ComponentAdapter componentAdapter = AdapterBus.getComponentAdapter(designer, container);

        if (componentAdapter instanceof ContainerAdapter) {
            ContainerAdapter containerAdapter = (ContainerAdapter) componentAdapter;
            HoverPainter painter = containerAdapter.getPainter();
            if (painter != null) {
                return painter;
            }
        }
        //否则，看当前的容器是否有布局管理器
        Container parent = (Container) container;
        LayoutManager layout = parent.getLayout();

        if (layout != null) {
            //如果有，则获取其布局管理器的适配器
            LayoutAdapter layoutAdapter = AdapterBus.getLayoutAdapter(designer, parent);
            HoverPainter painter = layoutAdapter.getPainter();
            if (painter != null) {
                return painter;
            } else {
                return new NullLayoutPainter(designer, parent);
            }
        } else {
            //设置一个缺省的提示渲染器
            return new NullLayoutPainter(designer, parent);
        }
    }

    /**
     * 恢复双缓冲状态，dbcomponents保存着初始状态为启动双缓冲的组件
     */
    public static void resetBuffer(ArrayList<JComponent> dbcomponents) {
        for (JComponent jcomponent : dbcomponents) {
            jcomponent.setDoubleBuffered(true);
        }
    }

    /**
     * 禁止双缓冲状态，并将初始状态为启动双缓冲的组件保存到dbcomponents中
     */
    public static void disableBuffer(Component comp, ArrayList<JComponent> dbcomponents) {
        if ((comp instanceof JComponent) && ((JComponent) comp).isDoubleBuffered()) {
            JComponent jcomponent = (JComponent) comp;

            dbcomponents.add(jcomponent);
            jcomponent.setDoubleBuffered(false);
        }

        if (comp instanceof Container) {
            Container container = (Container) comp;
            int count = container.getComponentCount();

            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    Component component = container.getComponent(i);

                    disableBuffer(component, dbcomponents);
                }
            }
        }
    }

    public static Component getFirstInvisibleParent(Component comp) {
        Component parent = comp;

        while ((parent != null) && parent.isVisible()) {
            parent = parent.getParent();
        }

        return parent;
    }

    public static int indexOfComponent(Container container, Component target) {
        int count = container.getComponentCount();

        for (int i = 0; i < count; i++) {
            Component child = container.getComponent(i);

            if (child == target) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 计算组件root相对于其顶层容器的可见区域
     */
    public static Rectangle computeVisibleRectRel2Root(Component root) {
        Container container = findAncestorScrollPane(root);

        if (container == null) {
            return getRelativeBounds(root);
        } else {
            //如果是JScrollPane的子组件，需要计算其viewport与改组件的交叉的可见区域
            return getBoundsRel2Parent(root, container);
        }
    }

    /**
     * 计算组件root相对于其顶层容器的可见区域
     */
    public static Rectangle computeVisibleRect(Component root) {
        Rectangle root_bounds = Util.getRelativeBounds(root);
        Rectangle rect = computeVisibleRectRel2Root(root);
        rect.x -= root_bounds.x;
        rect.y -= root_bounds.y;

        return rect;
    }

    private static Rectangle getBoundsRel2Parent(Component child, Container parent) {
        Rectangle cRect = getRelativeBounds(child);
        Rectangle pRect = getRelativeBounds(parent);
        Rectangle bounds = new Rectangle();
        Rectangle.intersect(cRect, pRect, bounds);

        return bounds;
    }

    public static Container findAncestorScrollPane(Component p) {
        if ((p == null) || !(p instanceof Container)) {
            return null;
        }

        Container container = (Container) p;

        if (container instanceof JScrollPane) {
            return (JScrollPane) container;
        }

        Container c = p.getParent();

        return findAncestorScrollPane(c);
    }

    public static Class<? extends PropertyEditor> getPropertyEditorClass(Class propType) {
        return propertyEditorClasses.get(propType);
    }

    public static void showMessage(String message, Component editorComponent) {
        JOptionPane.showMessageDialog(editorComponent, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean isStringNull(String string) {
        return (string == null) || (string.trim().length() == 0);
    }

    public static Class<? extends TableCellRenderer> getTableCellRendererClass(Class propType) {
        return cellRendererClasses.get(propType);
    }

    public static Class<? extends SourceCoder> getSourceCoder(Class propType) {
        Class<? extends SourceCoder> clazz = sourceCoders.get(propType);
        if (clazz != null) {
            return clazz;
        }
        Class[] interfaces = propType.getInterfaces();
        if (interfaces != null) {
            for (Class ins : interfaces) {
                clazz = getSourceCoder(ins);
                if (clazz != null) {
                    return clazz;
                }
            }
        }
        if (!propType.isInterface()) {
            Class superclass = propType.getSuperclass();
            if (superclass == null) {
                return null;
            }
            clazz = getSourceCoder(superclass);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }

    public static void set12FontFor(Component container) {
        container.setFont(new Font("Dialog", 0, 12));
        if (container instanceof Container) {
            int count = ((Container) container).getComponentCount();
            for (int i = 0; i < count; i++) {
                Component component = ((Container) container).getComponent(i);
                set12FontFor(component);
            }
        }
    }

    public static boolean isPropertyChanged(LookAndFeel lnf, Object bean, PropertyDescriptor property) {
        if (property.getName().equals("preferredSize")) {
            Component component = (Component) bean;
            return component.isPreferredSizeSet();
        } else if (property.getName().equals("minimumSize")) {
            Component component = (Component) bean;
            return component.isMinimumSizeSet();
        } else if (property.getName().equals("minimumSize")) {
            Component component = (Component) bean;
            return component.isMaximumSizeSet();
        } else if (property.getName().equals("name")) {
            return false;
        } else if (property.getName().equals("text")) {
            return true;
        } else if (needGenCode(property)) {
            Object default_value = getDefaultValue(lnf, property);
            Object value = readBeanValue(bean, property);
            return isChanged(value, default_value);
        } else {
            return false;
        }
    }

    public static Object getDefaultValue(LookAndFeel lnf, PropertyDescriptor property) {
        HashMap<String, DefaultValue> dValues = (HashMap<String, DefaultValue>) property.getValue("default-value");
        DefaultValue dValue = dValues.get(lnf.getName());
        Class beanClass = (Class) property.getValue("bean-class");
        String beanName = beanClass.getName();
        if (!dValue.isSet()) {
            HashMap<String, Object> objects = lnf_beans.get(lnf.getName());
            if (objects == null) {
                objects = new HashMap<String, Object>();
                lnf_beans.put(lnf.getName(), objects);
            }
            Object bean = objects.get(beanName);
            if (bean == null) {
                bean = createBean(lnf, beanName);
                objects.put(beanName, bean);
            }
            Object value = Util.readBeanValue(bean, property);
            dValue.setValue(value);
        }
        return dValue.getValue();
    }

    private static Object createBean(LookAndFeel lnf, String beanName) {
        LookAndFeel ideLnf = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(lnf);
            return Beans.instantiate(Util.class.getClassLoader(), beanName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                UIManager.setLookAndFeel(ideLnf);
            } catch (Exception ex) {
            }
        }
    }
    private static HashMap<String, HashMap<String, Object>> lnf_beans = new HashMap<String, HashMap<String, Object>>();

    private static boolean needGenCode(PropertyDescriptor property) {
        String gencode = (String) property.getValue("gencode");
        if (Util.isStringNull(gencode)) {
            return true;
        } else {
            return gencode.toLowerCase().trim().equals("true");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public static boolean isChanged(Object old_value, Object value) {
        if (old_value != null && value != null && old_value != null) {
            Class old_class = old_value.getClass();
            Class new_class = value.getClass();
            if (old_class != new_class) {
                return false;
            }
            Comparator comparator = getComparator(old_class);
            if (comparator != null) {
                return comparator.compare(old_value, value) != 0;
            } else if (old_value == null) {
                return !value.equals(old_value);
            } else {
                return !old_value.equals(value);
            }
        } else {
            return false;
        }
    }

    public static Comparator getComparator(Class propType) {
        Comparator comparator = comparators.get(propType);
        if (comparator != null) {
            return comparator;
        }
        Class[] interfaces = propType.getInterfaces();
        if (interfaces != null) {
            for (Class ins : interfaces) {
                comparator = getComparator(ins);
                if (comparator != null) {
                    return comparator;
                }
            }
        }
        if (!propType.isInterface()) {
            Class superclass = propType.getSuperclass();
            if (superclass == null) {
                return null;
            }
            comparator = getComparator(superclass);
            if (comparator != null) {
                return comparator;
            }
        }
        return null;
    }

    public static Object readBeanValue(Object bean, PropertyDescriptor property) {
        try {
            Method m = property.getReadMethod();
            return m.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGetName(String cname) {
        return "get" + Character.toUpperCase(cname.charAt(0)) + cname.substring(1);
    }

    public static boolean isDesigningContainer(SwingDesigner designer, Component comp) {
        ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, comp);
        return adapter instanceof ContainerAdapter;
    }

    public static Component findDesigningParent(Component comp) {
        Component parent = comp.getParent();
        if (Util.isRootComponent(parent) || Util.isDesigning(parent)) {
            return parent;
        }
        return findDesigningParent(parent);
    }

    public static boolean isRootComponent(Component root) {
        Container parent = root.getParent();
        return parent == null;
    }
    private static HashMap<Class, Comparator> comparators;
    static {
        comparators = new HashMap<Class, Comparator>();
        comparators.put(DefaultComboBoxModel.class, new DefaultComboBoxModelComparator());
        comparators.put(Font.class, new FontComparator());
        comparators.put(Color.class, new ColorComparator());
        comparators.put(ListModel.class, new ListModelComparator());
        comparators.put(SpinnerModel.class, new SpinnerModelComparator());
        comparators.put(String.class, new StringComparator());
        comparators.put(EmptyBorder.class, new EmptyBorderComparator());
        comparators.put(CompoundBorder.class, new CompoundBorderComparator());
        comparators.put(BasicBorders.MarginBorder.class, new BasicBordersMarginBorderComparator());
        comparators.put(BasicBorders.RadioButtonBorder.class, new BasicBordersRadioButtonBorderComparator());
        comparators.put(LineBorder.class, new LineBorderComparator());
        comparators.put(BasicBorders.SplitPaneBorder.class, new BasicBorderSplitPaneBorderComparator());
        comparators.put(AbstractBorder.class, new AbstractBorderComparator());
        comparators.put(Icon.class, new IconComparator());
    }

    private static class IconComparator implements Comparator<Icon> {

        public int compare(Icon o1, Icon o2) {
            return 0;
        }
    }

    private static class AbstractBorderComparator implements Comparator<AbstractBorder> {

        public int compare(AbstractBorder o1, AbstractBorder o2) {
            return 0;
        }
    }

    private static class BasicBorderSplitPaneBorderComparator implements Comparator<BasicBorders.SplitPaneBorder> {

        public int compare(SplitPaneBorder o1, SplitPaneBorder o2) {
            return 0;
        }
    }

    private static class LineBorderComparator implements Comparator<LineBorder> {

        public int compare(LineBorder o1, LineBorder o2) {
            Color c1 = o1.getLineColor();
            Color c2 = o2.getLineColor();
            int t1 = o1.getThickness();
            int t2 = o2.getThickness();
            boolean b1 = o1.getRoundedCorners();
            boolean b2 = o2.getRoundedCorners();
            if (c1 == c2) {
                return t1 == t2 && b1 == b2 ? 0 : 1;
            } else {
                if (c1 != null && c2 != null && c1.equals(c2)) {
                    return t1 == t2 && b1 == b2 ? 0 : 1;
                } else {
                    return 1;
                }
            }
        }
    }

    private static class BasicBordersRadioButtonBorderComparator implements Comparator<BasicBorders.RadioButtonBorder> {

        public int compare(RadioButtonBorder o1, RadioButtonBorder o2) {
            return 0;
        }
    }

    private static class BasicBordersMarginBorderComparator implements Comparator<BasicBorders.MarginBorder> {

        public int compare(MarginBorder o1, MarginBorder o2) {
            return 0;
        }
    }

    private static class CompoundBorderComparator implements Comparator<CompoundBorder> {

        public int compare(CompoundBorder o1, CompoundBorder o2) {
            Border inner1 = o1.getInsideBorder();
            Border inner2 = o2.getInsideBorder();
            if (!borderEquals(inner1, inner2)) {
                return 1;
            }
            Border outer1 = o1.getOutsideBorder();
            Border outer2 = o2.getOutsideBorder();
            if (!borderEquals(outer1, outer2)) {
                return 1;
            }
            return 0;
        }

        boolean borderEquals(Border b1, Border b2) {
            if (b1 != null && b2 != null) {
                if (b1.getClass() == b2.getClass()) {
                    Comparator comparator = getComparator(b1.getClass());
                    if (comparator != null) {
                        return comparator.compare(b1, b2) == 0;
                    } else if (b1.equals(b2)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private static class EmptyBorderComparator implements Comparator<EmptyBorder> {

        public int compare(EmptyBorder o1, EmptyBorder o2) {
            Insets insets1 = o1.getBorderInsets();
            Insets insets2 = o2.getBorderInsets();
            if (insets1.equals(insets2)) {
                return 0;
            }
            return 1;
        }
    }

    private static class StringComparator implements Comparator<String> {

        public int compare(String o1, String o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else if (o2.trim().length() == 0) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                if (o2 == null) {
                    if (o1.trim().length() == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if (o1.trim().length() == 0 && o2.trim().length() == 0) {
                    return 0;
                } else {
                    return o1.compareTo(o2);
                }
            }
        }
    }

    private static class SpinnerModelComparator implements Comparator<SpinnerModel> {

        @Override
        public int compare(SpinnerModel o1, SpinnerModel o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o2 == null) {
                return 1;
            }
            Class c1 = o1.getClass();
            Class c2 = o2.getClass();
            if (c1 == c2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private static class ListModelComparator implements Comparator<ListModel> {

        @Override
        public int compare(ListModel o1, ListModel o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o2 == null) {
                return 1;
            }
            Class c1 = o1.getClass();
            Class c2 = o2.getClass();
            if (c1 == c2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private static class ColorComparator implements Comparator<Color> {

        @Override
        public int compare(Color o1, Color o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o2 == null) {
                return 1;
            }
            int r1 = o1.getRed();
            int g1 = o1.getGreen();
            int b1 = o1.getBlue();
            int r2 = o2.getRed();
            int g2 = o2.getGreen();
            int b2 = o2.getBlue();
            boolean e = r1 == r2 && g1 == g2 && b1 == b2;
            return e ? 0 : 1;
        }
    }

    private static class DefaultComboBoxModelComparator implements Comparator<DefaultComboBoxModel> {

        @Override
        public int compare(DefaultComboBoxModel o1, DefaultComboBoxModel o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o2 == null) {
                return 1;
            }
            int s1 = o1.getSize();
            int s2 = o2.getSize();
            if (s1 < s2) {
                return -1;
            } else if (s1 == s2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private static class FontComparator implements Comparator<Font> {

        @Override
        public int compare(Font o1, Font o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o2 == null) {
                return 1;
            }
            String f1 = o1.getFamily();
            String n1 = o1.getName();
            String f2 = o2.getFamily();
            String n2 = o2.getName();
            int st1 = o1.getStyle();
            int st2 = o2.getStyle();
            int s1 = o1.getSize();
            int s2 = o2.getSize();
            boolean e = (f1.equals(f2) || f1.equals(n2) || f2.equals(n1)) && st1 == st2 && s1 == s2;
            if (e) {
                return 0;
            }
            return 1;
        }
    }

    public static String getCapitalName(String name) {
        return "" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public static EventSetDescriptor getESD(String esn, Component component) {
        try {
            BeanInfo info = Introspector.getBeanInfo(component.getClass());
            EventSetDescriptor[] esds = info.getEventSetDescriptors();
            if (esds != null) {
                for (EventSetDescriptor esd : esds) {
                    if (esd.getName().equals(esn)) {
                        return esd;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}