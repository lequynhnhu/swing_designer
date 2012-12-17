/*
 * SplitPaneOrientationWrapper.java
 *
 * Created on 2007-9-2, 13:23:46
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import dyno.swing.designer.properties.types.Item;
import javax.swing.JSplitPane;

/**
 *
 * @author William Chen
 */
public class SplitPaneOrientationItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("HORIZONTAL_SPLIT", JSplitPane.HORIZONTAL_SPLIT, "javax.swing.JSplitPane.HORIZONTAL_SPLIT"), 
        new Item("VERTICAL_SPLIT", JSplitPane.VERTICAL_SPLIT, "javax.swing.JSplitPane.VERTICAL_SPLIT")
    };

    public SplitPaneOrientationItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}