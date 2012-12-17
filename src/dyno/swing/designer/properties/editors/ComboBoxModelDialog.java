/*
 * ComboBoxItemDialog.java
 *
 * Created on 2007年8月28日, 上午1:10
 */

package dyno.swing.designer.properties.editors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;

/**
 *
 * @author  William Chen
 */
public class ComboBoxModelDialog extends ListDialog {
    /** Creates new form ComboBoxItemDialog */
    public ComboBoxModelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    protected ListModel createListModel(){
        return new DefaultComboBoxModel();
    }
    protected void addModelElement(ListModel model, Object object){
        ((MutableComboBoxModel)model).addElement(object);
    }

    protected String getModelTitle() {
        return "Combo Box Items Editing Dialog";
    }
}