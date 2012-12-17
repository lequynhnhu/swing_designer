/*
 * GenericBeanInfo.java
 *
 * Created on 2007年8月12日, 下午8:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beaninfo;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.items.ItemProvider;
import dyno.swing.designer.properties.wrappers.ColorWrapper;
import dyno.swing.designer.properties.wrappers.Decoder;
import dyno.swing.designer.properties.wrappers.FontWrapper;
import dyno.swing.designer.properties.wrappers.InsetsWrapper;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.primitive.BooleanWrapper;
import dyno.swing.designer.properties.wrappers.primitive.ByteWrapper;
import dyno.swing.designer.properties.wrappers.primitive.CharWrapper;
import dyno.swing.designer.properties.wrappers.primitive.DoubleWrapper;
import dyno.swing.designer.properties.wrappers.primitive.FloatWrapper;
import dyno.swing.designer.properties.wrappers.primitive.IntegerWrapper;
import dyno.swing.designer.properties.wrappers.primitive.LongWrapper;
import dyno.swing.designer.properties.wrappers.primitive.ShortWrapper;
import dyno.swing.designer.properties.wrappers.primitive.StringWrapper;
import java.awt.Color;
import java.awt.Font;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.Image;
import java.awt.Insets;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 *
 * @author William Chen
 */
public abstract class GenericBeanInfo extends SimpleBeanInfo {

    private static String[] lnf_names = {"Windows", "Metal", "JGoodies Plastic 3D", "JGoodies Plastic XP"};
    private static HashMap<Class, Decoder> decoders;
    private static HashMap<String, BeanInfoDesc> beanInfoDescs;
    static {
        initBeanInfoDescs();
        initTypeDecoders();
    }

    private static void initTypeDecoders() {
        decoders = new HashMap<Class, Decoder>();
        decoders.put(byte.class, new ByteWrapper());
        decoders.put(short.class, new ShortWrapper());
        decoders.put(char.class, new CharWrapper());
        decoders.put(int.class, new IntegerWrapper());
        decoders.put(long.class, new LongWrapper());
        decoders.put(float.class, new FloatWrapper());
        decoders.put(double.class, new DoubleWrapper());
        decoders.put(boolean.class, new BooleanWrapper());
        decoders.put(String.class, new StringWrapper());
        decoders.put(Color.class, new ColorWrapper());
        decoders.put(Font.class, new FontWrapper());
        decoders.put(Insets.class, new InsetsWrapper());
    }
    private Class beanClass;
    private boolean isabstract;
    private BeanDescriptor beanDescriptor;
    private Image icon16;
    private Image icon32;

