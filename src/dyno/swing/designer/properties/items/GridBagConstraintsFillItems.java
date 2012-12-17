/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;

/**
 *
 * @author William Chen
 */
public class GridBagConstraintsFillItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
            new Item("NONE", java.awt.GridBagConstraints.NONE, "java.awt.GridBagConstraints.NONE"),
            new Item("HORIZONTAL", java.awt.GridBagConstraints.HORIZONTAL, "java.awt.GridBagConstraints.HORIZONTAL"),
            new Item("VERTICAL", java.awt.GridBagConstraints.VERTICAL, "java.awt.GridBagConstraints.VERTICAL"),
            new Item("BOTH", java.awt.GridBagConstraints.BOTH, "java.awt.GridBagConstraints.BOTH")
    };

    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}
