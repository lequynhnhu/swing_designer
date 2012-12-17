/*
 * HorizontalAlignmentWrapper.java
 *
 * Created on 2007-8-19, 16:48:45
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import dyno.swing.designer.properties.types.Item;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author William Chen
 */
public class VerticalScrollBarPolicyItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("AS_NEEDED", ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, "javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED"), 
        new Item("NEVER", ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, "javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER"), 
        new Item("ALWAYS", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, "javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS")
    };

    public VerticalScrollBarPolicyItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}