    /** Creates a new instance of JButtonBeanInfo */
    public GenericBeanInfo() {
        try {
            beanClass = getBeanClass();
            beanDescriptor = new BeanDescriptor(beanClass);
            initBeanProperties();
            String isAbstract = (String) beanDescriptor.getValue("abstract");
            isabstract = isAbstract != null && isAbstract.equals("true");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseBeaninfo(Element eBeaninfo) {
        String classname = eBeaninfo.getAttribute("class");
        String resourceName = eBeaninfo.getAttribute("resource");
        ArrayList<PropertyDesc> array = new ArrayList<PropertyDesc>();
        NodeList list = eBeaninfo.getElementsByTagName("property");

        if ((list != null) && (list.getLength() > 0)) {
            for (int i = 0; i < list.getLength(); i++) {
                Element eProperty = (Element) list.item(i);
                PropertyDesc prop = new PropertyDesc();
                prop.setPropertyName(eProperty.getAttribute("name"));

                String pcn = eProperty.getAttribute("editor");

                if ((pcn != null) && (pcn.trim().length() > 0)) {
                    prop.setPropertyEditor(pcn.trim());
                }


                NodeList defaultList = eProperty.getElementsByTagName("default");
                if ((defaultList != null) && (defaultList.getLength() > 0)) {
                    ArrayList<Element> defaults = new ArrayList<Element>();
                    for (int j = 0; j < defaultList.getLength(); j++) {
                        Element eDefault = (Element) defaultList.item(j);
                        defaults.add(eDefault);
                    }
                    prop.setDefaults(defaults);
                }

                Properties properties = parseProperties(eProperty);
                prop.setProperties(properties);
                array.add(prop);
            }
        }

        PropertyDesc[] props = array.toArray(new PropertyDesc[0]);
        BeanInfoDesc desc = new BeanInfoDesc();
        Properties properties = parseProperties(eBeaninfo);
        desc.setClassname(classname);
        desc.setProperties(props);
        desc.setResourceName(resourceName);
        desc.setBeanProperties(properties);
        beanInfoDescs.put(classname, desc);
    }

    private static void parseBeanInfos(Element eBeaninfos) {
        NodeList list = eBeaninfos.getElementsByTagName("beaninfo");

        if ((list != null) && (list.getLength() > 0)) {
            for (int i = 0; i < list.getLength(); i++) {
                Element eBeaninfo = (Element) list.item(i);
                parseBeaninfo(eBeaninfo);
            }
        }
    }

    private static void initBeanInfoDescs() {
        try {
            beanInfoDescs = new HashMap<String, BeanInfoDesc>();

            InputStream input = GenericBeanInfo.class.getResourceAsStream("beaninfo.xml");
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            Element eBeaninfos = document.getDocumentElement();
            parseBeanInfos(eBeaninfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BeanInfoDesc getBeanInfoDesc(Class beanClass) {
        BeanInfoDesc biDesc = beanInfoDescs.get(beanClass.getName());
        if (biDesc == null) {
            Class beanInfoClass = getClass();
            String bicName = beanInfoClass.getName();
            String name = bicName;
            int dot = bicName.lastIndexOf('.');
            if (dot != -1) {
                name = bicName.substring(dot + 1);
            }
            try {
                InputStream bis = beanInfoClass.getResourceAsStream(name + ".xml");
                if (bis != null) {
                    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
                    Element eBeaninfos = document.getDocumentElement();
                    parseBeanInfos(eBeaninfos);
                    biDesc = beanInfoDescs.get(beanClass.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return biDesc;
    }

    private void initBeanProperties() {
        BeanInfoDesc desc = getBeanInfoDesc(beanClass);

        if (desc != null) {
            Properties props = desc.getBeanProperties();
            Enumeration names = props.propertyNames();

            while (names.hasMoreElements()) {
                String key = (String) names.nextElement();
                beanDescriptor.setValue(key, props.get(key));
            }
        }
    }

    public BeanDescriptor getBeanDescriptor() {
        return beanDescriptor;
    }

    public Image getIcon(int iconKind) {
        if ((iconKind == ICON_COLOR_16x16) || (iconKind == ICON_MONO_16x16)) {
            if (icon16 == null) {
                BeanInfoDesc desc = getBeanInfoDesc(beanClass);

                if (desc != null) {
                    String resource = "resources/" + desc.getResourceName() + "_16.png";
                    icon16 = super.loadImage(resource);
                }
            }

            return icon16;
        } else {
            if (icon32 == null) {
                BeanInfoDesc desc = getBeanInfoDesc(beanClass);

                if (desc != null) {
                    String resource = "resources/" + desc.getResourceName() + "_32.png";
                    icon32 = super.loadImage(resource);
                }
            }

            return icon32;
        }
    }

    private PropertyDescriptor cloneProperty(PropertyDescriptor source) {
        try {
            String propertyName = source.getName();
            PropertyDescriptor prop = new PropertyDescriptor(propertyName, beanClass);

            Class clazz = source.getPropertyEditorClass();
            if (clazz != null) {
                prop.setPropertyEditorClass(clazz);
            }
            String desc = source.getShortDescription();
            if (!Util.isStringNull(desc)) {
                prop.setShortDescription(desc);
            }
            Enumeration names = source.attributeNames();

            while (names.hasMoreElements()) {
                String key = (String) names.nextElement();
                Object value = source.getValue(key);
                if (value != null) {
                    prop.setValue(key, value);
                }
            }

            prop.setValue("bean-class", beanDescriptor.getBeanClass());
            return prop;
        } catch (Exception ex) {
            return null;
        }
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        if (property_descriptor == null) {
            try {
                ArrayList<PropertyDescriptor> properties = getDeclaredProperties();

                if (properties == null) {
                    return null;
                }

                Class superClass = beanDescriptor.getBeanClass().getSuperclass();
                BeanInfo superBeanInfo = Introspector.getBeanInfo(superClass);
                PropertyDescriptor[] superPropertyDescriptors = superBeanInfo.getPropertyDescriptors();
                ArrayList<PropertyDescriptor> additional = new ArrayList<PropertyDescriptor>();
                if (superPropertyDescriptors != null) {
                    for (PropertyDescriptor propDesc : superPropertyDescriptors) {
                        if (!existsIn(propDesc, properties)) {
                            PropertyDescriptor clone = cloneProperty(propDesc);
                            additional.add(clone);
                        }
                    }
                }
                properties.addAll(additional);
                property_descriptor = properties.toArray(new PropertyDescriptor[0]);
                if (!isabstract) 
                    initDefaultValues();
            } catch (IntrospectionException ex) {
                ex.printStackTrace();
            }
        }
        return property_descriptor;
    }

    private void initDefaultValues() {
        for (PropertyDescriptor property : property_descriptor) {

            HashMap<String, DefaultValue> defaults = new HashMap<String, DefaultValue>();
            for (String name : lnf_names) {
                defaults.put(name, new DefaultValue());
            }
            property.setValue("default-value", defaults);

            ArrayList<Element> eDefaults = (ArrayList<Element>) property.getValue("default");
            if (eDefaults == null) {
                continue;
            }
            for (Element eDefault : eDefaults) {
                String lnf = eDefault.getAttribute("lnf");
                DefaultValue dValue = defaults.get(lnf);

                String sValue = eDefault.getAttribute("value");
                String itemString = (String) property.getValue("items");
                if (!Util.isStringNull(itemString)) {
                    try {
                        ItemProvider provider = (ItemProvider) Beans.instantiate(this.getClass().getClassLoader(),
                                itemString);
                        ItemWrapper wrapper = new ItemWrapper(provider);
                        Object value = wrapper.decode(sValue);
                        dValue.setValue(value);
                    } catch (Exception ex) {
                    }
                } else {
                    Class property_class = property.getPropertyType();
                    Decoder decoder = decoders.get(property_class);
                    if (decoder != null) {
                        Object value = decoder.decode(sValue);
                        dValue.setValue(value);
                    } else if (property_class == String.class) {
                        dValue.setValue(sValue);
                    } else if (sValue.equals("null")) {
                        dValue.setValue(null);
                    }
                }
            }
        }
    }

    private boolean existsIn(PropertyDescriptor prop, ArrayList<PropertyDescriptor> props) {
        for (PropertyDescriptor desc : props) {
            if (prop.getName().equals(desc.getName())) {
                return true;
            }

        }
        return false;
    }
    private PropertyDescriptor[] property_descriptor;

    protected ArrayList<PropertyDescriptor> getDeclaredProperties() {
        PropertyDesc[] propertyNames = getDeclaredPropertyNames();

        if (propertyNames == null) {
            return null;
        }

        ArrayList<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>();

        for (PropertyDesc prop_desc : propertyNames) {
            String propertyName = prop_desc.getPropertyName();
            String pEditorClassname = prop_desc.getPropertyEditor();
            Class beanClass = beanDescriptor.getBeanClass();

            try {
                PropertyDescriptor prop = new PropertyDescriptor(propertyName, beanClass);
                prop.setValue("bean-class", beanClass);
                if (pEditorClassname != null) {
                    setPropertyEditorClass(pEditorClassname, prop);
                }

                String description = prop_desc.getProperties().getProperty("description");
                if (!Util.isStringNull(description)) {
                    prop.setShortDescription(description);
                }

                ArrayList<Element> defaults = prop_desc.getDefaults();
                if (defaults != null) {
                    prop.setValue("default", defaults);
                }

                Properties props = prop_desc.getProperties();
                Enumeration names = props.propertyNames();

                while (names.hasMoreElements()) {
                    String key = (String) names.nextElement();
                    prop.setValue(key, props.get(key));
                }

                properties.add(prop);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return properties;
    }

    private void setPropertyEditorClass(String pEditorClassname, PropertyDescriptor prop) {
        try {
            Class editorClass = Class.forName(pEditorClassname);
            prop.setPropertyEditorClass(editorClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected abstract Class getBeanClass();

    private PropertyDesc[] getDeclaredPropertyNames() {
        BeanInfoDesc desc = getBeanInfoDesc(beanClass);

        if (desc == null) {
            return null;
        }

        return desc.getProperties();
    }

    private static Properties parseProperties(Element eProperty) {
        NamedNodeMap map = eProperty.getAttributes();
        Properties properties = new Properties();

        if ((map != null) && (map.getLength() > 0)) {
            for (int i = 0; i <
                    map.getLength(); i++) {
                Node node = map.item(i);
                properties.put(node.getNodeName(), node.getNodeValue());
            }

        }

        return properties;
    }

    private static class PropertyDesc {

        private String propertyName;
        private String propertyEditor;
        private Properties properties;
        private ArrayList<Element> defaults;

        public PropertyDesc() {
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyEditor() {
            return propertyEditor;
        }

        public void setPropertyEditor(String propertyEditor) {
            this.propertyEditor = propertyEditor;
        }

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }

        public ArrayList<Element> getDefaults() {
            return defaults;
        }

        public void setDefaults(ArrayList<Element> defaults) {
            this.defaults = defaults;
        }
    }

    private static class BeanInfoDesc {

        private String classname;
        private String resourceName;
        private PropertyDesc[] properties;
        private Properties beanProperties;

        public BeanInfoDesc() {
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public PropertyDesc[] getProperties() {
            return properties;
        }

        public void setProperties(PropertyDesc[] propertyNames) {
            this.properties = propertyNames;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public Properties getBeanProperties() {
            return beanProperties;
        }

        public void setBeanProperties(Properties beanProperties) {
            this.beanProperties = beanProperties;
        }
    }
}