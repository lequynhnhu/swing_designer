
/*
 * CompoundBorderAdapter.java
 * 
 * Created on 2007-8-27, 23:13:09
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.beans.BorderAdapter;
import dyno.swing.designer.beans.BorderGroupModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author William Chen
 */
public class CompoundBorderAdapter implements BorderAdapter {
    protected CompoundBorder border;
    public CompoundBorderAdapter(Border b){
        border=(CompoundBorder)b;
    }
    public BorderGroupModel getBorderProperties() {
        return new CompoundBorderModel(border);
    }
}

