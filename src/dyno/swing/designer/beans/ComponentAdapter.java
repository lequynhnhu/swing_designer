/*
 * ComponentAdapter.java
 *
 * Created on 2007��5��2��, ����11:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import dyno.swing.designer.properties.ValidationException;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import java.beans.BeanInfo;
import java.util.ArrayList;

import javax.swing.JPopupMenu;


/**
 * ����������ӿڣ��ɿ�����BeanInfo����չ��
 * ��ҪĿ����Ϊ��������ṩ���������Ϊ
 *
 * @author William Chen
 */
public interface ComponentAdapter {
    /**
     * ��ȡ��Component������͵�BeanInfo
     * @return ��ȡ�������Ӧ��beanInfo
     */
    BeanInfo getBeanInfo();


    Image getIcon(int iconKind);
    /**
     * ��¡һ�������͵�������÷�����Ҫ���ڸ��ơ����к�ճ�����ʱʹ�á���¡ԭ���������Ҫ��
     * ��¡��������ԭʼ���������ͬ�����ԡ�
     *
     * @param sourceԴ���
     * @return ��¡���
     */
    Component clone();

    /**
     * �����ѡ�������ѡ����������ͺ�����ƽ����ϸ�������ƶ���������ǰҪ��������ͼ��
     * һ��ʹ����������ͼ�δ��档
     *
     * @param component Ҫ��ӵ����
     * @param g ��ǰ�������ͼ�������Ķ���
     */
    void paintComponentMascot(Graphics g);


    /**
     * ���������ƽ����ϵ��ʱ������������������������componentClick�������д���
     * ��Ҫ�����ڽ��JTabbedPane�����״̬ʱ�ܽ��������л���
     *
     * @param e ��ǰ����¼�
     *
     * @return �Ƿ�������������¼������������¼���Ԥʾ����ק��ѡ�����Ϊ�Ŀ�ʼ��Ӧ�÷���true���Ա������¼������������
     */
    boolean componentClicked(MouseEvent e);

    /**
     * ������ڴ����������Ҽ����ʱ���÷������������ĺ���������ṩ������Ӧ�Ĳ˵�
     *
     * @param ���������˵�������¼�
     *
     * @return �����˵�
     */
    JPopupMenu getContextPopupMenu(MouseEvent e);

    /**
     * Ϊ��ǰ��������������Ա��model, ���鷵��
     * @return BeanPropertyModel
     */
    ArrayList<GroupModel> getBeanPropertyModel();

    /**
     * Ϊ��ǰ��������¼����������鷵��
     */
    ArrayList<GroupModel> getEventPropertyModel();
    /**
     * �ṩ˫��������ı༭��
     * @param bean ���˫���ı�������
     * @param x
     * @param y ���������bean�ڵ�λ��
     * @return ����Ƶı༭��
     */
    DesignerEditor getDesignerEditor(int x, int y);

    /**
     * ����value����bean��˵�Ƿ���Ч
     * @param bean���༭�����
     * @param value�±༭��ֵ
     * @throws ���ֵ���Ϸ����׳����쳣���쳣�а���������Ϣ
     */
    void validateBeanValue(Object value)
        throws ValidationException;

    /**
     * ����bean��ֵ
     * @param ��Ҫ���µ�bean
     * @param �µ�value
     */
    void setBeanValue(Object value);

    /**
     * ��ȡ��ǰ�༭��bean��ֵ
     * @param bean ���༭��bean
     * @return ��ǰ���༭��bean��ֵ
     */
    Object getBeanValue();

    /**
     * ��ȡDesignerEditor�ı༭���õ�λ��
     * @param bean ���˫���ı�������
     * @param x
     * @param y ���������bean�ڵ�λ��
     * @return �༭��Ӧ�÷��õ�λ�ã������bean�����λ��
     */
    Rectangle getEditorBounds(int x, int y);
    /**
     * ʵ�������������������������г�ʼ��
     */
    void initialize();    
}
