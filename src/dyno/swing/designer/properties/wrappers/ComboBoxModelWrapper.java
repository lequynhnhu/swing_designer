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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author William Chen
 */
public class ComboBoxModelWrapper implements Encoder, Decoder, SourceCoder {

    private Encoder elementEncoder;
    private Decoder elementDecoder;

    public ComboBoxModelWrapper() {
    }

    public ComboBoxModelWrapper(Encoder eEncoder, Decoder eDecoder) {
        this.elementEncoder = eEncoder;
        this.elementDecoder = eDecoder;
    }

    public Encoder getElementEncoder() {
        return elementEncoder;
    }

    public Decoder getElementDecoder() {
        return elementDecoder;
    }

    public Object decode(String txt) {
        if (Util.isStringNull(txt)) {
            return new DefaultComboBoxModel();
        } else {
            StringTokenizer tokenizer = new StringTokenizer(txt, ",");
            DefaultComboBoxModel model = new DefaultComboBoxModel();
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
            ComboBoxModel m = (ComboBoxModel) v;
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
        ComboBoxModel cbm=(ComboBoxModel)value;
        String strlist="";
        for(int i=0;i<cbm.getSize();i++){
            if(i!=0)
                strlist+=", ";
            strlist+="\""+cbm.getElementAt(i)+"\"";
        }
        return "new javax.swing.DefaultComboBoxModel(new Object[] {"+strlist + "})";
    }
}