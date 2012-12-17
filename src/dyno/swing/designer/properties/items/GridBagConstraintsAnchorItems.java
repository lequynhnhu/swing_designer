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
public class GridBagConstraintsAnchorItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {new Item("CENTER", java.awt.GridBagConstraints.CENTER, "java.awt.GridBagConstraints.CENTER"),
            new Item("NORTH", java.awt.GridBagConstraints.NORTH, "java.awt.GridBagConstraints.NORTH"),
            new Item("NORTHEAST", java.awt.GridBagConstraints.NORTHEAST, "java.awt.GridBagConstraints.NORTHEAST"),
            new Item("EAST", java.awt.GridBagConstraints.EAST, "java.awt.GridBagConstraints.EAST"),
            new Item("SOUTHEAST", java.awt.GridBagConstraints.SOUTHEAST, "java.awt.GridBagConstraints.SOUTHEAST"),
            new Item("SOUTH", java.awt.GridBagConstraints.SOUTH, "java.awt.GridBagConstraints.SOUTH"),
            new Item("SOUTHWEST", java.awt.GridBagConstraints.SOUTHWEST, "java.awt.GridBagConstraints.SOUTHWEST"),
            new Item("WEST", java.awt.GridBagConstraints.WEST, "java.awt.GridBagConstraints.WEST"),
            new Item("NORTHWEST", java.awt.GridBagConstraints.NORTHWEST, "java.awt.GridBagConstraints.NORTHWEST"),
            new Item("PAGE_START", java.awt.GridBagConstraints.PAGE_START, "java.awt.GridBagConstraints.PAGE_START"),
            new Item("PAGE_END", java.awt.GridBagConstraints.PAGE_END, "java.awt.GridBagConstraints.PAGE_END"),
            new Item("LINE_START", java.awt.GridBagConstraints.LINE_START, "java.awt.GridBagConstraints.LINE_START"),
            new Item("LINE_END", java.awt.GridBagConstraints.LINE_END, "java.awt.GridBagConstraints.LINE_END"),
            new Item("FIRST_LINE_START", java.awt.GridBagConstraints.FIRST_LINE_START, "java.awt.GridBagConstraints.FIRST_LINE_START"),
            new Item("FIRST_LINE_END", java.awt.GridBagConstraints.FIRST_LINE_END, "java.awt.GridBagConstraints.FIRST_LINE_END"),
            new Item("LAST_LINE_START", java.awt.GridBagConstraints.LAST_LINE_START, "java.awt.GridBagConstraints.LAST_LINE_START"),
            new Item("LAST_LINE_END", java.awt.GridBagConstraints.LAST_LINE_END, "java.awt.GridBagConstraints.LAST_LINE_END"),
            new Item("BASELINE", java.awt.GridBagConstraints.BASELINE, "java.awt.GridBagConstraints.BASELINE"),
            new Item("BASELINE_LEADING", java.awt.GridBagConstraints.BASELINE_LEADING, "java.awt.GridBagConstraints.BASELINE_LEADING"),
            new Item("BASELINE_TRAILING", java.awt.GridBagConstraints.BASELINE_TRAILING, "java.awt.GridBagConstraints.BASELINE_TRAILING"),
            new Item("ABOVE_BASELINE", java.awt.GridBagConstraints.ABOVE_BASELINE, "java.awt.GridBagConstraints.ABOVE_BASELINE"),
            new Item("ABOVE_BASELINE_LEADING", java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING, "java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING"),
            new Item("ABOVE_BASELINE_TRAILING", java.awt.GridBagConstraints.ABOVE_BASELINE_TRAILING, "java.awt.GridBagConstraints.ABOVE_BASELINE_TRAILING"),
            new Item("BELOW_BASELINE", java.awt.GridBagConstraints.BELOW_BASELINE, "java.awt.GridBagConstraints.BELOW_BASELINE"),
            new Item("BELOW_BASELINE_LEADING", java.awt.GridBagConstraints.BELOW_BASELINE_LEADING, "java.awt.GridBagConstraints.BELOW_BASELINE_LEADING"),
    };

    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}
