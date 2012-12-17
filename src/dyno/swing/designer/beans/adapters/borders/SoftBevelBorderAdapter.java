
/*
 * EmptyBorderAdapter.java
 * 
 * Created on 2007-8-27, 17:53:05
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.beans.BorderGroupModel;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author William Chen
 */
public class SoftBevelBorderAdapter extends BevelBorderAdapter {
    public SoftBevelBorderAdapter(Border b){
        super((SoftBevelBorder)b);
    }
    public BorderGroupModel getBorderProperties() {
        return new SoftBevelBorderModel((SoftBevelBorder)border);
    }
}
