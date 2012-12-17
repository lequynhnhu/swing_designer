/*
 * SourceGenerationWorker.java
 * 
 * Created on 2007-9-17, 0:09:00
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.code.CodeSeed;
import dyno.swing.designer.main.DesigningPanel;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

/**
 *
 * @author rehte
 */
public class SourceWorker extends SwingWorker<String, Object> implements PropertyChangeListener {
    protected DesigningPanel designingPanel;
    protected SwingDesigner designer;
    protected JTextArea txtSource;
    protected CodeSeed seed;
    protected PrintWriter writer;
    
    public SourceWorker(DesigningPanel d) {
        this.designingPanel = d;
        this.designer = d.getDesigner();
        this.seed = d.getSeed();
        this.writer = d.getWriter();
        this.txtSource = d.getTxtSource();
        addPropertyChangeListener(this);
    }

    protected String doInBackground() throws Exception {
        return seed.generateSourceCode(designer.getRootComponent());
    }

    @Override
    protected void done() {
        try {
            String source = get();
            if (source != null) {
                txtSource.setText(source);
                designer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                designingPanel.setDirty(false);
            } else {
                designer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        } catch (Exception ex) {
            ex.printStackTrace(writer);
            designer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("state")) {
            StateValue value = (StateValue) evt.getNewValue();
            if (value == StateValue.STARTED) {
                designer.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
        }
    }
}