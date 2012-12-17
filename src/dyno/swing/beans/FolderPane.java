/*
 * FolderPane.java
 *
 * Created on June 8, 2007, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.Scrollable;


/**
 * Windows�ļ����������۵���������
 * ʵ����Scrollable��ʹ���ܷ���JScrollPane�С�
 *
 * @author William Chen
 */
public class FolderPane extends JComponent implements Scrollable {
    //���е����
    private ArrayList<Folder> tabs = new ArrayList<Folder>();

    //�Ƿ���������Ч��
    private boolean animated;

    /** Creates a new instance of FolderPane */
    public FolderPane() {
        //set UI
        setUI(new FolderPaneUI());
        setLayout(new FolderPaneLayout());
    }

    public void addFolder(String title, JComponent comp) {
        addFolder(title, true, comp);
    }

    /**
     * @param title �����ı���
     * @param expanded ������ʼ״̬�Ƿ�չ��
     * @param comp ������Ӧ�ó������
     */
    public void addFolder(String title, boolean expanded, JComponent comp) {
        Folder tab = new Folder(title, expanded, comp);
        tabs.add(tab);
        this.add(tab);
        //Ϊ�������ӳ����¼�
        ((FolderPaneUI) ui).addTab(tab);
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    ArrayList<Folder> getTabs() {
        return tabs;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
        int orientation, int direction) {
        return 10;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
        int orientation, int direction) {
        return 100;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    //�ò�����Ĳ��ֹ�����
    class FolderPaneLayout implements LayoutManager {
        private static final int INTER_TAB_PADDING = 15;

        public FolderPaneLayout() {
        }

        public void addLayoutComponent(String name, Component comp) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension preferredLayoutSize(Container parent) {
            //����ʵ��preferredSize�Ա���JScrollPane����ʱʹ��
            Insets insets = parent.getInsets();
            int w = 0;
            int h = 0;

            for (Folder tab : tabs) {
                Dimension dim = tab.getRequiredDimension();

                if (dim.width > w) {
                    w = dim.width;
                }

                h += (dim.height + INTER_TAB_PADDING);
            }

            w += (insets.left + insets.right);
            h += (insets.top + insets.bottom);

            return new Dimension(w, h);
        }

        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            int width=parent.getWidth()-insets.left-insets.right;
            int x = insets.left;
            int y = insets.top;

            for (Folder tab : tabs) {
                Dimension dim = tab.getRequiredDimension();
                tab.setBounds(x, y, width, dim.height);
                tab.doLayout();
                y += (dim.height + INTER_TAB_PADDING);
            }
        }
    }
}
