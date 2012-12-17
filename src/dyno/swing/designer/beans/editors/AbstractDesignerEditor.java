/*
 * AbstractDesignerEditor.java
 * 
 * Created on 2007-8-17, 19:30:59
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.editors;

import dyno.swing.designer.beans.DesignerEditor;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author William Chen
 */
public abstract class AbstractDesignerEditor implements DesignerEditor{
    protected ArrayList<ChangeListener> listeners;
    /** Creates a new instance of TextEditor */
    public AbstractDesignerEditor() {
        listeners = new ArrayList<ChangeListener>();
    }
    protected void fireStateChanged() {
        ChangeEvent evt = new ChangeEvent(getEditorComponent());

        for (ChangeListener l : listeners)
            l.stateChanged(evt);
    }

    public void addChangeListener(ChangeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void removeChangeListener(ChangeListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

}
