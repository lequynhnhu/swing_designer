/*
 * Drawer.java
 *
 * Created on June 8, 2007, 11:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JComponent;


/**
 * �����Ǹ������࣬Ŀ����ʵ�ֶ���״̬ʱ͸��ͼ��������ƶ�Ч��
 * ����һ�����룬��Ӧ�ó�������
 *
 * @author William Chen
 */
class Drawer extends JComponent {
    //Ŀǰ����鿪������ͬʱ��͸����ֵ
    private double ratio = 1.0;

    //Ŀǰ�Ƿ��ڶ���״̬
    private boolean animating;

    //Ӧ�ó�������
    private JComponent content;

    //������Ӧ�ó������������ͼ��
    private Image offImage;

    /** Creates a new instance of Drawer */
    Drawer(double r, JComponent comp) {
        this.ratio = r;
        this.content = comp;
        add(comp);
        setLayout(null);
    }
    
    int getContentHeight() {
        return content.getHeight();
    }

    int getContentWidth() {
        return content.getWidth();
    }
    void setContentWidth(int w){
        Rectangle bounds=content.getBounds();
        bounds.width=w;
        content.setBounds(bounds);
    }
    void setRatio(double ratio) {
        this.ratio = ratio;
        repaint();
    }

    double getRatio() {
        return this.ratio;
    }

    //���Ǹ����paintChildren
    protected void paintChildren(Graphics g) {
        if (animating) {
            //����״̬������ǰ͸����Ӧ�ó�������Լ�����״̬
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, (float) ratio));
            g2d.drawImage(getOffImage(), 0, getHeight() - content.getHeight(),
                this);
        } else {
            //��ͨ״̬��ʹ������ȱʡ����Ⱦ
            super.paintChildren(g);
        }
    }

    void clearOffImage() {
        offImage = null;
    }

    void setAnimating(boolean animating) {
        this.animating = animating;
    }

    //����Ӧ�ó������������ͼ��
    private Image getOffImage() {
        if (offImage == null) {
            int contentWidth = content.getWidth();
            int contentHeight = content.getHeight();

            if (offImage == null) {
                offImage = createImage(contentWidth, contentHeight);

                Graphics g = offImage.getGraphics();
                //ʹ��Renderer����
                content.paint(g);
            }
        }

        return offImage;
    }
}
