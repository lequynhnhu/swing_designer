/*
 * SourceParser.java
 * 
 * Created on 2007-9-17, 0:35:11
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main.actions;

import dyno.swing.designer.main.DesigningPanel;

/**
 *
 * @author rehte
 */
public class SourceParser extends ParserWorker{

    public SourceParser(DesigningPanel d) {
        super(d);
    }

    @Override
    protected void done() {
        try{
            if(get()!=null){
                designer.setDirty(false);
            }
        }catch(Exception e){}
        super.done();
    }

}
