/*
 * DesigningPanel.java
 *
 * Created on August 3, 2007, 4:16 PM
 */
package dyno.swing.designer.main;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.actions.ActionCategory;
import dyno.swing.designer.beans.code.CodeSeed;
import dyno.swing.designer.beans.toolkit.BeanInfoToggleButton;
import dyno.swing.designer.beans.toolkit.ComponentPalette;
import dyno.swing.designer.main.actions.AboutAction;
import dyno.swing.designer.main.actions.ExitAction;
import dyno.swing.designer.main.actions.NewFileAction;
import dyno.swing.designer.main.actions.NewFileWorker;
import dyno.swing.designer.main.actions.OpenFileAction;
import dyno.swing.designer.main.actions.SaveAsFileAction;
import dyno.swing.designer.main.actions.SaveFileAction;
import dyno.swing.designer.main.actions.SourceParser;
import dyno.swing.designer.main.actions.SourceWorker;
import dyno.swing.designer.properties.types.Item;
import dyno.swing.designer.treeview.TreePalette;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 *
 * @author  William Chen
 */
public class DesigningPanel extends JPanel implements InvocationHandler {

    private double overallRatio = 0.73;
    private double toolkitRatio = 0.2;
    private double middleRatio = 0.75;
    private CodeSeed seed;
    private boolean dirty;

    /** Creates new form DesigningPanel */
    public DesigningPanel() {
        initComponents();
        seed = new CodeSeed(designer);
        designer.addInvocationHandler(this);
    }

