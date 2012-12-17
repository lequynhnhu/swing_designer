/*
 * AbstractCellEditor.java
 *
 * Created on 2007年8月13日, 下午8:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import java.awt.Graphics;
import java.awt.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;


/**
 *
 * @author William Chen
 */
public abstract class AbstractPropertyEditor implements ExtendedPropertyEditor {
    private ArrayList<PropertyChangeListener> listeners;

    public AbstractPropertyEditor() {
        listeners = new ArrayList<PropertyChangeListener>();
    }

    public boolean isPaintable() {
        return false;
    }

    public void paintValue(Graphics gfx, Rectangle box) {
    }

    public String getJavaInitializationString() {
        return null;
    }

    public String getAsText() {
        return null;
    }

    public void setAsText(String text) throws IllegalArgumentException {
    }

    public String[] getTags() {
        return null;
    }

    public boolean supportsCustomEditor() {
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    protected void firePropertyChanged() {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, null);

        for (PropertyChangeListener listener : listeners)
            listener.propertyChange(evt);
    }
    public void setDefaultValue(Object v){}
}
