/*
 * CodeSeed.java
 *
 * Created on 2007-9-6, 22:01:04
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.code;

import com.sun.tools.javac.Main;
import dyno.swing.beans.layouts.AbsoluteLayout;
import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.items.ItemProvider;
import dyno.swing.designer.properties.types.EventHandler;
import dyno.swing.designer.properties.types.Item;
import dyno.swing.designer.properties.wrappers.ItemWrapper;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.EventSetDescriptor;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

/**
 *
 * @author William Chen
 */
public class CodeSeed {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                    public void run() {
                        cleanCacheDir();
                    }
                }));
    }
    private String class_name;
    private String packager;
    private String source_code;
    private CodeBlock current_code;
    private SwingDesigner designer;
    private Prototype prototype;
    private Item guitype;

    public void setPackager(String packager) {
        this.packager = packager;
    }

    private String generatePrototype(PrintWriter writer) {
        prototype = new Prototype();
        for (CodeBlock block : current_code.getCodes()) {
            switch (block.getType()) {
                case source:
                    break;
                case code:
                    break;
                case declare:
                    prototype.setDeclaration(block);
                    break;
                case init:
                    prototype.setInit(block);
                    break;
                case get:
                    prototype.addGet(block);
                    break;
                case custom:
                    break;
                case listener:
                    break;
                case event:
                    break;
                case packager:
                    prototype.setPackager(block);
                    break;
                case importer:
                    prototype.addImporter(block);
                    break;
            }
        }
        return prototype.generatePrototypeSourceCode(writer, guitype);
    }

    public CodeSeed(SwingDesigner designer) {
        this.designer = designer;
    }

    public void setClassName(String class_name) {
        this.class_name = class_name;
    }

    private Class compileAndLoad(String className, File srcFile, PrintWriter pw) {
        try {
            if (pw != null) {
                Main.compile(new String[]{srcFile.getCanonicalPath()}, pw);
            } else {
                Main.compile(new String[]{srcFile.getCanonicalPath()});
            }
            PrototypeClassLoader loader = new PrototypeClassLoader();
            return loader.loadClass(className);
        } catch (Exception e) {
            e.printStackTrace(pw);
        }
        return null;
    }

    private CodeBlock analyseCode() {
        try {
            Stack<CodeBlock> stack = new Stack<CodeBlock>();
            CodeBlock source = new CodeBlock(CodeType.source, null);
            stack.push(source);
            BufferedReader reader = new BufferedReader(new StringReader(source_code));
            String line;
            while ((line = reader.readLine()) != null) {
                String trim_line = line.trim();
                if (trim_line.startsWith("package")) {
                    stack.peek().addCode(new CodeBlock(CodeType.packager, line));
                } else if (trim_line.startsWith("import")) {
                    stack.peek().addCode(new CodeBlock(CodeType.importer, line));
                } else if (trim_line.startsWith("//[")) {
                    String signature = trim_line.substring(3);
                    String ctype = signature;
                    String para = null;
                    int left = signature.indexOf("(");
                    if (left != -1) {
                        ctype = signature.substring(0, left);
                        para = signature.substring(left + 1);
                        int right = signature.indexOf(")");
                        if (right != -1) {
                            para = signature.substring(left + 1, right);
                        }
                    }
                    CodeType type = CodeType.valueOf(ctype);
                    CodeBlock block = new CodeBlock(type, para);
                    stack.peek().addCode(block);
                    stack.push(block);
                } else if (trim_line.startsWith("//]")) {
                    stack.pop();
                } else {
                    CodeBlock code = new CodeBlock(CodeType.code, line);
                    stack.peek().addCode(code);
                }
            }
            return stack.pop();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void initListeners(Component comp) {
        for (CodeBlock block : current_code.getCodes()) {
            switch (block.getType()) {
                case init:
                    initComponentListener(comp, block);
                    break;
                case get:
                    String comp_name = block.getParam();
                    Component component = this.getComponentByName(comp, comp_name);
                    if (component != null) {
                        initComponentListener(component, block);
                    }
                    break;
            }
        }
    }

    private void initComponentListener(Component comp, CodeBlock block) {
        for (CodeBlock code : block.getCodes()) {
            switch (code.getType()) {
                case listener:
                    addComponentListener(comp, code);
                    break;
            }
        }
    }

    private void addComponentListener(Component component, CodeBlock code) {
        HashMap<EventSetDescriptor, HashMap<String, EventHandler>> eventHandlers = (HashMap<EventSetDescriptor, HashMap<String, EventHandler>>) ((JComponent) component).getClientProperty("event.handler");
        if (eventHandlers == null) {
            eventHandlers = new HashMap<EventSetDescriptor, HashMap<String, EventHandler>>();
            ((JComponent) component).putClientProperty("event.handler", eventHandlers);
        }
        String eventSetName = code.getParam();
        EventSetDescriptor esd = Util.getESD(eventSetName, component);
        HashMap<String, EventHandler> handlers = eventHandlers.get(esd);
        if (handlers == null) {
            handlers = new HashMap<String, EventHandler>();
            eventHandlers.put(esd, handlers);
        }
        for (CodeBlock block : code.getCodes()) {
            switch (block.getType()) {
                case event:
                    String mName = block.getParam();
                    String content = "";
                    ArrayList<CodeBlock> lines = block.getCodes();
                    if (lines != null) {
                        for (CodeBlock line : lines) {
                            content += line.getParam() + "\n";
                        }
                    }
                    try {
                        EventHandler handler = new EventHandler(mName);
                        handler.setCode_buffer(content);
                        handlers.put(mName, handler);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void initRootComponent(CodeBlock init_block, ArrayList<CodeBlock> custom_codes, Component rootComponent) {
        init_block.addCodeLine("\tprivate void initComponents(){");
        initRootComponentProperties(init_block, rootComponent);
        initRootComponentChildren(init_block, rootComponent);
        initRootComponentEvents(init_block, rootComponent);
        init_block.addAllCode(custom_codes);
        init_block.addCodeLine("\t}");
    }

    private SourceCoder getSourceCoder(Object value, PropertyDescriptor property) {
        Class clazz = property.getPropertyType();
        if (value != null) {
            clazz = value.getClass();
        }
        Class<? extends SourceCoder> coderClass = Util.getSourceCoder(clazz);
        if (coderClass != null) {
            try {
                return coderClass.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            String itemString = (String) property.getValue("items");
            if (!Util.isStringNull(itemString)) {
                try {
                    ItemProvider provider = (ItemProvider) Beans.instantiate(getClass().getClassLoader(), itemString);
                    ItemWrapper wrapper = new ItemWrapper(provider);
                    return wrapper;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    private void initComponentChildren(CodeBlock child_block, Component child) {
        if (Util.isDesigningContainer(designer, child)) {
            Container childContainer = (Container) child;
            initContainerChildren(child_block, childContainer);
        }
    }

    private void initComponentEvents(CodeBlock child_block, Component child) {
        JComponent jcomponent = (JComponent) child;
        HashMap<EventSetDescriptor, HashMap<String, EventHandler>> eventHandlers = (HashMap<EventSetDescriptor, HashMap<String, EventHandler>>) jcomponent.getClientProperty("event.handler");
        if (eventHandlers != null) {
            Set<EventSetDescriptor> keys = eventHandlers.keySet();
            for (EventSetDescriptor key : keys) {
                HashMap<String, EventHandler> handlers = eventHandlers.get(key);
                if (!handlers.isEmpty() && hasHandler(handlers)) {
                    CodeBlock listener_block = new CodeBlock(CodeType.listener, key.getName());
                    child_block.addCode(listener_block);
                    initComponentListenerBlock(child, key, listener_block, handlers);
                }
            }
        }
    }

    private void initComponentListenerBlock(Component child, EventSetDescriptor esd, CodeBlock listener_block, HashMap<String, EventHandler> handlers) {
        String var = Util.getComponentName(child);
        listener_block.addCodeLine("\t\t\t" + var + "." + esd.getAddListenerMethod().getName() + "(new " + esd.getListenerType().getName() + "(){");
        MethodDescriptor[] methods = esd.getListenerMethodDescriptors();
        for (MethodDescriptor method : methods) {
            String code_line = "\t\t\t\tpublic void " + method.getMethod().getName() + "(";
            Class[] paraTypes = method.getMethod().getParameterTypes();
            for (int i = 0; i < paraTypes.length; i++) {
                if (i != 0) {
                    code_line += ",";
                }
                code_line += paraTypes[i].getName() + " event";
            }
            code_line += ") {";
            listener_block.addCodeLine(code_line);
            String mHandler = method.getName();
            EventHandler handler = handlers.get(mHandler);
            String code_buffer = handler.getCode_buffer();
            if (code_buffer != null) {
                CodeBlock body_block = new CodeBlock(CodeType.event, mHandler);
                body_block.addSourceCode(code_buffer);
                listener_block.addCode(body_block);
            }
            listener_block.addCodeLine("\t\t\t\t}");
        }
        listener_block.addCodeLine("\t\t\t});");
    }

    private void initComponentProperties(CodeBlock child_block, Component child) {
        try {
            String cname = Util.getComponentName(child);
            if (Util.isDesigningContainer(designer, child)) {
                ContainerAdapter adapter = (ContainerAdapter) AdapterBus.createComponentAdapter(designer, child);
                String code = adapter.getLayoutCode();
                if (code != null) {
                    code = code.replaceAll("\\Q" + Constants.VAR_CONTAINER + "\\E", cname);
                    child_block.addSourceCode(code, "\t\t\t");
                }
            }
            BeanInfo info = Introspector.getBeanInfo(child.getClass());
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            for (PropertyDescriptor property : properties) {
                if (Util.isPropertyChanged(designer.getDesignLnf(), child, property)) {
                    Method m = property.getWriteMethod();
                    Deprecated dep = m.getAnnotation(Deprecated.class);
                    if (dep == null) {
                        Object beanValue = Util.readBeanValue(child, property);
                        String objectString = beanValue == null ? "null" : beanValue.toString();
                        SourceCoder coder = getSourceCoder(beanValue, property);
                        if (coder != null) {
                            objectString = coder.getJavaCode(beanValue);
                        }
                        String code_line = "\t\t\t" + cname + "." + property.getWriteMethod().getName() + "(" + objectString + ");";
                        child_block.addCodeLine(code_line);
                    }
                }
            }
            Container parent = child.getParent();
            if (parent != null) {
                LayoutManager layout = parent.getLayout();
                if (layout == null || layout instanceof AbsoluteLayout) {
                    child_block.addCodeLine("\t\t\t" + cname + ".setBounds(" + child.getX() + ", " + child.getY() + ", " + child.getWidth() + ", " + child.getHeight() + ");");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initRootComponentChildren(CodeBlock init_block, Component rootComponent) {
        if (rootComponent instanceof Container) {
            Container rootContainer = (Container) rootComponent;
            initContainerChildren(init_block, rootContainer);
        }
    }

    private void initContainerChildren(CodeBlock block, Container container) {
        Component[] children = getChildren(container);
        for (Component child : children) {
            initContainerChild(container, child);
            ContainerAdapter adapter = (ContainerAdapter) AdapterBus.createComponentAdapter(designer, container);
            String code = adapter.getAddComponentCode(child);
            String parentName = "this";
            String tabs = "\t\t";
            if (!designer.isRootComponent(container)) {
                parentName = Util.getComponentName(container);
                tabs += "\t";
            }
            code = code.replaceAll("\\Q" + Constants.VAR_CONTAINER + "\\E", parentName);
            block.addSourceCode(code, tabs);
        }
    }

    private void initContainerChild(Container container, Component child) {
        if (declaration == null) {
            declaration = new CodeBlock(CodeType.declare, null);
            copy.insetCodeAt(init_line_no, declaration);
        }
        CodeBlock child_block = findGetChild(child);
        String cname = Util.getComponentName(child);
        String classname = child.getClass().getName();
        declaration.addCodeLine("\tprivate " + classname + " " + cname + ";");
        if (child_block == null) {
            child_block = new CodeBlock(CodeType.get, cname);
        }
        copy.insetCodeAt(init_line_no, child_block);
        ArrayList<CodeBlock> custom_codes = new ArrayList<CodeBlock>();
        ArrayList<CodeBlock> body_codes = child_block.getCodes();
        if (body_codes != null) {
            for (CodeBlock line_block : body_codes) {
                if (line_block.getType() == CodeType.custom) {
                    custom_codes.add(line_block);
                }
            }
        }
        child_block.clearCodes();
        child_block.addCodeLine("\tprivate " + classname + " " + Util.getGetName(cname) + "(){");
        child_block.addCodeLine("\t\tif(" + cname + " == null){");
        child_block.addCodeLine("\t\t\t" + cname + " = new " + classname + "();");
        initComponentProperties(child_block, child);
        initComponentChildren(child_block, child);
        initComponentEvents(child_block, child);
        child_block.addAllCode(custom_codes);
        child_block.addCodeLine("\t\t}");
        child_block.addCodeLine("\t\treturn " + cname + ";");
        child_block.addCodeLine("\t}");
    }

    private CodeBlock findGetChild(Component child) {
        String cname = Util.getComponentName(child);
        for (CodeBlock block : current_code.getCodes()) {
            if (block.getType() == CodeType.get) {
                if (cname.equals(block.getParam())) {
                    return block;
                }
            }
        }
        return null;
    }

    private void initRootComponentEvents(CodeBlock init_block, Component rootComponent) {
        JComponent jcomponent = AdapterBus.getJComponent(rootComponent);
        HashMap<EventSetDescriptor, HashMap<String, EventHandler>> eventHandlers = (HashMap<EventSetDescriptor, HashMap<String, EventHandler>>) jcomponent.getClientProperty("event.handler");
        if (eventHandlers != null) {
            Set<EventSetDescriptor> keys = eventHandlers.keySet();
            for (EventSetDescriptor key : keys) {
                HashMap<String, EventHandler> handlers = eventHandlers.get(key);
                if (!handlers.isEmpty() && hasHandler(handlers)) {
                    CodeBlock listener_block = new CodeBlock(CodeType.listener, key.getName());
                    init_block.addCode(listener_block);
                    initRootListenerBlock(key, listener_block, handlers);
                }
            }
        }
    }

    private void initRootListenerBlock(EventSetDescriptor esd, CodeBlock listener_block, HashMap<String, EventHandler> handlers) {
        listener_block.addCodeLine("\t\t" + esd.getAddListenerMethod().getName() + "(new " + esd.getListenerType().getName() + "(){");
        MethodDescriptor[] methods = esd.getListenerMethodDescriptors();
        for (MethodDescriptor method : methods) {
            String code_line = "\t\t\tpublic void " + method.getMethod().getName() + "(";
            Class[] paraTypes = method.getMethod().getParameterTypes();
            for (int i = 0; i < paraTypes.length; i++) {
                if (i != 0) {
                    code_line += ",";
                }
                code_line += paraTypes[i].getName() + " event";
            }
            code_line += ") {";
            listener_block.addCodeLine(code_line);
            String mHandler = method.getName();
            EventHandler handler = handlers.get(mHandler);
            String code_buffer = handler.getCode_buffer();
            if (code_buffer != null) {
                CodeBlock body_block = new CodeBlock(CodeType.event, mHandler);
                body_block.addSourceCode(code_buffer);
                listener_block.addCode(body_block);
            }
            listener_block.addCodeLine("\t\t\t}");
        }
        listener_block.addCodeLine("\t\t});");
    }

    private boolean hasHandler(HashMap<String, EventHandler> hm) {
        Set<String> keys = hm.keySet();
        for (String key : keys) {
            EventHandler h = (EventHandler) hm.get(key);
            if (h != null && h.getCode_buffer() != null) {
                return true;
            }
        }
        return false;
    }

    private void initRootComponentProperties(CodeBlock init_block, Component rootComponent) {
        try {
            JComponent jRootComponent = AdapterBus.getJComponent(rootComponent);
            LayoutManager layout = jRootComponent.getLayout();
            boolean default_layout = rootComponent instanceof RootPaneContainer && layout != null && layout instanceof BorderLayout ||
                    rootComponent instanceof JPanel && layout != null && layout instanceof FlowLayout;
            if (!default_layout) {
                if (layout == null) {
                    init_block.addCodeLine("\t\tthis.setLayout(null);");
                } else {
                    ContainerAdapter adapter = (ContainerAdapter) AdapterBus.createComponentAdapter(designer, rootComponent);
                    String code = adapter.getLayoutCode();
                    if (code != null) {
                        code = code.replaceAll("\\Q" + Constants.VAR_CONTAINER + "\\E", "this");
                        init_block.addSourceCode(code, "\t\t");
                    }
                }
            }
            BeanInfo info = Introspector.getBeanInfo(rootComponent.getClass());
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            for (PropertyDescriptor property : properties) {
                if (Util.isPropertyChanged(designer.getDesignLnf(), rootComponent, property)) {
                    Object beanValue = Util.readBeanValue(rootComponent, property);
                    String objectString = beanValue == null ? "null" : beanValue.toString();
                    SourceCoder coder = getSourceCoder(beanValue, property);
                    if (coder != null) {
                        objectString = coder.getJavaCode(beanValue);
                    }
                    String code_line = "\t\t" + property.getWriteMethod().getName() + "(" + objectString + ");";
                    init_block.addCodeLine(code_line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initSourceCode() {
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        if (packager != null) {
            writer.println("package " + packager + ";");
        }
        writer.println("public class " + class_name + " extends " + guitype.getCode() + " {");
        writer.println("\tpublic " + class_name + "(){");
        writer.println("\t\tinitComponents();");
        writer.println("\t}");
        writer.println("\t//[init(400, 300)");
        writer.println("\tprivate void initComponents(){");
        writer.println("\t}");
        writer.println("\t//]");
        writer.println("}");
        writer.close();
        source_code = sw.toString();
    }
    private int init_line_no;
    private CodeBlock declaration;
    private CodeBlock copy;

    private void reset() {
        init_line_no = -1;
        declaration = null;
        copy = null;
    }

    public String generateSourceCode(Component rootComponent) {
        reset();
        copy = new CodeBlock(CodeType.source, null);
        ArrayList<CodeBlock> blocks = current_code.getCodes();
        CodeBlock init_block = null;
        for (int i = 0; i < blocks.size(); i++) {
            CodeBlock block = blocks.get(i);
            switch (block.getType()) {
                case packager:
                case importer:
                case init:
                    init_line_no = copy.getBlockCount() + 1;
                    init_block = block;
                case code:
                    copy.addCode(block);
                    break;
            }
        }
        ArrayList<CodeBlock> custom_codes = new ArrayList<CodeBlock>();
        blocks = init_block.getCodes();
        for (int i = 0; i < blocks.size(); i++) {
            CodeBlock block = blocks.get(i);
            CodeType type = block.getType();
            if (type == CodeType.custom) {
                custom_codes.add(block);
            }
        }
        init_block.clearCodes();
        init_block.setParam("" + rootComponent.getWidth() + ", " + rootComponent.getHeight());
        initRootComponent(init_block, custom_codes, rootComponent);
        current_code = copy;
        source_code = current_code.getSourceCode();
        return source_code;
    }

    private Component[] getChildren(Container container) {
        ArrayList<Component> components = new ArrayList<Component>();
        _collectDesigningComponents(container, components);
        return components.toArray(new Component[0]);
    }

    private void _collectDesigningComponents(Container container, ArrayList<Component> components) {
        int count = container.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component child = container.getComponent(i);
            if (Util.isDesigning(child)) {
                components.add(child);
            } else if (child instanceof Container) {
                _collectDesigningComponents((Container) child, components);
            }
        }
    }

    public static File getCodeBufferDir() {
        File codeBufferDir = new File(new File("."), ".swing_designer_buffer");
        if (!codeBufferDir.exists()) {
            codeBufferDir.mkdir();
        }
        return codeBufferDir;
    }

    private static File getCodeBufferFile() {
        File codeBufferDir = getCodeBufferDir();
        return new File(codeBufferDir, "temp.java");
    }

    private static void cleanCacheDir() {
        deleteDir(getCodeBufferDir());
    }

    private static void deleteDir(File dir) {
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    file.delete();
                }
            }
            dir.delete();
        }
    }

    public String generateSourceFile() {
        try {
            File bf = getCodeBufferFile();
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(bf)));
            String cName = generatePrototype(writer);
            writer.close();
            return cName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Component generateComponent(PrintWriter pw) {
        try {
            current_code = analyseCode();
            String className = generateSourceFile();
            File srcFile = saveToFile(className);

            Class clazz = compileAndLoad(className, srcFile, pw);
            if (clazz != null) {
                Component comp = (Component) clazz.newInstance();
                if (comp != null) {
                    initComponentName(comp);
                    initListeners(comp);
                    comp.setSize(getInitSize());
                    return comp;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(pw);
        }
        return null;
    }

    private Dimension getInitSize() {
        CodeBlock init_block = prototype.getInit();
        String param = init_block.getParam();
        if (param != null) {
            int coma = param.indexOf(",");
            if (coma != -1) {
                String xStr = param.substring(0, coma).trim();
                String yStr = param.substring(coma + 1).trim();
                int x = Integer.parseInt(xStr);
                int y = Integer.parseInt(yStr);
                return new Dimension(x, y);
            }
        }
        return new Dimension(400, 300);
    }

    private Component getComponentByName(Component root, String varName) {
        try {
            Class clazz = root.getClass();
            Field field = clazz.getDeclaredField(varName);
            field.setAccessible(true);
            return (Component) field.get(root);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initComponentName(Component comp) {
        Class clazz = comp.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Component subcomp = (Component) field.get(comp);
                if (!Util.isDesigning(subcomp)) {
                    Util.setComponentName(subcomp, field.getName());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (!Util.isDesigning(comp)) {
            Util.setComponentName(comp, getClassName());
        }
    }

    public String getClassName() {
        return class_name;
    }

    public String getSourceCode() {
        return source_code;
    }

    public void setSourceCode(String source_code) {
        this.source_code = source_code;
    }

    private File saveToFile(String cName) {
        String file_name = cName;
        String pack = null;
        int dot = cName.lastIndexOf(".");
        if (dot != -1) {
            file_name = cName.substring(dot + 1);
            pack = cName.substring(0, dot);
            pack = pack.replace('.', '/');
        }
        file_name += ".java";
        File bf = getCodeBufferFile();
        if (pack != null) {
            File dir = getCodeBufferDir();
            File src_dir = new File(dir, pack);
            if (!src_dir.exists()) {
                src_dir.mkdirs();
            }
            File newfile = new File(src_dir, file_name);
            bf.renameTo(newfile);
            return newfile;
        } else {
            File newfile = new File(getCodeBufferDir(), file_name);
            bf.renameTo(newfile);
            return newfile;
        }
    }

    public Item getGuitype() {
        return guitype;
    }

    public void setGuitype(Item guitype) {
        this.guitype = guitype;
    }
}