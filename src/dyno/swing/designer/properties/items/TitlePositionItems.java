/*
 * TitlePositionWrapper.java
 *
 * Created on 2007-8-28, 0:58:30
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import dyno.swing.designer.properties.types.Item;
import javax.swing.border.TitledBorder;

/**
 *
 * @author William Chen
 */
public class TitlePositionItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("ABOVE_TOP", TitledBorder.ABOVE_TOP, "javax.swing.border.TitledBorder.ABOVE_TOP"), 
        new Item("TOP", TitledBorder.TOP, "javax.swing.border.TitledBorder.TOP"), 
        new Item("BELOW_TOP", TitledBorder.BELOW_TOP, "javax.swing.border.TitledBorder.BELOW_TOP"), 
        new Item("ABOVE_BOTTOM", TitledBorder.ABOVE_BOTTOM, "javax.swing.border.TitledBorder.ABOVE_BOTTOM"), 
        new Item("BOTTOM", TitledBorder.BOTTOM, "javax.swing.border.TitledBorder.BOTTOM"), 
        new Item("BELOW_BOTTOM", TitledBorder.BELOW_BOTTOM, "javax.swing.border.TitledBorder.BELOW_BOTTOM"), 
        new Item("DEFAULT_POSITION", TitledBorder.DEFAULT_POSITION, "javax.swing.border.TitledBorder.DEFAULT_POSITION")
    };

    public TitlePositionItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}