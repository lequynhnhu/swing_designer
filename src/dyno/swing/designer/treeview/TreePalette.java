/*
 * TreePropPalette.java
 *
 * Created on 2007年8月11日, 下午9:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.treeview;

import dyno.swing.beans.FolderPane;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.events.DesignerEditAdapter;
import dyno.swing.designer.beans.events.DesignerEvent;
import dyno.swing.designer.properties.ComponentPropertyTable;
import dyno.swing.designer.properties.EventPropertyTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 *
 * @author William Chen
 */
public class TreePalette extends JScrollPane {

    private static final int PALETTE_WIDTH = 250;
    private JScrollPane treeSp;
    private FolderPane folderPane;
    private ComponentTree tree;
    private ComponentPropertyTable propertyTable;
    private EventPropertyTable eventTable;
    private SwingDesigner designer;

    /** Creates a new instance of TreePropPalette */
    public TreePalette(SwingDesigner designer) {
        this.designer = designer;
        folderPane = new FolderPane();
        folderPane.setAnimated(true);
        initFolderPane();
        setViewportView(folderPane);
        getViewport().setBackground(folderPane.getBackground());
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        designer.addDesignerEditListener(new DesignerEditAdapter() {
            public void componentAdded(DesignerEvent evt) {
                refresh();
            }
            public void componentDeleted(DesignerEvent evt) {
                refresh();
            }
            public void componentCut(DesignerEvent evt) {
                refresh();
            }
            public void componentPasted(DesignerEvent evt) {
                refresh();
            }
        });
    }

    public void refreshUI() {
        tree.refreshTreeRoot();
        propertyTable.setBean(null);
        eventTable.setBean(null);
    }

    private void initFolderPane() {
        tree = new ComponentTree(designer);
        treeSp = new JScrollPane(tree);
        treeSp.setSize(PALETTE_WIDTH, 308);
        folderPane.addFolder("Inspector", treeSp);
        propertyTable = new ComponentPropertyTable(designer);
        propertyTable.setBorder(null);
        JScrollPane psp = new JScrollPane(propertyTable);
        psp.setBorder(null);
        eventTable = new EventPropertyTable(designer);
        eventTable.setBorder(null);
        JScrollPane esp = new JScrollPane(eventTable);
        esp.setBorder(null);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(true);
        tabbedPane.setBorder(null);
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        tabbedPane.addTab("Properties", psp);
        tabbedPane.addTab("Events", esp);
        tabbedPane.setSize(PALETTE_WIDTH, 309);
        folderPane.addFolder("Properties", tabbedPane);
    }

    public void refresh() {
        tree.refreshUI();
    }

    public ComponentTree getComponentTree() {
        return tree;
    }
}