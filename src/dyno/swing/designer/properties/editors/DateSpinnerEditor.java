/*
 * DateSpinnerEditor.java
 *
 * Created on 2007-8-15, 8:09:30
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import javax.swing.SpinnerDateModel;


/**
 *
 * @author William Chen
 */
public class DateSpinnerEditor extends SpinnerEditor {
    public DateSpinnerEditor() {
        super(new SpinnerDateModel());
    }
}
