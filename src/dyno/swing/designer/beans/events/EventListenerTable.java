/*
 * EventWhiteBoard.java
 *
 * Created on 2007-8-17, 14:34:00
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.events;

import java.util.ArrayList;
import java.util.EventListener;

/**
 *
 * @author William Chen
 */
public class EventListenerTable {

    protected ArrayList<EventListener> listeners;

    protected EventListenerTable() {
        listeners = new ArrayList<EventListener>();
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }
}
