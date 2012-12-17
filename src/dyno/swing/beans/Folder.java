/*
 * Folder.java
 *
 * Created on June 8, 2007, 5:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JComponent;


/**
 * �����Ǹ������࣬ʵ���۵�����һ���߼���壬�������caption�����ݡ�
 *
 * @author William Chen
 */
class Folder extends JComponent {
    //ȱʡcaption�ĸ߶�
    private static final int CAPTION_HEIGHT = 25;

    //�������
    private CaptionButton caption;

    //��Ӧ�ó���ĳ���
    private Drawer drawer;

    /**
     * Creates a new instance of Folder
     */
    Folder(String label, JComponent comp) {
        this(label, true, comp);
    }

    /**
     * @param label ����������
     * @param expanded ����Ƿ�չ��
     * @param comp Ӧ�ó������
     */
    Folder(String label, boolean expanded, JComponent comp) {
        //�����Լ���layout
        setLayout(new FolderTabLayout());
        //���ɲ���ӱ������
        caption = new CaptionButton(label, expanded);
        add(caption);
        //���ɲ���ӳ���
        drawer = new Drawer(expanded ? 1 : 0, comp);
        add(drawer);
    }
    CaptionButton getCaption() {
        return caption;
    }

    Drawer getDrawer() {
        return drawer;
    }

    //��ø����Ŀǰ����Ŀռ��С��drawer+caption_height
    Dimension getRequiredDimension() {
        int w = drawer.getContentWidth();

        //�߶��ǳ���ĸ߶ȼ��ϱ���ĸ߶ȣ�����ĸ߶���Ŀǰ����ĳ���        
        int h = (int) (drawer.getContentHeight() * drawer.getRatio()) +
            CAPTION_HEIGHT;

        return new Dimension(w, h);
    }

    /**
     * ���𲼾�������
     */
    class FolderTabLayout implements LayoutManager {
        public void addLayoutComponent(String name, Component comp) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension preferredLayoutSize(Container parent) {
            return parent.getPreferredSize();
        }

        public Dimension minimumLayoutSize(Container parent) {
            return parent.getMinimumSize();
        }

        public void layoutContainer(Container parent) {
            int w = parent.getWidth();
            int h = parent.getHeight();
            //���������ǹ̶��߶�
            caption.setBounds(0, 0, w, CAPTION_HEIGHT);
            //����ֻ��ʾ����ı���
            drawer.setBounds(0, CAPTION_HEIGHT, w, h - CAPTION_HEIGHT);
            drawer.setContentWidth(w);
        }
    }
}
