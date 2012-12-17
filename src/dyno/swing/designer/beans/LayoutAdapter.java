/*
 * LayoutAdapter.java
 *
 * Created on 2007��5��2��, ����11:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import java.awt.Component;

/**
 * �ýӿ���LayoutManager��BeanInfo�ࡣ��׼Javaƽ̨û���ṩ���ֹ�������BeanInfo�࣬
 * ���ڽ�����ƹ�����˵����һЩ�������Ϊ��
 *
 * @author William Chen
 */
public interface LayoutAdapter {
    /**
     * ��������״̬ʱ��������ƶ���ĳ�������Ϸ�ʱ������������в��ֹ������������øò���
     * ������������accept��������ǰλ���Ƿ���Է��ã����ṩ����ı�ʶ�������ɫ�����ʶ����
     * ����BorderLayout�У����ĳ����λ�Ѿ���������������ʱӦ�÷���false��ʶ�����򲻿���
     * ���á�
     *
     * @param container ��������������container
     * @param bean ����ӵ������
     * @param x ��ӵ�λ��x����λ���������container��
     * @param y ��ӵ�λ��y����λ���������container��
     * @return �Ƿ���Է��á�
     */
    boolean accept(Component bean, int x, int y);

    /**
     * �����ComponentAdapter��������ʱ��������ֲ��ֹ�������Ϊ�գ���̶����øò��ֹ�������
     * addBean�������������ľ�����ӡ��ڸ÷����ڣ����ֹ����������ṩ����Ĺ��ܡ�
     * @param container ��������������container
     * @param bean ����ӵ������
     * @param x ��ӵ�λ��x����λ���������container��
     * @param y ��ӵ�λ��y����λ���������container��
     * @return �Ƿ���ӳɹ����ɹ�����true������false
     */
    boolean addBean(Component bean, int x, int y);

    /**
     * ���ظò��ֹ�����������Painter��Ϊ�����ṩ����λ�õı�ʶ��
     *
     */
    HoverPainter getPainter();

    Painter getAnchorPainter();
    /**
     * ��ʾparent�������child�����CardLayout����ʾĳ������ʾ������������
     */
    void showComponent(Component child);

    void addNextComponent(Component dragged);

    void addBefore(Component target, Component added);

    void addAfter(Component target, Component added);

    boolean canAcceptMoreComponent();
    
    ConstraintsGroupModel getLayoutConstraints(Component bean);
    
    GroupModel getLayoutProperties();
    
    String getLayoutCode();
}
