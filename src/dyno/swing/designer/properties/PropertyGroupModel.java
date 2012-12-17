/*
 * DefaultBeanPropertyModel.java
 *
 * Created on 2007??8??13??, ????12:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties;

import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.editors.ExtendedPropertyEditor;
import dyno.swing.designer.properties.editors.accessibles.AccessiblePropertyEditor;
import dyno.swing.designer.properties.editors.accessibles.BaseAccessibleEditor;
import dyno.swing.designer.properties.editors.ItemCellEditor;
import dyno.swing.designer.properties.renderer.EncoderCellRenderer;
import dyno.swing.designer.properties.items.ItemProvider;
import dyno.swing.designer.properties.wrappers.Decoder;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.Encoder;
import java.awt.Component;
import java.beans.Beans;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author William Chen
 */
public class PropertyGroupModel implements GroupModel, Comparable<PropertyGroupModel> {

    protected Object bean;
    protected PropertyDescriptor[] properties;
    protected TableCellRenderer[] renderers;
    protected PropertyCellEditor[] editors;
    protected String groupName;
    protected SwingDesigner designer;

    /** Creates a new instance of DefaultBeanPropertyModel */
    public PropertyGroupModel(String name, Object bean, SwingDesigner designer, PropertyDescriptor[] props) {
        this.groupName = name;
        this.bean = bean;
        this.designer = designer;
        this.properties = props; //beanInfo.getPropertyDescriptors();
        renderers = new TableCellRenderer[properties.length];
        editors = new PropertyCellEditor[properties.length];
    }

    public int getRowCount() {
        return properties.length;
    }

    public TableCellRenderer getRenderer(int row) {
        if (renderers[row] == null) {
            initRenderer(row);
        }
        if (renderers[row] != null && renderers[row] instanceof PropertyContext) {
            PropertyContext context = (PropertyContext) renderers[row];
            context.setBean(bean);
            context.setSwingDesigner(designer);
        }
        return renderers[row];
    }

    public TableCellEditor getEditor(int row) {
        if (editors[row] == null) {
            initEditor(row);
        }

        if (editors[row] != null) {
            editors[row].setBean(bean);
            editors[row].setSwingDesigner(designer);
        }

        return editors[row];
    }

