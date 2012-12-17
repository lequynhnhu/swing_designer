/*
 * FileWorker.java
 *
 * Created on 2007-9-8, 19:24:01
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.beans.code.CodeSeed;
import dyno.swing.designer.main.*;
import java.awt.Component;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import javax.swing.SwingWorker;

/**
 *
 * @author William Chen
 */
public class ParserWorker extends SwingWorker<Component, Object> implements PropertyChangeListener {

    protected DesigningPanel designer;
    protected CodeSeed seed;
    protected PrintWriter writer;

    public ParserWorker(DesigningPanel d) {
        designer = d;
        this.seed = d.getSeed();
        this.writer = d.getWriter();
        addPropertyChangeListener(this);
    }

    protected Component doInBackground() throws Exception {
        Component comp = seed.generateComponent(writer);
        return comp;
    }

    @Override
    protected void done() {
        try {
            Component comp = get();
            if (comp != null) {
                designer.setRootComponent(comp);
                designer.refreshRightPalette();
                designer.setProgressVisible(false);
                designer.logging("Generating Designing Done!");
                designer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                designer.setProgressVisible(false);
                designer.logging("Generating Designing Failed!");
                designer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        } catch (Exception ex) {
            ex.printStackTrace(writer);
            designer.setProgressVisible(false);
            designer.logging("Generating Designing Failed!");
            designer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("state")) {
            StateValue value = (StateValue) evt.getNewValue();
            if (value == StateValue.STARTED) {
                designer.setProgressVisible(true);
                designer.logging("Generating Designing View ...");
                designer.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
        }
    }
}