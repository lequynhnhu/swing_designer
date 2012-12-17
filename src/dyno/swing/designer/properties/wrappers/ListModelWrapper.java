/*
 * ComboBoxModelWrapper.java
 *
 * Created on 2007-8-28, 0:16:54
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author William Chen
 */
public class ListModelWrapper implements Encoder, Decoder, SourceCoder {

    private Encoder elementEncoder;
    private Decoder elementDecoder;
    
    public ListModelWrapper() {
    }

    public ListModelWrapper(Encoder eEncoder, Decoder eDecoder) {
        this.elementEncoder = eEncoder;
        this.elementDecoder = eDecoder;
    }
    public Encoder getElementWrapper(){
        return elementEncoder;
    }
    public Object decode(String txt) {
        if (Util.isStringNull(txt)) {
            return new DefaultListModel();
        } else {
            StringTokenizer tokenizer = new StringTokenizer(txt, ",");
            DefaultListModel model = new DefaultListModel();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                token = token.trim();
                if (elementDecoder != null) {
                    Object object = elementDecoder.decode(token);
                    model.addElement(object);
                } else {
                    model.addElement(token);
                }
            }
            return model;
        }
    }

    public String encode(Object v) {
        if (v == null) {
            return "";
        } else {
            String txt = "";
            ListModel m = (ListModel) v;
            int size = m.getSize();
            for (int i = 0; i < size; i++) {
                Object object = m.getElementAt(i);
                if (elementEncoder != null) {
                    txt += elementEncoder.encode(object);
                } else {
                    txt += object;
                }
                if (i != size - 1) {
                    txt += ",";
                }
            }
            return txt;
        }
    }

    public void validate(String txt) throws ValidationException {
        if (!Util.isStringNull(txt)) {
            StringTokenizer tokenizer = new StringTokenizer(txt, ",");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                token = token.trim();
                if (elementDecoder != null) {
                    elementDecoder.validate(token);
                }
            }
        }        
    }

    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        ListModel dlm=(ListModel)value;
        String strlist="";
        for(int i=0;i<dlm.getSize();i++){
            if(i!=0)
                strlist+=", ";
            strlist+="\""+dlm.getElementAt(i)+"\"";
        }
        String code="new javax.swing.AbstractListModel() {String[] strings = {"+strlist + "};";
            code+="public int getSize() { return strings.length; }";
            code+="public Object getElementAt(int i) { return strings[i]; }";
        code+="}";
        return code;
    }
}