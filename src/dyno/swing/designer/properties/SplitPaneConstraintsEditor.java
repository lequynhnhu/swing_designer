/*
 * BorderConstraintsEditor.java
 * 
 * Created on 2007-8-26, 22:33:13
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.properties.items.SplitPaneConstraintsItems;

/**
 *
 * @author William Chen
 */
public class SplitPaneConstraintsEditor extends EnumerationEditor{
    public SplitPaneConstraintsEditor(){
        super(new SplitPaneConstraintsItems());
    }
}
