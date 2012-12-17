/*
 * BorderConstraintsItems.java
 * 
 * Created on 2007-9-16, 14:59:02
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import java.awt.BorderLayout;

/**
 *
 * @author rehte
 */
public class BorderConstraintsItems implements ItemProvider{
    private static Item[]VALUE_ITEMS={
        new Item("North", BorderLayout.NORTH, "java.awt.BorderLayout.NORTH"),
        new Item("East", BorderLayout.EAST, "java.awt.BorderLayout.EAST"),
        new Item("West", BorderLayout.WEST, "java.awt.BorderLayout.WEST"),
        new Item("South", BorderLayout.SOUTH, "java.awt.BorderLayout.SOUTH"),
        new Item("Center", BorderLayout.CENTER, "java.awt.BorderLayout.CENTER")
    };
    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }

}
