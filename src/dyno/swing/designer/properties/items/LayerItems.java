/*
 * LayerWrapper.java
 *
 * Created on 2007-9-3, 22:31:21
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.wrappers.*;
import dyno.swing.designer.properties.types.Item;
import javax.swing.JLayeredPane;

/**
 *
 * @author William Chen
 */
public class LayerItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("FRAME_CONTENT_LAYER", JLayeredPane.FRAME_CONTENT_LAYER, "javax.swing.JLayeredPane.FRAME_CONTENT_LAYER"), 
        new Item("DEFAULT_LAYER", JLayeredPane.DEFAULT_LAYER, "javax.swing.JLayeredPane.DEFAULT_LAYER"), 
        new Item("PALETTE_LAYER", JLayeredPane.PALETTE_LAYER, "javax.swing.JLayeredPane.PALETTE_LAYER"), 
        new Item("MODAL_LAYER", JLayeredPane.MODAL_LAYER, "javax.swing.JLayeredPane.MODAL_LAYER"), 
        new Item("POPUP_LAYER", JLayeredPane.POPUP_LAYER, "javax.swing.JLayeredPane.POPUP_LAYER"), 
        new Item("DRAG_LAYER", JLayeredPane.DRAG_LAYER, "javax.swing.JLayeredPane.DRAG_LAYER")
    };

    public LayerItems() {
    }

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}