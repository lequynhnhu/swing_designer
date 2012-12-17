/*
 * BooleanEditor.java
 *
 * Created on 2007年8月13日, 下午7:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.ValidationException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JCheckBox;


/**
 *
 * @author William Chen
 */
public class BooleanEditor extends AbstractPropertyEditor {
    private JCheckBox checkBox;

    /** Creates a new instance of TextEditor */
    public BooleanEditor() {
        checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    firePropertyChanged();
                }
            });
    }

    public void setValue(Object value) {
        Boolean b = (Boolean) value;
        checkBox.setSelected((b == null) ? false : b.booleanValue());
    }

    public Object getValue() {
        return checkBox.isSelected() ? Boolean.TRUE : Boolean.FALSE;
    }

    public Component getCustomEditor() {
        return checkBox;
    }

    @Override
    public void validateValue() throws ValidationException {
    }
}
