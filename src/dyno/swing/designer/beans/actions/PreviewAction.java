/*
 * CutAction.java
 *
 * Created on 2007-9-8, 22:44:46
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.TopContainerAdapter;
import dyno.swing.designer.beans.events.DesignerEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

/**
 *
 * @author rehte
 */
public class PreviewAction extends AbstractDesignerAction {

    public PreviewAction(SwingDesigner designer) {
        super(designer);
    }

    protected String getName() {
        return "preview";
    }

    public void actionPerformed(ActionEvent e) {
        if (designer.isAddingMode()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        Component component = designer.getRootComponent();
        TopContainerAdapter adapter=(TopContainerAdapter)AdapterBus.getComponentAdapter(designer, component);
        adapter.preview();
    }

    protected String getDescription() {
        return "Preview Design";
    }

    public void componentDeleted(DesignerEvent evt) {
        setEnabled(false);
    }

    public void startDesigning(DesignerEvent evt) {
        setEnabled(false);
    }

    public void stopDesigning(DesignerEvent evt) {
        setEnabled(true);
    }
}