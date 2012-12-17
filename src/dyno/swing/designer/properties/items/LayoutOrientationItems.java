/*
 * LayoutOrientationWrapper.java
 *
 * Created on 2007-8-29, 1:57:02
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import dyno.swing.designer.properties.types.Item;
import javax.swing.JList;

/**
 *
 * @author William Chen
 */
public class LayoutOrientationItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("VERTICAL", JList.VERTICAL, "javax.swing.JList.VERTICAL"), 
        new Item("HORIZONTAL_WRAP", JList.HORIZONTAL_WRAP, "javax.swing.JList.HORIZONTAL_WRAP"), 
        new Item("VERTICAL_WRAP", JList.VERTICAL_WRAP, "javax.swing.JList.VERTICAL_WRAP")
    };

    public LayoutOrientationItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}