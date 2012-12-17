/*
 * TabLayoutPolicyWrapper.java
 *
 * Created on 2007-9-2, 13:56:24
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import dyno.swing.designer.properties.types.Item;
import javax.swing.JTabbedPane;

/**
 *
 * @author William Chen
 */
public class TabLayoutPolicyItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("WRAP_TAB_LAYOUT", JTabbedPane.WRAP_TAB_LAYOUT, "javax.swing.JTabbedPane.WRAP_TAB_LAYOUT"), 
        new Item("SCROLL_TAB_LAYOUT", JTabbedPane.SCROLL_TAB_LAYOUT, "javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT")
    };

    public TabLayoutPolicyItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}