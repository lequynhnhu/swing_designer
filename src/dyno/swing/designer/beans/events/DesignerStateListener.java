/*
 * DesignerListener.java
 *
 * Created on August 3, 2007, 5:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import java.util.EventListener;


/**
 * ���������״̬�仯�Ľӿ�
 * @author William Chen
 */
public interface DesignerStateListener extends EventListener {
    /**
     * ����ƽ���������״̬ʱ�������¼�
     */
    void startDesigning(DesignerEvent evt);

    /**
     * ����ƽ��������ͨģʽʱ�������¼�
     */
    void stopDesigning(DesignerEvent evt);
}
