/*
 * EnumerationWrapper.java
 *
 * Created on 2007-8-19, 17:02:34
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.properties.items.ItemProvider;
import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.types.Item;

/**
 *
 * @author William Chen
 */
public class ItemWrapper implements Encoder, Decoder, SourceCoder {

    private Item[] items;

    public ItemWrapper(ItemProvider provider) {
        this(provider.getItems());
    }

    public ItemWrapper(Item[] items) {
        this.items = items;
    }

    public Object decode(String txt) {
        for (Item item : items) {
            if (txt.equals(item.getName())) {
                return item.getValue();
            }
        }
        return null;
    }

    public String encode(Object v) {
        for (Item item : items) {
            if (item.getValue().equals(v)) {
                return item.getName();
            }
        }
        return null;
    }

    public void validate(String txt) throws ValidationException {
        for (Item item : items) {
            if (txt.equals(item.getName())) {
                return;
            }
        }
        throw new ValidationException("No such element:" + txt);
    }

    @Override
    public String getJavaCode(Object value) {
        for (Item item : items) {
            if (item.getValue().equals(value)) {
                return item.getCode();
            }
        }
        return null;
    }
}