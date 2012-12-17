/*
 * HorizontalAlignmentWrapper.java
 *
 * Created on 2007-8-19, 16:48:45
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import javax.swing.JDesktopPane;

/**
 *
 * @author William Chen
 */
public class DesktopPaneDragModeItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("LIVE_DRAG_MODE", JDesktopPane.LIVE_DRAG_MODE, "javax.swing.JDesktopPane.LIVE_DRAG_MODE"), 
        new Item("OUTLINE_DRAG_MODE", JDesktopPane.OUTLINE_DRAG_MODE, "javax.swing.JDesktopPane.OUTLINE_DRAG_MODE")
    };

    public DesktopPaneDragModeItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}