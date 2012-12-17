/*
 * Adapter.java
 *
 * Created on 2007��5��2��, ����11:28
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
 * ���������࣬Ϊ�������������������ֺͲ������������ṩӳ���ϵ������
 * ��������������������չ�㡣�����Ҫ��չ�µ�������ͻ��߲��ֹ�������������Ϊ��
 * ֻ��Ҫ��components.properties��layouts.properties����µ�ӳ���ϵ�Ϳ����ˡ�
 *
 * @author William Chen
 */
public class AdapterBus {

    //�����<-->����������࣬��component.properties�ж��壬��ӳ���Ϊ����������ṩʵ��������
    private static HashMap<Class<? extends Component>, Class<? extends ComponentAdapter>> componentAdapterClasses;
    //�����<-->���������������һ��ӳ���ϵ����
    //private static HashMap<Class<?extends Component>, ComponentAdapter> componentAdapters;
    //������<-->��������������layouts.properties�ж���
    private static HashMap<Class<? extends LayoutManager>, Class<? extends LayoutAdapter>> layoutAdapters;
    private static HashMap<Class<? extends Border>, Class<? extends BorderAdapter>> borderAdapters;
    static {
        //��ʼ��
        initComponentAdapters();
        initLayoutAdapters();
        initBorderAdapters();
    }

    /**
     * ��ʼ�����������
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
     * ��ʼ������������
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
     * ��ȡ���������componentClass��Ӧ������������������ʼӳ�����û�и���������
     * ����������丸���Ӧ����������ֱ�����ҵ�Component��Ϊֹ���������û�в��ҵ���
     * ��ʹ��ȱʡ�������������DefaultComponentAdapter
     *
     * @return �����������Ӧ���������������
     */
    public static ComponentAdapter createComponentAdapter(SwingDesigner designer, Component component) {
        Class<? extends Component> componentClass = component.getClass();
        ComponentAdapter adapter;

        //��ӳ����û�У�������丸�ࡢ������ֱ��Component���߿�
        Class parentClass = searchAdapterClass(componentAdapterClasses, componentClass, Component.class);

        if ((parentClass == null) || (parentClass == Component.class)) {
            //���û�в��ҵ���ʹ��ȱʡ�����������
            adapter = new DefaultComponentAdapter(designer, component);
        } else {
            try {
                //�����ˣ���Ϊʵ����һ��������
                Class<? extends ComponentAdapter> adapterClass = componentAdapterClasses.get(parentClass);
                Constructor<? extends ComponentAdapter> cons = adapterClass.getConstructor(SwingDesigner.class, Component.class);
                adapter = cons.newInstance(designer, component);
            } catch (Exception ex) {
                ex.printStackTrace();
                //ʵ����ʧ�ܣ���תΪȱʡ�����������
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
     * ��ȡ���������componentClass��Ӧ������������������ʼӳ�����û�и���������
     * ����������丸���Ӧ����������ֱ�����ҵ�Component��Ϊֹ���������û�в��ҵ���
     * ��ʹ��ȱʡ�������������DefaultComponentAdapter
     *
     * @return �����������Ӧ���������������
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
     * ��ȡlayoutClass���ֹ�������Ӧ�Ĳ���������
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
     * ��ȡlayoutClass���ֹ�������Ӧ�Ĳ���������
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