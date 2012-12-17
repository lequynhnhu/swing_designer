
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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author William Chen
 */
public class EtchedBorderAdapter implements BorderAdapter {
    protected EtchedBorder border;
    public EtchedBorderAdapter(Border b){
        border=(EtchedBorder)b;
    }
    public BorderGroupModel getBorderProperties() {
        return new EtchedBorderModel(border);
    }
}
