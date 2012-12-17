/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers.primitive;

import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.wrappers.Decoder;
import dyno.swing.designer.properties.wrappers.Encoder;
import dyno.swing.designer.properties.wrappers.SourceCoder;

/**
 *
 * @author William Chen
 */
public class ByteWrapper implements Encoder, Decoder, SourceCoder {

    public String encode(Object v) {
        if(v==null)
            return "0";
        return v.toString();
    }

    public Object decode(String txt) {
        if(txt==null)
            return Byte.valueOf((byte)0);
        return Byte.parseByte(txt);        
    }

    public void validate(String txt) throws ValidationException {
        try{
             Byte.parseByte(txt); 
        }catch(NumberFormatException nfe){
            throw new ValidationException(nfe.getMessage());
        }
    }

    public String getJavaCode(Object value) {
        if(value==null)
            return "(byte)0";
        return "(byte)"+value.toString();
    }

}
