/*
 * AbstractComponentAdapter.java
 *
 * Created on 2007年5月2日, 下午11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.DesignerEditor;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.actions.AbstractContextAction;
import dyno.swing.designer.beans.actions.ActionCategory;
import dyno.swing.designer.beans.actions.ChangeVarName;
import dyno.swing.designer.beans.actions.InvokeEditorAction;
import dyno.swing.designer.beans.actions.SetLnfAction;
import dyno.swing.designer.beans.editors.TextEditor;
import dyno.swing.designer.properties.EventGroupModel;
import dyno.swing.designer.properties.PropertyGroupModel;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;


/**
 *
 * @author William Chen
 */
public abstract class AbstractComponentAdapter implements ComponentAdapter, Constants {

    static String[] skipped_property_list = {"name", "bounds", "model", "actionCommand", "actionMap"};
    static HashMap<String, String> skipped_properties = new HashMap<String, String>();
    static {
        for (String name : skipped_property_list) {
            skipped_properties.put(name, name);
        }
    }
    private static HashMap<Class<? extends Component>, Dimension> INITIAL_SIZE;
    static {
        INITIAL_SIZE = new HashMap<Class<? extends Component>, Dimension>();
        INITIAL_SIZE.put(JPanel.class, new Dimension(80, 80));
        INITIAL_SIZE.put(JTable.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JTabbedPane.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JScrollPane.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JTextPane.class, new Dimension(80, 80));
        INITIAL_SIZE.put(JTextArea.class, new Dimension(80, 80));
        INITIAL_SIZE.put(JEditorPane.class, new Dimension(80, 80));
        INITIAL_SIZE.put(JSplitPane.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JSeparator.class, new Dimension(80, 4));
        INITIAL_SIZE.put(JToolBar.class, new Dimension(120, 22));
        INITIAL_SIZE.put(JList.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JComboBox.class, new Dimension(80, 22));
        INITIAL_SIZE.put(JSpinner.class, new Dimension(80, 22));
        INITIAL_SIZE.put(JDesktopPane.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JLayeredPane.class, new Dimension(160, 160));
        INITIAL_SIZE.put(JInternalFrame.class, new Dimension(100, 100));
    }
    protected BeanInfo beanInfo;
    protected SwingDesigner designer;
    protected ArrayList<GroupModel> groupModels;
    protected DesignerEditor designerEditor;
    private PropertyDescriptor textProperty;
    protected Component component;

