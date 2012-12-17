/*
 * BorderItem.java
 *
 * Created on 2007-8-27, 20:20:36
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.wrappers.BorderWrapper;
import dyno.swing.designer.properties.wrappers.Encoder;
import javax.swing.border.Border;

public class BorderItem {
    private static Encoder border_wrapper=new BorderWrapper();
    private Border border;
    private String name;

    public BorderItem(Border b) {
        this.border = b;
        this.name = border_wrapper.encode(b);
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof BorderItem) {
            return ((BorderItem) o).name.equals(this.name);
        } else if (o instanceof Border) {
            BorderItem bi = new BorderItem((Border) o);
            return bi.name.equals(this.name);
        } else {
            return false;
        }
    }
}