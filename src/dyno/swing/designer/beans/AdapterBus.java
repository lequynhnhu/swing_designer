/*
 * Adapter.java
 *
 * Created on 2007年5月2日, 下午11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import dyno.swing.designer.beans.adapters.borders.DefaultBorderAdapter;
import dyno.swing.designer.beans.adapters.component.DefaultComponentAdapter;
import dyno.swing.designer.beans.adapters.layout.DefaultLayoutAdapter;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.border.Border;


/**
 * 适配器中枢，为组件和组件适配器、布局和布局适配器，提供映射关系的中枢
 * 这个类是整个设计器的扩展点。如果需要扩展新的组件类型或者布局管理器的特殊行为，
 * 只需要在components.properties和layouts.properties添加新的映射关系就可以了。
 *
 * @author William Chen
 */
public class AdapterBus {

    //组件类<-->组件适配器类，在component.properties中定义，该映射表还为该组件子类提供实例化帮助
    private static HashMap<Class<? extends Component>, Class<? extends ComponentAdapter>> componentAdapterClasses;
    //组件类<-->组件适配器，由上一个映射关系生成
    //private static HashMap<Class<?extends Component>, ComponentAdapter> componentAdapters;
    //布局类<-->布局适配器，在layouts.properties中定义
    private static HashMap<Class<? extends LayoutManager>, Class<? extends LayoutAdapter>> layoutAdapters;
    private static HashMap<Class<? extends Border>, Class<? extends BorderAdapter>> borderAdapters;
    static {
        //初始化
        initComponentAdapters();
        initLayoutAdapters();
        initBorderAdapters();
    }

