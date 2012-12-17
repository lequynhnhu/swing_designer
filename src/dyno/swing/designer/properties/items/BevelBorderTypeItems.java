/*
 * BevelBorderTypeWrapper.java
 *
 * Created on 2007-8-27, 21:56:22
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import javax.swing.border.BevelBorder;

/**
 *
 * @author William Chen
 */
public class BevelBorderTypeItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("RAISED", BevelBorder.RAISED, "javax.swing.border.BevelBorder.RAISED"), 
        new Item("LOWERED", BevelBorder.LOWERED, "javax.swing.border.BevelBorder.LOWERED")
    };

    public BevelBorderTypeItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}