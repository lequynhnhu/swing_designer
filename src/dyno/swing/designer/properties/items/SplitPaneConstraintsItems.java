/*
 * SplitPaneConstraintsItems.java
 * 
 * Created on 2007-9-16, 23:41:06
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;

/**
 *
 * @author rehte
 */
public class SplitPaneConstraintsItems implements ItemProvider{
    private static Item[] VALUE_ITEMS={
        new Item("left", "left", "\"left\""),
        new Item("right", "right", "\"right\""),
        new Item("top", "top", "\"top\""),
        new Item("bottom", "bottom", "\"bottom\"")
    };

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }

}
