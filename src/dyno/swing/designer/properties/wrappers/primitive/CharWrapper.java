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
public class CharWrapper implements Encoder, Decoder, SourceCoder {

    public String encode(Object v) {
        if(v==null)
            return "\\0";
        return v.toString();
    }

    public Object decode(String txt) {
        if(txt==null||txt.length()==0)
            return '\0';
        if(txt.equals("\\0"))
            return '\0';
        else
            return txt.charAt(0);
    }

    public void validate(String txt) throws ValidationException {
        if(txt==null || txt.length()!=1)
            throw new ValidationException("Character should be 1 character long!");
    }

    public String getJavaCode(Object value) {
        if(value==null)
            return "\'\\0\'";
        else
            return "\'"+value.toString()+"\'";
    }

}
