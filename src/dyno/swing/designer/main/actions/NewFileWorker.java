/*
 * NewFileWorker.java
 *
 * Created on 2007-9-8, 19:20:01
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.*;
import dyno.swing.designer.properties.types.Item;
import java.awt.Component;

/**
 *
 * @author rehte
 */
public class NewFileWorker extends ParserWorker {
    private String packager;
    private String className;
    private Item type;
    public NewFileWorker(DesigningPanel d, String pack, String cn, Item type) {
        super(d);
        this.packager=pack;
        this.className=cn;
        this.type=type;
    }

    protected Component doInBackground() throws Exception {
        seed.setPackager(packager);
        seed.setClassName(className);
        seed.setGuitype(type);
        seed.initSourceCode();
        return super.doInBackground();
    }

    @Override
    protected void done() {
        try {
            if (get() != null) {
                designer.setSource(seed.getSourceCode());
                designer.setDirty(true);
            }
        } catch (Exception ex) {
        }
        super.done();
    }
}