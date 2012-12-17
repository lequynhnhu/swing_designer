
/*
 * EmptyBorderAdapter.java
 * 
 * Created on 2007-8-27, 17:53:05
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.beans.BorderAdapter;
import dyno.swing.designer.beans.BorderGroupModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public class BevelBorderAdapter implements BorderAdapter {
    protected BevelBorder border;
    public BevelBorderAdapter(Border b){
        border=(BevelBorder)b;
    }
    public BorderGroupModel getBorderProperties() {
        return new BevelBorderModel(border);
    }
}
