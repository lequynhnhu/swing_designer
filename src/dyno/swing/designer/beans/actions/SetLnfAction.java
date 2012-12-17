/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.actions;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.Action;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author William Chen
 */
public class SetLnfAction extends AbstractContextAction implements ActionCategory {

    private Action[] subActions;

    public SetLnfAction(SwingDesigner designer) {
        super(designer);
        putValue(NAME, "Set LookAndFeel");
    }

    public void actionPerformed(ActionEvent e) {
    }

    public Action[] getSubActions() {
        if (subActions == null) {
            ArrayList<Action> arrays=new ArrayList<Action>();
            arrays.add(new MetalLnfAction(designer));
            arrays.add(new SystemLnfAction(designer));
            initLnfActions(arrays);
            subActions=(Action[])arrays.toArray(new Action[0]);            
        }
        return subActions;
    }

    private void initLnfActions(ArrayList<Action> arrays) {
        try {
            InputStream lnf_input = getClass().getResourceAsStream("lnfs.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document=builder.parse(lnf_input);
            Element root=document.getDocumentElement();
            NodeList list=root.getElementsByTagName("LookAndFeel");
            if(list!=null && list.getLength()>0){
                for(int i=0;i<list.getLength();i++){
                    Element eLnf=(Element)list.item(i);
                    String name=eLnf.getAttribute("name");
                    String className=eLnf.getAttribute("class");
                    GenericLnfAction action=new GenericLnfAction(designer, className, name);
                    arrays.add(action);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
