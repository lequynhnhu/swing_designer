/*
 * PaletteDraggingHandler.java
 *
 * Created on 2007年8月9日, 下午11:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.toolkit;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.event.MouseEvent;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputListener;


/**
 *
 * @author William Chen
 */
public class PaletteDraggingHandler implements MouseInputListener {

    private MouseEvent lastPressEvent;
    private SwingDesigner designer;
    /** Creates a new instance of PaletteDraggingHandler */
    public PaletteDraggingHandler(SwingDesigner designer) {
        this.designer=designer;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        lastPressEvent = e;
    }

    public void mouseReleased(MouseEvent e) {
        lastPressEvent = null;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (lastPressEvent == null) {
            return;
        }

        if (e.getPoint().distance(lastPressEvent.getPoint()) > 5) {
            BeanInfoToggleButton button = (BeanInfoToggleButton) e.getSource();
            TransferHandler handler = button.getTransferHandler();
            handler.exportAsDrag(button, lastPressEvent, TransferHandler.COPY);
            designer.startDraggingComponent(button);
            lastPressEvent = null;
        }
    }

    public void mouseMoved(MouseEvent e) {
    }
}
