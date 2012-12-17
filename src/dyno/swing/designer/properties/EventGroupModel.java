/*
 * EventGroupModel.java
 *
 * Created on 2007-9-5, 22:39:44
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.editors.EventCodeEditor;
import dyno.swing.designer.properties.renderer.EventHandlerRenderer;
import dyno.swing.designer.properties.types.EventHandler;
import java.awt.Component;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author William Chen
 */
public class EventGroupModel implements GroupModel, Comparable<EventGroupModel> {

    private SwingDesigner designer;
    private Component component;
    private String groupName;
    private EventHandlerRenderer renderer;
    private PropertyCellEditor editor;
    private HashMap<String, EventHandler> handlers;
    private EventSetDescriptor eventSet;
    private MethodDescriptor[] methods;

    public EventGroupModel(SwingDesigner d, Component c, EventSetDescriptor eSet) {
        this.designer = d;
        this.component = c;
        this.eventSet = eSet;
        this.groupName = eSet.getName();
        this.methods = eSet.getListenerMethodDescriptors();
        this.renderer = new EventHandlerRenderer();
        this.editor = new PropertyCellEditor(new EventCodeEditor());
        JComponent jcomponent=AdapterBus.getJComponent(component);
        HashMap<EventSetDescriptor, HashMap<String, EventHandler>>  eventHandlers = (HashMap<EventSetDescriptor, HashMap<String, EventHandler>>) jcomponent.getClientProperty("event.handler");
        if (eventHandlers == null) {
            eventHandlers = new HashMap<EventSetDescriptor, HashMap<String, EventHandler>> ();
            jcomponent.putClientProperty("event.handler", eventHandlers);
        }        
        this.handlers = eventHandlers.get(eSet);
        if (this.handlers == null) {
            this.handlers = new HashMap<String, EventHandler>();
            eventHandlers.put(eSet, handlers);
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public int getRowCount() {
        return methods.length;
    }

    public TableCellRenderer getRenderer(int row) {
        return renderer;
    }

    public TableCellEditor getEditor(int row) {
        return editor;
    }

    public Object getValue(int row, int column) {
        if (column == 0) {
            return methods[row].getName();
        } else {
            String name = methods[row].getName();
            EventHandler handler = handlers.get(name);
            if (handler == null) {
                handler = new EventHandler(methods[row].getName());
                handlers.put(name, handler);
            }
            return handler;
        }
    }

    public boolean setValue(Object value, int row, int column) {
        if (column == 0) {
            return false;
        } else {
            String name = methods[row].getName();
            EventHandler h = (EventHandler) value;
            if (h != null) {
                handlers.put(name, h);
            }
            return true;
        }
    }

    public boolean isEditable(int row) {
        return true;
    }

    public boolean isValueSet(int row) {
        String name = methods[row].getName();
        EventHandler h = (EventHandler) handlers.get(name);
        if (h == null) {
            return false;
        }
        String code=h.getCode_buffer();
        return !Util.isStringNull(code);
    }

    public boolean restoreDefaultValue(int row) {
        String name = methods[row].getName();
        EventHandler h = (EventHandler) handlers.get(name);
        if (h != null) {
            h.setCode_buffer(null);
        }
        return true;
    }

    public String getPropertyDescription(int row) {
        return null;
    }

    public int compareTo(EventGroupModel o) {
        if (o == null) {
            return 1;
        } else {
            String gn = o.getGroupName();
            if (gn == null) {
                if (groupName == null) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (groupName == null) {
                    return -1;
                } else {
                    return groupName.compareTo(gn);
                }
            }
        }
    }
}