/*
 * DesignerEditListener.java
 *
 * Created on 2007-8-5, 15:06:16
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import java.util.EventListener;


/**
 * ���������������ı༭�������ӿ�
 *
 * @author William Chen
 */
public interface DesignerEditListener extends EventListener {
    /**
     * ��������µ�����󴥷����¼�
     */
    void componentAdded(DesignerEvent evt);

    /**
     * ����ѡ��������ɾ��ʱ�������¼�
     */
    void componentDeleted(DesignerEvent evt);

    /**
     * ������������ȡ������������ʧ��ʱ�������¼�
     */
    void componentCanceled(DesignerEvent evt);

    /**
     * ��ѡ������������ʱ�������¼�
     */
    void componentCopyed(DesignerEvent evt);

    /**
     * ��ѡ������������ʱ�������¼�
     */
    void componentCut(DesignerEvent evt);

    /**
     * �����ƻ��߼��е�������������ճ������ƽ�����ʱ�������¼�
     */
    void componentPasted(DesignerEvent evt);

    /**
     * �������ϵ������ѡ��ʱ�������¼�
     */
    void componentSelected(DesignerEvent evt);
    
    /**
     * �������ϵ�������Ա��༭ʱ
     */
    void componentEdited(DesignerEvent evt);
    /**
     * �������ϵ�����ߴ緢���仯ʱ
     */
    void componentResized(DesignerEvent evt);
    /**
     * �������ϵ����λ�ñ��ƶ�ʱ
     */
    void componentMoved(DesignerEvent evt);
}
