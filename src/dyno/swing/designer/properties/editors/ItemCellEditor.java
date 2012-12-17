/*
 * ItemEditor.java
 *
 * Created on 2007-8-15, 8:25:57
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.types.Item;
import dyno.swing.designer.properties.items.ItemProvider;
import java.util.Vector;


/**
 *
 * @author William Chen
 */
public class ItemCellEditor extends ComboEditor {
    public ItemCellEditor(ItemProvider provider){
        this(provider.getItems());
    }
    public ItemCellEditor(Item[] items) {
        super(items);
    }

    public ItemCellEditor(Vector<Item> items) {
        super(items);
    }

    public void setValue(Object value) {
        Item item = new Item("", value);
        comboBox.setSelectedItem(item);
    }

    public Object getValue() {
        Item item = (Item)comboBox.getSelectedItem();
        return item.getValue();
    }
}
