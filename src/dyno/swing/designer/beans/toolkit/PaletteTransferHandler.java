/*
 * PaletteTransferHandler.java
 *
 * Created on 2007年8月9日, 下午11:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.toolkit;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;


/**
 *
 * @author William Chen
 */
public class PaletteTransferHandler extends TransferHandler {
    /** Creates a new instance of PaletteTransferHandler */
    public PaletteTransferHandler() {
        super("beanInfo");
    }

    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        return (comp != null) && comp instanceof BeanInfoToggleButton;
    }

    public boolean importData(JComponent comp, Transferable t) {
        return (comp != null) && comp instanceof BeanInfoToggleButton;
    }
}
