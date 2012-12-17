/*
 * EtchedBorderTypeWrapper.java
 *
 * Created on 2007-8-27, 23:22:46
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author William Chen
 */
public class EtchedBorderTypeItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("RAISED", EtchedBorder.RAISED, "javax.swing.border.EtchedBorder.RAISED"), 
        new Item("LOWERED", EtchedBorder.LOWERED, "javax.swing.border.EtchedBorder.LOWERED")
    };

    public EtchedBorderTypeItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}