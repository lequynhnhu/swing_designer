/*
 * OpenFileWorker.java
 *
 * Created on 2007-9-8, 19:18:42
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.*;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileWorker extends ParserWorker {

    private File file;

    public OpenFileWorker(DesigningPanel designer, File f) {
        super(designer);
        file = f;
    }

    protected Component doInBackground() throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            designer.setSource(content);
            seed.setSourceCode(content);
            Component comp = super.doInBackground();
            return comp;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
            }
        }
        return null;
    }

    @Override
    protected void done() {
        try {
            if (get() != null) {
                designer.setDirty(false);
            }
        } catch (Exception e) {
        }
        super.done();
    }
}