    /**
     * 初始化组件适配器
     */
    @SuppressWarnings(value = "unchecked")
    private static void initComponentAdapters() {
        try {
            componentAdapterClasses = new HashMap<Class<? extends Component>, Class<? extends ComponentAdapter>>();

            Properties prop = new Properties();
            prop.load(AdapterBus.class.getResourceAsStream("components.properties"));

            Set<String> keys = prop.stringPropertyNames();

            for (String compClassName : keys) {
                String adapterClassName = prop.getProperty(compClassName);
                Class<? extends Component> compClass = (Class<? extends Component>) Class.forName(compClassName);
                Class<? extends ComponentAdapter> adapterClass = (Class<? extends ComponentAdapter>) Class.forName(adapterClassName);
                componentAdapterClasses.put(compClass, adapterClass);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化布局适配器
     */
    @SuppressWarnings(value = "unchecked")
    private static void initLayoutAdapters() {
        try {
            layoutAdapters = new HashMap<Class<? extends LayoutManager>, Class<? extends LayoutAdapter>>();

            Properties prop = new Properties();
            prop.load(AdapterBus.class.getResourceAsStream("layouts.properties"));

            Set<String> keys = prop.stringPropertyNames();

            for (String layoutClassName : keys) {
                String adapterClassName = prop.getProperty(layoutClassName);
                Class<LayoutManager> layoutClass = (Class<LayoutManager>) Class.forName(layoutClassName);
                Class<LayoutAdapter> adapterClass = (Class<LayoutAdapter>) Class.forName(adapterClassName);
                layoutAdapters.put(layoutClass, adapterClass);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void initBorderAdapters() {
        try {
            borderAdapters = new HashMap<Class<? extends Border>, Class<? extends BorderAdapter>>();

            Properties prop = new Properties();
            prop.load(AdapterBus.class.getResourceAsStream("borders.properties"));

            Set<String> keys = prop.stringPropertyNames();

            for (String borderClassName : keys) {
                String adapterClassName = prop.getProperty(borderClassName);
                Class<Border> borderClass = (Class<Border>) Class.forName(borderClassName);
                Class<BorderAdapter> adapterClass = (Class<BorderAdapter>) Class.forName(adapterClassName);
                borderAdapters.put(borderClass, adapterClass);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取组件类型是componentClass对应的组件适配器，如果初始映射表中没有该适配器，
     * 则继续查找其父类对应的适配器，直至查找到Component类为止，如果还是没有查找到，
     * 则使用缺省的组件适配器：DefaultComponentAdapter
     *
     * @return 该组件类所对应的组件适配器对象
     */
    public static ComponentAdapter createComponentAdapter(SwingDesigner designer, Component component) {
        Class<? extends Component> componentClass = component.getClass();
        ComponentAdapter adapter;

        //如映射中没有，则查找其父类、祖先类直到Component或者空
        Class parentClass = searchAdapterClass(componentAdapterClasses, componentClass, Component.class);

        if ((parentClass == null) || (parentClass == Component.class)) {
            //如果没有查找到则使用缺省的组件适配器
            adapter = new DefaultComponentAdapter(designer, component);
        } else {
            try {
                //查找了，则为实例化一个适配器
                Class<? extends ComponentAdapter> adapterClass = componentAdapterClasses.get(parentClass);
                Constructor<? extends ComponentAdapter> cons = adapterClass.getConstructor(SwingDesigner.class, Component.class);
                adapter = cons.newInstance(designer, component);
            } catch (Exception ex) {
                ex.printStackTrace();
                //实例化失败，则转为缺省的组件适配器
                adapter = new DefaultComponentAdapter(designer, component);
            }
        }
        return adapter;
    }

    private static Class searchAdapterClass(HashMap maps, Class clazz, Class stop) {
        Class parentClass = clazz;
        while ((parentClass != null) && (parentClass != stop) && (maps.get(parentClass) == null)) {
            parentClass = parentClass.getSuperclass();
        }
        return parentClass;
    }
    public static JComponent getJComponent(Component component){
        JComponent jcomponent;
        if (component instanceof JComponent) {
            jcomponent = (JComponent) component;
        } else if (component instanceof RootPaneContainer) {
            jcomponent = (JComponent)((RootPaneContainer) component).getContentPane();
        } else {
            return null;
        }
        return jcomponent;
    }
    /**
     * 获取组件类型是componentClass对应的组件适配器，如果初始映射表中没有该适配器，
     * 则继续查找其父类对应的适配器，直至查找到Component类为止，如果还是没有查找到，
     * 则使用缺省的组件适配器：DefaultComponentAdapter
     *
     * @return 该组件类所对应的组件适配器对象
     */
    public static ComponentAdapter getComponentAdapter(SwingDesigner designer, Component component) {
        JComponent jcomponent=getJComponent(component);
        ComponentAdapter adapter = (ComponentAdapter) jcomponent.getClientProperty("component.adapter");
        if (adapter == null) {
            adapter = createComponentAdapter(designer, component);
            jcomponent.putClientProperty("component.adapter", adapter);
        }
        return adapter;
    }

    /**
     * 获取layoutClass布局管理器对应的布局适配器
     */
    public static LayoutAdapter getLayoutAdapter(SwingDesigner designer, Container container) {
        LayoutManager layout = container.getLayout();
        Class<? extends LayoutManager> layoutClass = layout.getClass();
        Class parentClass = searchAdapterClass(layoutAdapters, layoutClass, LayoutManager.class);
        if (parentClass == null || parentClass == LayoutManager.class) {
            return new DefaultLayoutAdapter(designer, container);
        } else {
            Class<? extends LayoutAdapter> adapterClass = layoutAdapters.get(parentClass);
            try {
                Constructor<? extends LayoutAdapter> cons = adapterClass.getConstructor(SwingDesigner.class, Container.class);
                LayoutAdapter adapter = cons.newInstance(designer, container);
                return adapter;
            } catch (Exception e) {
                e.printStackTrace();
                LayoutAdapter adapter = new DefaultLayoutAdapter(designer, container);
                return adapter;
            }
        }
    }

    /**
     * 获取layoutClass布局管理器对应的布局适配器
     */
    public static BorderAdapter getBorderAdapter(Border border) {
        Class<? extends Border> borderClass = border.getClass();
        Class parentClass = searchAdapterClass(borderAdapters, borderClass, Border.class);
        if (parentClass == null || parentClass == Border.class) {
            return new DefaultBorderAdapter(border);
        } else {
            Class<? extends BorderAdapter> adapterClass = borderAdapters.get(parentClass);
            try {
                Constructor<? extends BorderAdapter> cons = adapterClass.getConstructor(Border.class);
                BorderAdapter adapter = cons.newInstance(border);
                return adapter;
            } catch (Exception e) {
                e.printStackTrace();
                BorderAdapter adapter = new DefaultBorderAdapter(border);
                return adapter;
            }
        }
    }
}