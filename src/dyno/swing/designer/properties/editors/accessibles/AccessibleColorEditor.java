/*
 * AccessibleColorEditor.java
 *
 * Created on 2007��8��13��, ����9:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.properties.editors.*;
import dyno.swing.designer.properties.wrappers.ColorWrapper;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JToggleButton;


/**
 *
 * @author William Chen
 */
public class AccessibleColorEditor extends BaseAccessibleEditor {
    private ColorPalette palette;
    private Color defaultColor;
    /** Creates a new instance of AccessibleColorEditor */
    public AccessibleColorEditor() {
        super(new ColorWrapper(), new ColorWrapper(), true);
    }
    public void setDefaultColor(Color c){
        this.defaultColor=c;
    }

    @Override
    protected ITextComponent createTextField() {
        return new ColorTextField();
    }
   
    protected void popupDialog() {
        if(palette==null){
            palette=new ColorPalette();
            palette.addDefaultAction(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    setResult(defaultColor);
                    palette.setVisible(false);
                }
            });
            palette.addCustomAction(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    chooseCustomColor();
                    palette.setVisible(false);
                }
            });
            palette.addColorAction(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    Color c=((ColorIcon)((JToggleButton)e.getSource()).getIcon()).getColor();
                    setResult(c);
                    palette.setVisible(false);
                }
            });            
        }
        palette.setChoosedColor((Color)getValue());
        palette.show(this, 0, getHeight());
    }

    @Override
    protected boolean isComboButton() {
        return true;
    }

    private void chooseCustomColor() throws HeadlessException {
        Color choosedColor = JColorChooser.showDialog(this, "Choose Color", (Color) getValue());
        setResult(choosedColor);
    }

    private void setResult(Color choosedColor) {
        if (choosedColor != null) {
            setValue(choosedColor);
            fireStateChanged();
        }
    }
}
