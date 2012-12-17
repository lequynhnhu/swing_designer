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
 * 界面设计组件触发的编辑处理器接口
 *
 * @author William Chen
 */
public interface DesignerEditListener extends EventListener {
    /**
     * 当添加了新的组件后触发的事件
     */
    void componentAdded(DesignerEvent evt);

    /**
     * 当所选择的组件被删除时触发的事件
     */
    void componentDeleted(DesignerEvent evt);

    /**
     * 添加组件动作被取消或者添加组件失败时发出的事件
     */
    void componentCanceled(DesignerEvent evt);

    /**
     * 当选择的组件被复制时发出的事件
     */
    void componentCopyed(DesignerEvent evt);

    /**
     * 当选择的组件被剪切时发出的事件
     */
    void componentCut(DesignerEvent evt);

    /**
     * 当复制或者剪切到剪贴板的组件被粘帖到设计界面上时发出的事件
     */
    void componentPasted(DesignerEvent evt);

    /**
     * 当界面上的组件被选择时发出的事件
     */
    void componentSelected(DesignerEvent evt);
    
    /**
     * 当界面上的组件属性被编辑时
     */
    void componentEdited(DesignerEvent evt);
    /**
     * 当界面上的组件尺寸发生变化时
     */
    void componentResized(DesignerEvent evt);
    /**
     * 当界面上的组件位置被移动时
     */
    void componentMoved(DesignerEvent evt);
}
