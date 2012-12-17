/*
 * DesignerEvent.java
 *
 * Created on August 3, 2007, 5:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import java.awt.AWTEvent;
import java.awt.Component;

import java.util.ArrayList;


/**
 * 设计事件
 * @author William Chen
 */
public class DesignerEvent extends AWTEvent {
    private ArrayList<Component> affectedComponents;

    public DesignerEvent(Object source) {
        super(source, 0);
        affectedComponents = new ArrayList<Component>();
    }
    public void setAffectedComponent(Component comp){
        affectedComponents.clear();
        affectedComponents.add(comp);
    }
    public void setAddedComponent(Component comp) {
        affectedComponents.clear();
        affectedComponents.add(comp);
    }

    public void setSelectedComponents(ArrayList<Component> components) {
        affectedComponents.clear();
        affectedComponents.addAll(components);
    }

    public void setDeletedComponents(ArrayList<Component> components) {
        affectedComponents.clear();
        affectedComponents.addAll(components);
    }

    public void setCopyedComponents(ArrayList<Component> components) {
        affectedComponents.clear();
        affectedComponents.addAll(components);
    }

    public void setCutComponents(ArrayList<Component> components) {
        affectedComponents.clear();
        affectedComponents.addAll(components);
    }

    public void setPastedComponents(ArrayList<Component> components) {
        affectedComponents.clear();
        affectedComponents.addAll(components);
    }

    public void setDraggedComponents(ArrayList<Component> components) {
        affectedComponents.clear();
        affectedComponents.addAll(components);
    }

    public ArrayList<Component> getAffectedComponents() {
        return affectedComponents;
    }
}
