/*
 * ComboBoxItemDialog.java
 *
 * Created on 2007年8月28日, 上午1:10
 */

package dyno.swing.designer.properties.editors;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author  William Chen
 */
public class ListModelDialog extends ListDialog {
    /** Creates new form ComboBoxItemDialog */
    public ListModelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    protected ListModel createListModel(){
        return new DefaultListModel();
    }
    protected void addModelElement(ListModel model, Object object){
        ((DefaultListModel)model).addElement(object);
    }
    protected String getModelTitle(){
        return "List Items Editing Dialog";
    }
}