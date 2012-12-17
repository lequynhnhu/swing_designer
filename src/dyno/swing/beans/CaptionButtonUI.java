/*
 * CaptionButtonUI.java
 *
 * Created on June 9, 2007, 8:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;


/**
 * �����ʱCaptionButton�齨��UI�ࡣ
 * ��UI���Ǹ���״̬UI�ࡣ
 *
 * @author William Chen
 */
public class CaptionButtonUI extends ComponentUI implements MouseMotionListener,
    MouseListener, FocusListener {
    //ȱʡǰ��ɫ��ȱʡ����
    private static final Color DEFAULT_FOREGROUND = new Color(33, 93, 198);

    //����һЩȱʡ���Ե�ֵ��������ɫ�����塢�ߴ硢��϶��ȱʡͼ�ꡢ���߿��stroke�ȵȡ�
    private static final Color LIGHTER = new Color(255, 255, 255);
    private static final Color DARKER = new Color(198, 211, 247);
    private static final int TEXT_LEADING_GAP = 14;
    private static final int IMAGE_TAILING_GAP = 12;
    private static final Color HOVERED_COLOR = new Color(66, 142, 255);
    private static Stroke DASHED_STROKE = new BasicStroke(1,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1 },
            0);
    private static Icon iconExpanded;
    private static Icon iconFoldered;
    private static Icon hoveredExpanded;
    private static Icon hoveredFoldered;

    static {
        //��ʼ��
        iconExpanded = new ImageIcon(CaptionButtonUI.class.getResource(
                    "resources/expanded.png"));
        iconFoldered = new ImageIcon(CaptionButtonUI.class.getResource(
                    "resources/foldered.png"));
        hoveredExpanded = new ImageIcon(CaptionButtonUI.class.getResource(
                    "resources/hovered_expanded.png"));
        hoveredFoldered = new ImageIcon(CaptionButtonUI.class.getResource(
                    "resources/hovered_foldered.png"));
    }

    //�Ƿ����߿�
    private boolean armed;

    //������߼�϶
    private int textLeadingGap = TEXT_LEADING_GAP;

    //ͼ���ʱ��϶
    private int imageTailingGap = IMAGE_TAILING_GAP;

    //����ɫ��ʼɫ
    private Color lightColor = LIGHTER;

    //����ɫ����ɫ
    private Color darkColor = DARKER;

    //��ǰ����Ƿ񸡶�������
    private boolean hovered;

    //��긡���ڱ����Ϸ�ʱ�������ɫ
    private Color hoveredColor = HOVERED_COLOR;

    //��UIʵ����Ӧ��CaptionButton
    protected CaptionButton button;

    /** Creates a new instance of CaptionButtonUI */
    public CaptionButtonUI() {
    }

    public static ComponentUI createUI(JComponent c) {
        return new CaptionButtonUI();
    }

    //��װCaptionButton��LAF
    public void installUI(JComponent c) {
        //����ȱʡ����
        button = (CaptionButton) c;
        button.setForeground(DEFAULT_FOREGROUND);
        button.setFocusable(true);

        //����¼�������
        button.addMouseListener(this);
        button.addMouseMotionListener(this);
        button.addFocusListener(this);
    }

    //ж��UI    
    public void uninstallUI(JComponent c) {
        //ж���¼�������
        button.removeMouseListener(this);
        button.removeMouseMotionListener(this);
        button.removeFocusListener(this);
    }

    //��CaptionButton�ı������̳�CaptionButtonUI��������Ը��Ǹ÷����Զ��屳��
    protected void paintBackground(Graphics g) {
        int w = button.getWidth();
        int h = button.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(1, 1, lightColor, w - 2, 1,
                darkColor);
        g2d.setPaint(gp);
        g2d.fillRect(1, 1, w - 2, h - 1);
        gp = new GradientPaint(2, 0, lightColor, w - 4, 0, darkColor);
        g2d.setPaint(gp);
        g2d.fillRect(2, 0, w - 4, 1);
        g2d.setColor(lightColor);
        g2d.drawLine(0, 2, 0, h - 1);
        g2d.setColor(darkColor);
        g2d.drawLine(w - 1, 2, w - 1, h - 1);
    }

    public void paint(Graphics g, JComponent c) {
        paintBackground(g);
        paintCaptionText(g);
        paintIcon(g);

        if (armed) {
            paintArmed(g);
        }
    }

    //�����߿򣬼̳�CaptionButtonUI��������Ը��Ǹ÷����Զ��役�������
    protected void paintArmed(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(DASHED_STROKE);
        g2d.drawRoundRect(1, 1, button.getWidth() - 3, button.getHeight() - 3,
            2, 2);
    }

    //��������չ�����ر�ͼ�꣬�̳�CaptionButtonUI��������Ը��Ǹ÷����Զ���չ�����ر�ͼ��
    protected void paintIcon(Graphics g) {
        Icon icon = null;

        if (hovered) {
            icon = button.isExpanded() ? hoveredExpanded : hoveredFoldered;
        } else {
            icon = button.isExpanded() ? iconExpanded : iconFoldered;
        }

        int x = button.getWidth() - imageTailingGap - icon.getIconWidth();
        int y = (button.getHeight() - icon.getIconHeight()) / 2;
        icon.paintIcon(button, g, x, y);
    }

    //�����������֣��̳�CaptionButtonUI��������Ը��Ǹ÷����Զ������ֵ����
    protected void paintCaptionText(Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        if (button.getText() != null) {
            Color foreground = button.getForeground();
            Color color = hovered ? hoveredColor : foreground;
            g.setColor(color);

            int y = ((button.getHeight() - fm.getHeight()) / 2) +
                fm.getAscent();
            g.drawString(button.getText(), textLeadingGap, y);
        }
    }

    //����������¼�
    public void mousePressed(MouseEvent e) {
        //�Ļ����
        setArmed(true);
        //�۵�����չ��
        button.setExpanded(!button.isExpanded());

        //����ѡ���¼�
        ItemEvent evt = new ItemEvent(button, button.isExpanded() ? 0 : 1,
                button.getText(),
                button.isExpanded() ? ItemEvent.SELECTED : ItemEvent.DESELECTED);
        button.fireItemStateChanged(evt);
        //��ý���
        button.requestFocus();
    }

    //�������¼��������¼�
    public void mouseEntered(MouseEvent e) {
        //����
        setHovered(true);
        //��������
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    //����˳��¼�
    public void mouseExited(MouseEvent e) {
        //������ʧ
        setHovered(false);
    }

    void setHovered(boolean b) {
        hovered = b;
        button.repaint();
    }

    //������ʧ�¼�
    public void focusLost(FocusEvent e) {
        setArmed(false);
    }

    void setArmed(boolean b) {
        armed = b;
        button.repaint();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void focusGained(FocusEvent e) {
    }
}