    private boolean isComponentNameProperty(int row) {
        return properties[row].getName().equals("name") && bean instanceof Component;
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            return properties[row].getName();
        }
        if (isComponentNameProperty(row)) {
            return Util.getComponentName((Component) bean);
        } else {
            try {
                Method m = properties[row].getReadMethod();
                return m.invoke(bean);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        }

        try {
            Method m = properties[row].getWriteMethod();
            m.invoke(bean, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEditable(int row) {
        if (bean instanceof Component) {
            if (properties[row].getName().equals("name")) {
                boolean isRoot = Util.isRootComponent((Component) bean);
                return !isRoot;
            }
        }
        boolean writable = properties[row].getWriteMethod() != null;
        return writable;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    private void initRenderer(int row) {
        try {
            String sRenderer = (String) properties[row].getValue(
                    "renderer");

            if (!Util.isStringNull(sRenderer)) {
                renderers[row] = (TableCellRenderer) Beans.instantiate(this.getClass()
                                                                           .getClassLoader(),
                        sRenderer);
            } else {
                String encoderString = (String) properties[row].getValue(
                    "encoder");
                if (!Util.isStringNull(encoderString)) {
                    Encoder wrapper = (Encoder) Beans.instantiate(this.getClass()
                                                                           .getClassLoader(),
                        encoderString);
                    renderers[row] = new EncoderCellRenderer(wrapper);
                } else {
                    String itemString = (String) properties[row].getValue(
                    "items");
                    if (!Util.isStringNull(itemString)) {
                        ItemProvider provider = (ItemProvider) Beans.instantiate(this.getClass()
                                                                           .getClassLoader(),
                        itemString);
                        ItemWrapper wrapper = new ItemWrapper(provider);
                        renderers[row] = new EncoderCellRenderer(wrapper);
                    } else {
                        Class propType = properties[row].getPropertyType();
                        Class<? extends TableCellRenderer> rendererClass = Util.getTableCellRendererClass(propType);

                        if (rendererClass != null) {
                            renderers[row] = rendererClass.newInstance();
                        } else {
                            Class<? extends PropertyEditor> editorClass = (Class<PropertyEditor>) properties[row].getPropertyEditorClass();

                            if (editorClass == null) {
                                editorClass = Util.getPropertyEditorClass(propType);
                            }

                            if (editorClass == null) {
                                renderers[row] = new DefaultTableCellRenderer();
                            } else {
                                PropertyEditor editor = editorClass.newInstance();
                                renderers[row] = new PropertyCellRenderer(editor);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEditor(int row) {
        try {
            Class<? extends PropertyEditor> editorClass = (Class<PropertyEditor>) properties[row].getPropertyEditorClass();
            Object defaultValue=Util.getDefaultValue(designer.getDesignLnf(), properties[row]);
            if (editorClass == null) {
                String encoderString = (String) properties[row].getValue("encoder");
                String decoderString = (String) properties[row].getValue("decoder");
                if (!(Util.isStringNull(encoderString) || Util.isStringNull(decoderString))) {
                    Decoder decoder = (Decoder) Beans.instantiate(this.getClass()
                                                                           .getClassLoader(),
                        decoderString);
                    Encoder encoder = (Encoder) Beans.instantiate(this.getClass()
                                                                           .getClassLoader(),
                        encoderString);
                    BaseAccessibleEditor bae = new BaseAccessibleEditor(encoder, decoder, false);
                    AccessiblePropertyEditor ape = new AccessiblePropertyEditor(bae);
                    ape.setDefaultValue(defaultValue);
                    editors[row] = new PropertyCellEditor(properties[row], ape);
                    return;
                } else {
                    String itemString = (String) properties[row].getValue(
                    "items");
                    if (!Util.isStringNull(itemString)) {
                        ItemProvider provider = (ItemProvider) Beans.instantiate(this.getClass()
                                                                           .getClassLoader(),
                        itemString);
                        ItemCellEditor ice=new ItemCellEditor(provider);
                        ice.setDefaultValue(defaultValue);
                        editors[row] = new PropertyCellEditor(properties[row], ice);
                        return;
                    } else {
                        Class propType = properties[row].getPropertyType();
                        editorClass = Util.getPropertyEditorClass(propType);
                    }
                }
            }

            if (editorClass != null) {
                PropertyEditor editor = editorClass.newInstance();
                if(editor instanceof ExtendedPropertyEditor)
                    ((ExtendedPropertyEditor)editor).setDefaultValue(defaultValue);
                editors[row] = new PropertyCellEditor(properties[row], editor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public int compareTo(PropertyGroupModel gm) {
        int firstIndex = PROPERTIES.indexOf(groupName);
        int lastIndex = PROPERTIES.indexOf(gm.getGroupName());
        if (firstIndex < lastIndex) {
            return -1;
        } else if (firstIndex == lastIndex) {
            return 0;
        } else {
            return 1;
        }
    }
    private static ArrayList<String> PROPERTIES = new ArrayList<String>();
    static {
        PROPERTIES.add("Properties");
        PROPERTIES.add("Others");
    }

    public boolean isValueSet(int row) {
        return Util.isPropertyChanged(designer.getDesignLnf(), bean, properties[row]);
    }

    public boolean restoreDefaultValue(int row) {
        Object default_value=Util.getDefaultValue(designer.getDesignLnf(), properties[row]);
        return setValue(default_value, row, 1);
    }

    public String getPropertyDescription(int row) {
        return properties[row].getShortDescription();
    }
}