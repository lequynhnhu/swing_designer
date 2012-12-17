/*
 * To change this template, choose Tools | Templates | Licenses | Default License
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author William Chen
 */
public class TextField extends JTextField implements ITextComponent {

    public void TextField() {
        setBorder(null);
        setOpaque(false);
    }

    public void setValue(Object v) {
    }

    @Override
    public void setEditable(boolean b) {
        super.setEditable(b);
        setBackground(Color.white);
    }    
}
