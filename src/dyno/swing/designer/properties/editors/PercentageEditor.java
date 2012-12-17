/*
 * PercentageEditor.java
 *
 * Created on 2007-8-15, 7:59:44
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import java.text.NumberFormat;


/**
 *
 * @author William Chen
 */
public class PercentageEditor extends FormattedEditor {
    public PercentageEditor() {
        super(NumberFormat.getPercentInstance());
    }
}
