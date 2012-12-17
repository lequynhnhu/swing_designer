/*
 * Container.java
 *
 * Created on 2007��8��11��, ����8:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import java.awt.Component;


/**
 *
 * @author William Chen
 */
public interface ContainerAdapter extends ComponentAdapter {

    /**
     * �÷���ֻ��isContainer����trueʱʹ�á��ڵ�ǰ�������λ�õ��������������µ������
     *
     * @param designer ��ǰ�����
     * @param x
     * @param y  ���λ�õ�����
     *
     * @return ��ӳɹ�����true������false
     */
    boolean acceptComponent(int x, int y);

    /**
     * ��ȡ����parent�ĵ�index����������������ʾ���������Ҫ��Ƶ����
     * @param parent ������
     * @param index �����������
     * @return ���parent�ĵ�index�������
     */
    Component getChild(int index);

    /**
     * ������״̬ʱ������ƶ���ĳ������Ϸ�ʱ������Ϊ������ṩ�Զ�������
     * ��Ҫ��������������ڷ����µ����ǰ�ṩ��λ����ʾ��ͨ���͵�ǰ�����������
     * �Ĳ��ֹ�������PainterЭ����Ϊ��������ṩ��λָʾ������JSplitPane��Painter
     * ��ΪҪ��ӵ�����ṩ������ʾ���Σ���BorderLayout��Painter����Ϊ���ֹ�������
     * BorderLayout�������ṩ���������е���ʾ���εȵȡ�
     *
     * @ return λ����ʾ��Ⱦ��������Ϊ�գ�Ϊ��ʱ��������ʾ
     */
    HoverPainter getPainter();
    Painter getAnchorPainter();
    /**
     * ��ȡ����parent��������������ĸ���
     * @return ����parent�����������
     */
    int getChildCount();

    /**
     * ������child������parent�е�������
     */
    int getIndexOfChild(Component child);

    /**
     * ��ʾparent�������child�����JTabbedPane��ʾĳ������ʾ������������
     */
    void showComponent(Component child);

    void addNextComponent(Component dragged);

    void addBefore(Component target, Component added);

    void addAfter(Component target, Component added);

    boolean canAcceptMoreComponent();

    boolean canAddBefore(Component hovering);

    boolean canAddAfter(Component hovering);

    ConstraintsGroupModel getLayoutConstraints(Component bean);

    GroupModel getLayoutProperties();
    /**
     * �ӵ�ǰ������ɾ�������bean�����⴦������JScrollPaneɾ��JViewport�������
     */
    void removeComponent(Component bean);
    
    String getAddComponentCode(Component bean);
    
    String getLayoutCode();
}