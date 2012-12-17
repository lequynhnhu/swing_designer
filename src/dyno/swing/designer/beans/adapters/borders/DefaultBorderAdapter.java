
/*
 * DefaultBorderAdapter.java
 * 
 * Created on 2007-8-27, 16:53:06
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.adapters.borders;

import dyno.swing.designer.beans.BorderAdapter;
import dyno.swing.designer.beans.BorderGroupModel;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public class DefaultBorderAdapter implements BorderAdapter{
    protected Border border;
    public DefaultBorderAdapter(Border border){
        this.border=border;
    }
    public BorderGroupModel getBorderProperties() {
        return null;
    }

}
