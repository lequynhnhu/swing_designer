/*
 * SpinnerModelDialog.java
 *
 * Created on 2007-9-1, 11:27:21
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.editors.accessibles.AccessibleDialog;
import dyno.swing.designer.properties.types.Item;
import java.awt.Frame;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author William Chen
 */
public class SpinnerModelDialog extends AccessibleDialog {

    private Item[] accessibleItems;

    public SpinnerModelDialog(Frame frame, boolean modal) {
        super(frame, modal);
    }

    @Override
    protected Item[] getAccessibleItems() {
        if (accessibleItems == null) {
            initializeItems();
        }
        return accessibleItems;
    }

    @Override
    public void setValue(Object v) {
        super.setValue(v);
        if (v instanceof SpinnerDateModel) {
            selectItem(1);
        } else if (v instanceof SpinnerListModel) {
            selectItem(2);
        } else if (v instanceof SpinnerNumberModel) {
            selectItem(3);
        } else {
            selectItem(0);
        }
    }

    private void initializeItems() {
        accessibleItems = new Item[]{
            new Item("Default", null),
            new Item("Date", new SpinnerDateModelEditor()), 
            new Item("List", new SpinnerListModelEditor()), 
            new Item("Number", new SpinnerNumberModelEditor())
        };
    }
}