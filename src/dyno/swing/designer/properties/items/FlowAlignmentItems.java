/*
 * FlowAlignmentItems.java
 * 
 * Created on 2007-9-16, 23:34:49
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import java.awt.FlowLayout;

/**
 *
 * @author rehte
 */
public class FlowAlignmentItems implements ItemProvider{
    private static Item[] VALUE_ITEMS = {
        new Item("LEFT", FlowLayout.LEFT, "java.awt.FlowLayout.LEFT"), 
        new Item("RIGHT", FlowLayout.RIGHT, "java.awt.FlowLayout.RIGHT"), 
        new Item("CENTER", FlowLayout.CENTER, "java.awt.FlowLayout.CENTER"), 
        new Item("LEADING", FlowLayout.LEADING, "java.awt.FlowLayout.LEADING"), 
        new Item("TRAILING", FlowLayout.TRAILING, "java.awt.FlowLayout.TRAILING"), 
    };
    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }

}