    public PrintWriter getWriter() {
        return new PrintWriter(logger.getWriter());
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isStateChange(method.getName())) {
            setDirty(true);
        } 
        return null;
    }

    public void setDirty(boolean d) {
        dirty = d;
        getSaveFileAction().setEnabled(dirty);
    }

    private boolean isStateChange(String methodName) {
        return !dirty && (methodName.equals("componentAdded") || methodName.equals("componentDeleted") || methodName.equals("componentCut") || methodName.equals("componentPasted") || methodName.equals("componentEdited") || methodName.equals("componentResized") || methodName.equals("componentMoved"));
    }

    public void setSource(String code) {
        txtSource.setText(code);
    }

    public void logging(String message) {
        logger.logging(message);
    }

    public void logging(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        logger.logging(sw.toString());
    }

    public void setProgressVisible(boolean b) {
        CardLayout card = (CardLayout) getCardPanel().getLayout();
        card.show(getCardPanel(), b ? "progress" : "designer");
    }

    public String getSource() {
        return txtSource.getText();
    }

    private JSplitPane getSpToolkit() {
        if (spToolkit == null) {
            spToolkit = new JSplitPane();
            spMiddle = getSpMiddle();
            spToolkit.setRightComponent(spMiddle);
            palette = getPalette();
            palette.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            paletteActionPerformed(evt);
                        }
                    });
            spToolkit.setLeftComponent(palette);
        }
        return spToolkit;
    }

    private ComponentPalette getPalette() {
        if (palette == null) {
            palette = new ComponentPalette(designer);
            designer.setPalette(palette);
        }
        return palette;
    }

    private JSplitPane getSpMiddle() {
        if (spMiddle == null) {
            spMiddle = new JSplitPane();
            spMiddle.setOrientation(JSplitPane.VERTICAL_SPLIT);
            dsgPanel = getDsgPanel();
            spMiddle.setTopComponent(dsgPanel);
            logger = getLogger();
            spMiddle.setBottomComponent(logger);
        }
        return spMiddle;
    }

    private JPanel getDsgPanel() {
        if (dsgPanel == null) {
            dsgPanel = new JPanel();
            dsgPanel.setBorder(BorderFactory.createLineBorder(new Color(127, 157, 185)));
            dsgPanel.setLayout(new BorderLayout());
            dsgPanel.setBackground(Color.white);
            cardPanel = getCardPanel();
            dsgPanel.add(cardPanel, BorderLayout.CENTER);
        }
        return dsgPanel;
    }

    private JPanel getCardPanel() {
        if (cardPanel == null) {
            cardPanel = new JPanel(new CardLayout());
            pPanel = getPPanel();
            cardPanel.add(pPanel, "progress");
            spDesigner = getSpDesigner();
            cardPanel.add(spDesigner, "designer");
        }
        return cardPanel;
    }

    private ProgressPanel getPPanel() {
        if (pPanel == null) {
            pPanel = new ProgressPanel();
        }
        return pPanel;
    }

    private JScrollPane getSpDesigner() {
        if (spDesigner == null) {
            spDesigner = new JScrollPane();
            designer = getDesigner();
            spDesigner.getViewport().setOpaque(true);
            spDesigner.getViewport().setBackground(Color.white);
            spDesigner.getViewport().setView(designer);
        }
        return spDesigner;
    }

    private JToolBar getDesignerTools() {
        if (designerTools == null) {
            designerTools = new JToolBar();
            designerTools.setFloatable(false);
            designerTools.add(getNewFileAction());
            designerTools.add(getOpenFileAction());
            designerTools.add(getSaveFileAction());
            Action[] actions = designer.getActions();
            initActionButton(actions);
        }
        return designerTools;
    }

    public SwingDesigner getDesigner() {
        if (designer == null) {
            designer = new SwingDesigner();
        }
        return designer;
    }

    private LoggingPanel getLogger() {
        if (logger == null) {
            logger = new LoggingPanel();
        }
        return logger;
    }

    private TreePalette getRightPalette() {
        if (rightPalette == null) {
            rightPalette = new TreePalette(designer);            
        }
        return rightPalette;
    }

    public void setRootComponent(Component comp) {
        designer.setRootComponent(comp);
        designer.repaint();
    }

    public void refreshRightPalette() {
        rightPalette.refreshUI();
    }

    private JSplitPane getSpOverall() {
        if (spOverall == null) {
            spOverall = new JSplitPane();
            spToolkit = getSpToolkit();
            rightPalette = getRightPalette();
            spOverall.setLeftComponent(spToolkit);
            spOverall.setRightComponent(rightPalette);
        }
        return spOverall;
    }

    private void initActionButton(Action[] actions) {
        for (Action action : actions) {
            if (action instanceof ActionCategory) {
                designerTools.addSeparator();
                Action[] sub = ((ActionCategory) action).getSubActions();
                initActionButton(sub);
            } else {
                designerTools.add(action);
            }
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        guiDsgPanel = getGuiDsgPanel();
        add(guiDsgPanel, BorderLayout.CENTER);
        menuBar = getMenuBar();
        add(menuBar, BorderLayout.NORTH);
    }

    private JMenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            menuFile = getMenuFile();
            menuBar.add(menuFile);
            menuEdit = getMenuEdit();
            menuBar.add(menuEdit);
            menuHelp = getMenuHelp();
            menuBar.add(menuHelp);
        }
        return menuBar;
    }

    private JMenu getMenuEdit() {
        if (menuEdit == null) {
            menuEdit = new JMenu("Edit");
            boolean first = true;
            Action[] actions = designer.getActions();
            for (Action action : actions) {
                if (action instanceof ActionCategory) {
                    if (first) {
                        first = false;
                    } else {
                        menuEdit.addSeparator();
                    }
                    Action[] subs = ((ActionCategory) action).getSubActions();
                    for (Action sub : subs) {
                        menuEdit.add(sub);
                    }
                } else {
                    menuEdit.add(action);
                }
            }
        }
        return menuEdit;
    }
    private Action newFileAction;

    private Action getNewFileAction() {
        if (newFileAction == null) {
            newFileAction = new NewFileAction(this);
        }
        return newFileAction;
    }
    private Action openFileAction;

    private Action getOpenFileAction() {
        if (openFileAction == null) {
            openFileAction = new OpenFileAction(this);
        }
        return openFileAction;
    }
    private Action saveFileAction;

    private Action getSaveFileAction() {
        if (saveFileAction == null) {
            saveFileAction = new SaveFileAction(this);
        }
        return saveFileAction;
    }
    private Action saveAsFileAction;

    private Action getSaveAsFileAction() {
        if (saveAsFileAction == null) {
            saveAsFileAction = new SaveAsFileAction(this);
        }
        return saveAsFileAction;
    }

    private Action[] getFileActions() {

        return new Action[]{getNewFileAction(), getOpenFileAction(), getSaveFileAction(), getSaveAsFileAction()};
    }

    private JMenu getMenuFile() {
        if (menuFile == null) {
            menuFile = new JMenu();
            menuFile.setText("File");

            for (Action action : getFileActions()) {
                menuFile.add(action);
            }
            menuFile.addSeparator();
            menuFile.add(new ExitAction(this));
        }
        return menuFile;
    }

    private JMenu getMenuHelp() {
        if (menuHelp == null) {
            menuHelp = new JMenu();
            menuHelp.setText("Help");
            menuHelp.add(new AboutAction(this));
        }
        return menuHelp;
    }

    void newPanel() {
        Item item=Util.getDefaultGuiType();
        new NewFileWorker(this, null, (String)item.getValue(), item).execute();
    }

    public CodeSeed getSeed() {
        return seed;
    }

    private JTabbedPane getOutlineTab() {
        if (outlineTab == null) {
            outlineTab = new JTabbedPane();
            outlineTab.setTabPlacement(JTabbedPane.BOTTOM);
            spGuiPanel = getSpGuiPanel();
            outlineTab.addTab("GUI Designer", spGuiPanel);
            spSourceCode = getSpSourceCode();
            outlineTab.addTab("Source Code", spSourceCode);
            outlineTab.addChangeListener(new ChangeListener() {

                        @Override
                public void stateChanged(ChangeEvent e) {
                            outlineStateChanged(e);
                        }
                    });
        }
        return outlineTab;
    }

    private void outlineStateChanged(ChangeEvent e) {
        if (dirty) {
            int index = outlineTab.getSelectedIndex();
            if (index == 0) {
                seed.setSourceCode(txtSource.getText());
                SourceParser worker = new SourceParser(this);
                worker.execute();
            } else {
                new SourceWorker(this).execute();
            }
        }
    }

    public boolean isDesigningGUI() {
        return outlineTab.getSelectedIndex() == 0;
    }

    private JPanel getGuiDsgPanel() {
        if (guiDsgPanel == null) {
            guiDsgPanel = new JPanel(new BorderLayout());
            outlineTab = getOutlineTab();
            guiDsgPanel.add(outlineTab, BorderLayout.CENTER);
            designerTools = getDesignerTools();
            guiDsgPanel.add(designerTools, BorderLayout.NORTH);
        }
        return guiDsgPanel;
    }

    private JPanel getSpGuiPanel() {
        if (spGuiPanel == null) {
            spGuiPanel = new JPanel(new DesigningPanelLayout());
            spOverall = getSpOverall();
            spGuiPanel.add(spOverall);
        }
        return spGuiPanel;
    }

    private JScrollPane getSpSourceCode() {
        if (spSourceCode == null) {
            spSourceCode = new JScrollPane();
            txtSource = getTxtSource();
            spSourceCode.setViewportView(txtSource);
        }
        return spSourceCode;
    }

    public JTextArea getTxtSource() {
        if (txtSource == null) {
            txtSource = new JTextArea();
            txtSource.setFont(new Font("DialogInput", 0, 12));
            txtSource.setTabSize(4);
            txtSource.getDocument().addDocumentListener(new DocumentListener() {

                        @Override
                public void insertUpdate(DocumentEvent e) {
                            if (!dirty) {
                                setDirty(true);
                            }
                        }

                        @Override
                public void removeUpdate(DocumentEvent e) {
                            if (!dirty) {
                                setDirty(true);
                            }
                        }

                        @Override
                public void changedUpdate(DocumentEvent e) {
                            if (!dirty) {
                                setDirty(true);
                            }
                        }
                    });
        }
        return txtSource;
    }

    private void paletteActionPerformed(java.awt.event.ActionEvent evt) {
        if (designer.isAddingMode()) {
            designer.stopAddingState();
        }
        BeanInfoToggleButton tb = (BeanInfoToggleButton) evt.getSource();
        if (tb.isSelected()) {
            designer.startAddingState(tb.getBeanInfo());
        }
    }

    private class DesigningPanelLayout implements LayoutManager {

        public void addLayoutComponent(String name, Component comp) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(100, 100);
        }

        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        public void layoutContainer(Container parent) {
            int w = parent.getWidth();
            int h = parent.getHeight();
            spOverall.setBounds(0, 0, w, h);
            int dl = (int) (overallRatio * w);
            spOverall.setDividerLocation(dl);
            dl = (int) (overallRatio * w * toolkitRatio);
            spToolkit.setDividerLocation(dl);
            dl = (int) (middleRatio * h);
            spMiddle.setDividerLocation(dl);
            SwingUtilities.updateComponentTreeUI(spOverall);

            spOverall.setDividerSize(5);
            spOverall.setBorder(null);
            ((BasicSplitPaneUI) spOverall.getUI()).getDivider().setBorder(null);
            spToolkit.setDividerSize(5);
            spToolkit.setBorder(null);
            ((BasicSplitPaneUI) spToolkit.getUI()).getDivider().setBorder(null);
            spMiddle.setDividerSize(5);
            spMiddle.setBorder(null);
            ((BasicSplitPaneUI) spMiddle.getUI()).getDivider().setBorder(null);
            spDesigner.setBorder(null);
            spDesigner.getViewport().setBorder(null);
        }
    }
    private SwingDesigner designer;
    private JToolBar designerTools;
    private JPanel dsgPanel;
    private ComponentPalette palette;
    private TreePalette rightPalette;
    private LoggingPanel logger;
    private JSplitPane spOverall;
    private JSplitPane spToolkit;
    private JSplitPane spMiddle;
    private JTabbedPane outlineTab;
    private JScrollPane spSourceCode;
    private JTextArea txtSource;
    private JPanel guiDsgPanel;
    private JPanel spGuiPanel;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuHelp;
    private JScrollPane spDesigner;
    private JPanel cardPanel;
    private ProgressPanel pPanel;
    private JMenu menuEdit;
}