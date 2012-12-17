/*
 * FolderPaneUI.java
 *
 * Created on June 9, 2007, 9:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.plaf.ComponentUI;


/**
 * FolderPane��UI��
 *
 * @author William Chen
 */
public class FolderPaneUI extends ComponentUI {
    //ȱʡ����
    private static final Color BACK_COLOR = new Color(116, 149, 226);
    private static final int LEFT_PADDING = 12;
    private static final int TOP_PADDING = 12;
    private static final int RIGHT_PADDING = 12;
    private static final int BOTTOM_PADDING = 12;
    private static final double INCDEC = 0.085;
    private static final double DECRATIO = 0.925;
    private static final int ANIMATION_INTERVAL = 15;

    //FolderPane��ʵ��
    private FolderPane pane;

    //��װui
    public void installUI(JComponent c) {
        c.setOpaque(true);
        c.setBackground(BACK_COLOR);
        c.setBorder(BorderFactory.createEmptyBorder(TOP_PADDING, LEFT_PADDING,
                RIGHT_PADDING, BOTTOM_PADDING));
        pane = (FolderPane) c;
    }

    //Ϊ���������۵������¼�
    void addTab(Folder tab) {
        tab.getCaption().addItemListener(new FolderTabItemListener(tab));
    }

    //�۵��¼�������
    class FolderTabItemListener implements ItemListener {
        private Folder tab;
        private Timer timer;

        public FolderTabItemListener(Folder t) {
            tab = t;
        }

        public void itemStateChanged(ItemEvent e) {
            if (pane.isAnimated()) {
                //��Ҫ����Ч�����ȹرտ��ܵ�timer
                if (timer != null) {
                    if (timer.isRunning()) {
                        timer.stop();
                    }
                }
                
                //�����µ�timer
                timer = new Timer(ANIMATION_INTERVAL, null);

                //����չ�������ǹر�ѡ��ͬ�Ķ���������
                ActionListener action;

                if (e.getStateChange() == e.DESELECTED) {
                    action = new FolderingAction(tab, timer);
                } else {
                    action = new ExpandingAction(tab, timer);
                }

                tab.getDrawer().clearOffImage();
                timer.addActionListener(action);
                //����timer
                timer.start();
            } else {
                //�޶���Ч����ֱ��չ�����߹ر�
                tab.getDrawer()
                   .setRatio((e.getStateChange() == e.DESELECTED) ? 0 : 1.0);
                pane.doLayout();
            }

            //ȷ���������
            pane.repaint();
        }
    }

    //�۵��¼�����
    abstract class FolderAction implements ActionListener {
        //��ǰ�۵������
        private Folder tab;

        //��ص�ʱ��
        private Timer timer;

        //������������������������߹رյ�ʵʱ�ٶ�
        private double ratio;
        private double exponent;

        public FolderAction(Folder t, Timer timer) {
            tab = t;
            this.timer = timer;
            ratio = INCDEC;
            exponent = DECRATIO;
            //���������ڶ���Ч��״̬
            tab.getDrawer().setAnimating(true);
        }

        //�����ٶȱ仯����������������˶�Ч���ĳ���
        protected double getDelta() {
            double r = ratio;
            ratio = ratio * exponent;

            return r;
        }

        public void actionPerformed(ActionEvent e) {
            //�����µ�ֵ
            double r = delta(tab.getDrawer().getRatio(), getDelta());

            if (overflow(r)) {
                //�������Ϊ����ֵ
                r = bound();
                //���ó���״̬��ֹͣ����Ч�������²����۵���壬ֹͣʱ�ӣ�����ͷ�ʱ��
                tab.getDrawer().setAnimating(false);
                doLayout(r);
                timer.stop();
                timer = null;
            } else {
                //��ͨ������һ֡�����ó���״̬�����²����۵����
                doLayout(r);
            }
        }

        private void doLayout(final double r) {
            tab.getDrawer().setRatio(r);

            //�����ǰ����˶���ʱ����̬�������������֣�����ڸ�������JScrolPane
            //��FolderPane��˵����Ҫ������Ҫʵʱ����ScrollBar
            Container parent = pane.getParent();

            if (parent != null) {
                parent.doLayout();
            }

            //�����Լ��ĳߴ粼��
            pane.doLayout();
        }

        //�����µ�λ�ú�͸����
        protected abstract double delta(double r, double d);

        //��ǰֵ�Ƿ���������糬��1.0����С����0
        protected abstract boolean overflow(double r);

        //��ǰ�˶��Ľ���ֵ
        protected abstract double bound();
    }

    //չ���õĶ��������¼�
    class ExpandingAction extends FolderAction {
        public ExpandingAction(Folder t, Timer timer) {
            super(t, timer);
        }

        protected double delta(double r, double d) {
            return r + d;
        } //����

        protected boolean overflow(double r) {
            return r > 1;
        } //����Ϊ1

        protected double bound() {
            return 1;
        } //����ֵλ1
    }

    //�۵�ʱ�õĶ��������¼�
    class FolderingAction extends FolderAction {
        public FolderingAction(Folder t, Timer timer) {
            super(t, timer);
        }

        protected double delta(double r, double d) {
            return r - d;
        } //�ݼ�

        protected boolean overflow(double r) {
            return r < 0;
        } //����Ϊ0

        protected double bound() {
            return 0;
        } //����ֵΪ0
    }
}
