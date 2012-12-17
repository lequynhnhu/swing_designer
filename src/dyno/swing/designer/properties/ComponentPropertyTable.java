/*
 * PropertyTable.java
 *
 * Created on 2007年8月12日, 下午5:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.ComponentAdapter;
import dyno.swing.designer.beans.ContainerAdapter;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.events.DesignerEditAdapter;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.table.TableModel;


/**
 *
 * @author William Chen
 */
public class ComponentPropertyTable extends AbstractPropertyTable {

    private SwingDesigner designer;
    private Component current_bean;

    /** Creates a new instance of PropertyTable */
    public ComponentPropertyTable(SwingDesigner designer) {
        super();
        setDesigner(designer);
    }

    public void setBean(Object bean) {
        if (bean != null) {
            groups = new ArrayList<PropertyGroup>();
            if (bean instanceof Component) {
                ComponentAdapter adapter = AdapterBus.getComponentAdapter(designer, (Component) bean);
                ArrayList<GroupModel> groupModels = adapter.getBeanPropertyModel();
                for (GroupModel model : groupModels) {
                    groups.add(new PropertyGroup(model.getGroupName(), model));
                }
                Container container = ((Component) bean).getParent();
                if (container != null) {
                    ComponentAdapter ca = AdapterBus.getComponentAdapter(designer, container);
                    if (ca instanceof ContainerAdapter) {
                        ContainerAdapter containerAdapter = (ContainerAdapter) ca;
                        GroupModel m = containerAdapter.getLayoutConstraints((Component) bean);
                        if (m != null) {
                            groups.add(new PropertyGroup(m.getGroupName(), m));
                        }
                    }
                }
                if (adapter instanceof ContainerAdapter) {
                    ContainerAdapter cAdapter = (ContainerAdapter) adapter;
                    GroupModel m = cAdapter.getLayoutProperties();
                    if (m != null) {
                        groups.add(new PropertyGroup(m.getGroupName(), m));
                    }
                }
            }
            TableModel model = new BeanTableModel();
            setModel(model);
            this.current_bean = (Component) bean;
        } else {
            setModel(default_table_model);
            this.current_bean = null;
        }
        this.repaint();
    }

    private void setDesigner(SwingDesigner designer) {
        this.designer = designer;
        designer.addDesignerEditListener(new DesignerEditAdapter() {

            public void componentEdited(DesignerEvent evt) {
                Component component = evt.getAffectedComponents().get(0);
                setBean(component);
                repaint();
            }

            public void componentResized(DesignerEvent evt) {
                repaint();
            }

            public void componentMoved(DesignerEvent evt) {
                repaint();
            }

            public void componentSelected(DesignerEvent evt) {
                ArrayList<Component> components = evt.getAffectedComponents();
                if (!components.isEmpty()) {
                    setBean(components.get(0));
                } else {
                    setBean(null);
                }
            }
        });
    }

    protected void fireComponentEdited() {
        DesignerEvent evt = new DesignerEvent(designer);
        evt.setAffectedComponent(current_bean);
        designer.getEditListenerTable().fireComponentEdited(evt);
    }
}