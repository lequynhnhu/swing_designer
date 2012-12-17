/*
 * ListSpinnerEditor.java
 *
 * Created on 2007-8-15, 8:11:23
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import java.util.List;

import javax.swing.SpinnerListModel;


/**
 *
 * @author William Chen
 */
public class ListSpinnerEditor extends SpinnerEditor {
    public ListSpinnerEditor(List values) {
        super(new SpinnerListModel(values));
    }

    public ListSpinnerEditor(Object[] values) {
        super(new SpinnerListModel(values));
    }
}
