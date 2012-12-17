/*
 * To change this template, choose Tools | Templates | Licenses | Default License
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import java.awt.event.ActionListener;

/**
 *
 * @author William Chen
 */
public interface ITextComponent {
    void setText(String text);
    String getText();
    void setEditable(boolean editable);
    void addActionListener(ActionListener l);
    void selectAll();
    void setValue(Object v);
}
