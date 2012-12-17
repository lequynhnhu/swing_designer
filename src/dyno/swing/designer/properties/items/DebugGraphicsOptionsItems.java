/*
 * DebugGraphicsOptionsWrapper.java
 *
 * Created on 2007-8-27, 15:53:40
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import javax.swing.DebugGraphics;

/**
 *
 * @author William Chen
 */
public class DebugGraphicsOptionsItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("NONE_OPTION",DebugGraphics.NONE_OPTION, "javax.swing.DebugGraphics.NONE_OPTION"), 
        new Item("NO_CHANGES", 0, "0"), 
        new Item("LOG_OPTION", DebugGraphics.LOG_OPTION, "javax.swing.DebugGraphics.LOG_OPTION"), 
        new Item("FLASH_OPTION", DebugGraphics.FLASH_OPTION, "javax.swing.DebugGraphics.FLASH_OPTION"), 
        new Item("BUFFERED_OPTION", DebugGraphics.BUFFERED_OPTION, "javax.swing.DebugGraphics.BUFFERED_OPTION")
    };

    public DebugGraphicsOptionsItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}