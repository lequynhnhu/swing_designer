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
import dyno.swing.designer.properties.types.Item;
import javax.swing.SwingConstants;

/**
 *
 * @author William Chen
 */
public class OrientationItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("HORIZONTAL", SwingConstants.HORIZONTAL, "javax.swing.SwingConstants.HORIZONTAL"), 
        new Item("VERTICAL", SwingConstants.VERTICAL, "javax.swing.SwingConstants.VERTICAL")
    };

    public OrientationItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}