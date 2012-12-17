/*
 * HorizontalAlignmentWrapper.java
 *
 * Created on 2007-8-19, 16:48:45
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import javax.swing.SwingConstants;
import dyno.swing.designer.properties.types.*;
/**
 *
 * @author William Chen
 */
public class VerticalAlignmentItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("TOP", SwingConstants.TOP, "javax.swing.SwingConstants.TOP"), 
        new Item("CENTER", SwingConstants.CENTER, "javax.swing.SwingConstants.CENTER"), 
        new Item("BOTTOM", SwingConstants.BOTTOM, "javax.swing.SwingConstants.BOTTOM")
    };
    public VerticalAlignmentItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}
