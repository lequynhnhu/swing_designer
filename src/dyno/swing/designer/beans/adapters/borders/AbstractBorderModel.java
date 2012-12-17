
/*
 * AbstractBorderModel.java
 * 
 * Created on 2007-8-28, 0:40:28
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.beans.BorderGroupModel;

/**
 *
 * @author William Chen
 */
public abstract class AbstractBorderModel implements BorderGroupModel{

    public String getGroupName() {
        return "Properties";
    }

    public boolean isEditable(int row) {
        return true;
    }

    public boolean isValueSet(int row) {
        return false;
    }

    public boolean restoreDefaultValue(int row) {
        return false;
    }

    public String getPropertyDescription(int row) {
        return null;
    }
}