    /** Creates a new instance of AbstractComponentAdapter */
    public AbstractComponentAdapter(SwingDesigner designer, Component c) {
        this.designer = designer;
        this.component = c;
        try {
            beanInfo = Introspector.getBeanInfo(c.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        initText();
        Dimension initialSize = getInitialSize();
        component.setSize(initialSize);
        if (component instanceof Container) {
            Util.layoutContainer((Container) component);
        }
    }

    private void initContextMenu(Action[] actions, JPopupMenu popupMenu) {
        for (Action action : actions) {
            if (action instanceof ActionCategory) {
                popupMenu.addSeparator();
                Action[] subs = ((ActionCategory) action).getSubActions();
                initContextMenu(subs, popupMenu);
            } else {
                popupMenu.add(action);
            }
        }
    }

    private void initText() {
        String text = Util.getComponentName(component);
        PropertyDescriptor[] des = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : des) {
            if (property.getName().equals("text")) {
                try {
                    property.getWriteMethod().invoke(component, text);
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public BeanInfo getBeanInfo() {
        return beanInfo;
    }

    public Image getIcon(int iconKind) {
        Class clazz = beanInfo.getBeanDescriptor().getBeanClass();
        clazz = clazz.getSuperclass();
        BeanInfo binfo = beanInfo;
        try {
            while (clazz != null && binfo.getIcon(iconKind) == null) {
                binfo = Introspector.getBeanInfo(clazz);
                clazz = clazz.getSuperclass();
            }
            if (clazz == null) {
                return null;
            }
            return binfo.getIcon(iconKind);
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Component clone() {
        try {
            Class<? extends Component> comp_class = (Class<? extends Component>) component.getClass();
            Component clone = comp_class.newInstance();

            clone.setBounds(component.getBounds());
            clone.setName(component.getName());
            cloneProperty(component, clone);

            return clone;
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }

    private void cloneProperty(Component comp, Component clone) {
        try {
            Class clazz = comp.getClass();
            BeanInfo bInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] properties = bInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : properties) {
                if (skipped_properties.get(property.getName()) == null) {
                    Method read = property.getReadMethod();
                    Method write = property.getWriteMethod();

                    if ((read != null) && (write != null)) {
                        Object comp_value = read.invoke(comp);
                        Object clone_value = read.invoke(clone);

                        if (isChanged(clone_value, comp_value)) {
                            write.invoke(clone, comp_value);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean isChanged(Object clone_value, Object comp_value) {
        boolean set = false;

        if (comp_value == null) {
            if (clone_value == null) {
                set = false;
            } else {
                set = true;
            }
        } else {
            if (clone_value == null) {
                set = true;
            } else {
                if (comp_value.equals(clone_value)) {
                    set = false;
                } else {
                    set = true;
                }
            }
        }

        return set;
    }

    public void paintComponentMascot(Graphics g) {
        component.paint(g);
    }

    public Dimension getInitialSize() {
        Dimension dim = INITIAL_SIZE.get(component.getClass());

        if (dim == null) {
            return component.getPreferredSize();
        } else {
            return dim;
        }
    }

    protected void initSubMenu(JMenu menu, Action[] actions, Component current) {
        for (Action action : actions) {
            if (action == null) {
                menu.addSeparator();
            } else {
                if (action instanceof AbstractContextAction) {
                    ((AbstractContextAction) action).setComponent(current);
                }
                if (action instanceof ActionCategory) {
                    Action[] subactions = ((ActionCategory) action).getSubActions();
                    if (subactions == null) {
                        menu.add(action);
                    } else {
                        JMenu menuItem = new JMenu(action);
                        initSubMenu(menuItem, subactions, current);
                        menu.add(menuItem);
                    }
                } else {
                    menu.add(action);
                }
            }
        }
    }

    public JPopupMenu getContextPopupMenu(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();
        
        if(setLnfAction == null){
            setLnfAction = new SetLnfAction(designer);
        }
        
        JMenu lnfmenu=new JMenu(setLnfAction);
        Action[]subactions = setLnfAction.getSubActions();
        for(Action action : subactions){
            lnfmenu.add(action);
        }
        popupMenu.add(lnfmenu);
        
        if (changeVarNameAction == null) {
            changeVarNameAction = new ChangeVarName(designer);
        }
        changeVarNameAction.setEnabled(!Util.isRootComponent(component));
        changeVarNameAction.setComponent(component);
        if(invokeEditorAction==null){
            invokeEditorAction = new InvokeEditorAction(designer);
        }
        invokeEditorAction.setComponent(component);
        invokeEditorAction.setEnabled(this.getDesignerEditor(e.getX(), e.getY())!=null);
        invokeEditorAction.setMouseEvent(e);
        
        popupMenu.add(changeVarNameAction);
        popupMenu.add(invokeEditorAction);
        
        Action[]actions = designer.getActions();
        initContextMenu(actions, popupMenu);
        return popupMenu;
    }
    private SetLnfAction setLnfAction;
    private ChangeVarName changeVarNameAction;
    private InvokeEditorAction invokeEditorAction;

    private static String PROPERTY_CATEGORY = "category";
    private static String DEFAULT_GROUP_NAME = "Properties";

    public ArrayList<GroupModel> getBeanPropertyModel() {
        if (groupModels == null) {
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            HashMap<String, ArrayList<PropertyDescriptor>> maps = new HashMap<String, ArrayList<PropertyDescriptor>>();
            ArrayList<String> groupNames = new ArrayList<String>();
            for (PropertyDescriptor property : properties) {
                String groupName = (String) property.getValue(PROPERTY_CATEGORY);
                if (Util.isStringNull(groupName)) {
                    groupName = DEFAULT_GROUP_NAME;
                }
                ArrayList<PropertyDescriptor> groupProperties = maps.get(groupName);
                if (groupProperties == null) {
                    groupProperties = new ArrayList<PropertyDescriptor>();
                    maps.put(groupName, groupProperties);
                    groupNames.add(groupName);
                }
                groupProperties.add(property);
            }
            ArrayList<PropertyGroupModel> groups = new ArrayList<PropertyGroupModel>();
            for (String groupName : groupNames) {
                ArrayList<PropertyDescriptor> groupProperties = maps.get(groupName);
                PropertyDescriptor[] prop_array = groupProperties.toArray(new PropertyDescriptor[0]);
                PropertyGroupModel groupModel = new PropertyGroupModel(groupName, component, designer, prop_array);
                groups.add(groupModel);
            }
            Collections.sort(groups);
            groupModels = new ArrayList<GroupModel>();
            groupModels.addAll(groups);
        }
        return groupModels;
    }

    public ArrayList<GroupModel> getEventPropertyModel() {
        if (eventGroups == null) {
            EventSetDescriptor[] events = beanInfo.getEventSetDescriptors();
            ArrayList<EventGroupModel> groups = new ArrayList<EventGroupModel>();
            for (EventSetDescriptor event : events) {
                groups.add(new EventGroupModel(designer, component, event));
            }
            Collections.sort(groups);
            eventGroups=new ArrayList<GroupModel>();
            eventGroups.addAll(groups);
        }
        return eventGroups;
    }
    private ArrayList<GroupModel> eventGroups;

    private boolean hasDesignerEditor() {
        String editor = (String) beanInfo.getBeanDescriptor()
                                         .getValue("designer-editor");

        return (editor != null) && (editor.trim().length() > 0);
    }

    private DesignerEditor createDesignerEditor() {
        try {
            String editor = (String) beanInfo.getBeanDescriptor()
                                             .getValue("designer-editor");

            return (DesignerEditor) Beans.instantiate(getClass().getClassLoader(),
                editor);
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }

    public DesignerEditor getDesignerEditor(int x, int y) {
        if (isTextBound()) {
            if (designerEditor == null) {
                designerEditor = new TextEditor();
            }
        } else if (hasDesignerEditor()) {
            if (designerEditor == null) {
                designerEditor = createDesignerEditor();
            }
        }

        return designerEditor;
    }

    private boolean isTextBound() {
        if (textProperty != null) {
            return true;
        }

        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : properties) {
            if (property.getName().equals("text")) {
                textProperty = property;

                return true;
            }
        }

        return false;
    }

    public void validateBeanValue(Object value) throws ValidationException {
    }

    public void setBeanValue(Object value) {
        if (textProperty != null) {
            Method m = textProperty.getWriteMethod();

            try {
                if (m != null) {
                    m.invoke(component, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBeanValue() {
        if (textProperty != null) {
            Method m = textProperty.getReadMethod();

            try {
                return m.invoke(component);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Rectangle getEditorBounds(int x, int y) {
        Rectangle bounds = component.getBounds();
        bounds.x = 1;
        bounds.y = 1;
        bounds.width -= 1;
        bounds.height -= 1;
        return bounds;
    }
    public boolean componentClicked(MouseEvent e) {
        Component parent=component;
        while(!(parent instanceof JInternalFrame || parent == null))
            parent=parent.getParent();
        if(parent != null){
            ((JInternalFrame)parent).toFront();
            return false;
        }
        return true;
    }    
}