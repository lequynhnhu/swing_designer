/*
 * CaptionButton.java
 *
 * Created on June 8, 2007, 5:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.beans;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.ArrayList;

import javax.swing.JComponent;


/**
 * ��������۵����ı��������������һ������toggle-button�������
 *
 * @author William Chen
 */
public class CaptionButton extends JComponent implements ItemSelectable {
    // �¼�����������
    private ArrayList<ItemListener> listeners = new ArrayList<ItemListener>();

    //�Ƿ�չ��
    private boolean expanded;

    //��������
    private String text;

    /** Creates a new instance of CaptionButton */
    public CaptionButton() {
        this(null, true);
    }

    /**
     * @param text ����
     * @expanded Ŀǰ�Ƿ�չ��
     */
    public CaptionButton(String text, boolean expanded) {
        this.text = text;
        this.expanded = expanded;
        setUI(new CaptionButtonUI());
    }

    //���ѡ���¼�������
    public void addItemListener(ItemListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    //ɾ��ѡ���¼�������
    public void removeItemListener(ItemListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    //�����¼�������
    protected void fireItemStateChanged(ItemEvent e) {
        for (ItemListener l : listeners)
            l.itemStateChanged(e);
    }

    //
    public Object[] getSelectedObjects() {
        if (!expanded) {
            return null;
        }

        return new Object[] { text };
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        repaint();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }
}
