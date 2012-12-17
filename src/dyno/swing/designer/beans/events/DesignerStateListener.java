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
 * 处理设计器状态变化的接口
 * @author William Chen
 */
public interface DesignerStateListener extends EventListener {
    /**
     * 当设计界面进入添加状态时发出的事件
     */
    void startDesigning(DesignerEvent evt);

    /**
     * 当设计界面进入普通模式时发出的事件
     */
    void stopDesigning(DesignerEvent evt);
}
