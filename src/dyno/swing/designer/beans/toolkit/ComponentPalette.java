/*
 * ComponentToolKit.java
 *
 * Created on August 3, 2007, 2:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.toolkit;

import dyno.swing.beans.FolderPane;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.events.DesignerEditAdapter;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author William Chen
 */
public class ComponentPalette extends JScrollPane {

    private static Color FOLDER_PANE_BACKGROUND = new Color(214, 223, 247);
    private static int FOLDER_PANE_WIDTH = 150;
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
    private FolderPane folderPane;
    private ButtonGroup buttonGroup;
    private MouseInputListener mouseProxy;
    private PaletteDraggingHandler handler;
    private ArrayList<JToolBar> toolBars;

    /** Creates a new instance of ComponentToolKit */
    public ComponentPalette(SwingDesigner designer) {
        ClassLoader loader = getClass().getClassLoader();
        Class[] interfaces = new Class[]{MouseInputListener.class};
        InvocationHandler h = new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (handler != null) {
                            return method.invoke(handler, args);
                        }
                        return null;
                    }
                };
        mouseProxy = (MouseInputListener) Proxy.newProxyInstance(loader, interfaces, h);
        toolBars = new ArrayList<JToolBar>();
        buttonGroup = new ButtonGroup();
        folderPane = new FolderPane();
        folderPane.setAnimated(true);
        initBeans();
        setViewportView(folderPane);
        getViewport().setBackground(folderPane.getBackground());
        designer.addDesignerEditListener(new DesignerEditAdapter() {

                    public void componentAdded(DesignerEvent evt) {
                        reset();
                    }

                    public void componentCanceled(DesignerEvent evt) {
                        reset();
                    }
                });
        this.handler = new PaletteDraggingHandler(designer);
    }

    public void addActionListener(ActionListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void removeActionListener(ActionListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    protected void fireActionPerformed(ActionEvent e) {
        for (ActionListener l : listeners) {
            l.actionPerformed(e);
        }
    }

    private boolean addFolder(JToolBar toolBar, String groupName, boolean added) {
        boolean expanded = false;
        if (!added) {
            expanded = true;
            added = true;
        }
        folderPane.addFolder(groupName, expanded, toolBar);

        Dimension size = toolBar.getPreferredSize();
        size.width = FOLDER_PANE_WIDTH;
        toolBar.setSize(size);
        return added;
    }

    private void initBeans() {
        InputStream input = null;
        try {
            input = this.getClass().getResourceAsStream("beans.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            String groupName = null;
            JToolBar toolBar = null;
            boolean added = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("#") && line.length() > 0) {
                    if (line.startsWith("component.group=")) {
                        if (groupName != null) {
                            added = addFolder(toolBar, groupName, added);
                        }
                        groupName = parseValue(line);
                        toolBar = new JToolBar(JToolBar.VERTICAL);
                        toolBar.setBackground(FOLDER_PANE_BACKGROUND);
                        toolBar.setFloatable(false);
                        toolBar.setLayout(new GridLayout(0, 1));
                        toolBars.add(toolBar);
                    } else {
                        JToggleButton tb = initBean(line);
                        if (tb != null) {
                            buttonGroup.add(tb);
                            toolBar.add(tb);
                        }
                        tb.setTransferHandler(new PaletteTransferHandler());
                        tb.addMouseListener(mouseProxy);
                        tb.addMouseMotionListener(mouseProxy);
                    }
                }
            }
            if (groupName != null) {
                addFolder(toolBar, groupName, added);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private JToggleButton initBean(String line) {
        try {
            String componentName = parseKey(line);
            String classname = parseValue(line);
            Class bean_class = Class.forName(classname);
            BeanInfo beanInfo = Introspector.getBeanInfo(bean_class);
            final BeanInfoToggleButton tb = new BeanInfoToggleButton(beanInfo);
            tb.setText(componentName);
            tb.setToolTipText(bean_class.getName());
            tb.setHorizontalAlignment(SwingConstants.LEFT);

            Image image = beanInfo.getIcon(BeanInfo.ICON_COLOR_16x16);

            if (image != null) {
                tb.setIcon(new ImageIcon(image));
            }
            tb.addItemListener(new ItemListener() {

                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                folderPane.requestFocus();
                                ActionEvent evt=new ActionEvent(e.getSource(), 0, "selection");
                                fireActionPerformed(evt);
                            }
                        }
                    });
            tb.addMouseListener(new MouseAdapter() {

                        @Override
                public void mouseEntered(MouseEvent e) {
                            resetRollover(e.getSource());
                        }
                    });

            return tb;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private String parseKey(String line) {
        String key = line;
        int index = line.indexOf("=");

        if (index != -1) {
            key = line.substring(0, index);
        }

        return key;
    }

    private String parseValue(String line) {
        String value = null;
        int index = line.indexOf("=");

        if (index != -1) {
            value = line.substring(index + 1);
        }

        return value;
    }

    public void reset() {
        buttonGroup.clearSelection();
        resetRollover(null);
    }

    private void resetRollover(Object current) {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();

            if (button == current) {
                continue;
            }

            ButtonModel model = button.getModel();

            if (model.isRollover()) {
                model.setRollover(false);
            }
        }

        folderPane.requestFocus();
        folderPane.repaint();
    }
}