/*
 * DesignerTransferHandler.java
 *
 * Created on August 9, 2007, 1:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import javax.swing.TransferHandler;


/**
 *
 * @author William Chen
 */
public class DesignerTransferHandler extends TransferHandler {
    /** Creates a new instance of DesignerTransferHandler */
    public DesignerTransferHandler() {
        super("rootComponent");
    }
}
