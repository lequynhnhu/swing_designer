/*
 * PropertyGroup.java
 *
 * Created on 2007-8-18, 23:30:01
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.GroupModel;
import javax.swing.table.TableCellRenderer;

public class PropertyGroup {

    private GroupModel model;
    private String name;
    private boolean foldered;
    private GroupRenderer renderer1;
    private GroupRenderer renderer2;
    public PropertyGroup(String name, GroupModel model){
        this(name, model, false);
        renderer1=new GroupRenderer();
        renderer2=new GroupRenderer();
    }
    public PropertyGroup(String name, GroupModel model, boolean foldered) {
        this.name = name;
        this.model = model;
        this.foldered = foldered;
    }

    public GroupModel getModel() {
        return model;
    }

    public void setModel(GroupModel model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFoldered() {
        return foldered;
    }

    public void setFoldered(boolean foldered) {
        this.foldered = foldered;
    }
    public TableCellRenderer get1stRenderer(){
        return renderer1;
    }
    public TableCellRenderer get2stRenderer(){
        return renderer2;
    }
}
