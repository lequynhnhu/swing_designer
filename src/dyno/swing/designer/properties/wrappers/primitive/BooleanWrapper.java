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
public class BooleanWrapper implements Encoder, Decoder, SourceCoder {

    public String encode(Object v) {
        if(v==null)
            return "false";
        Boolean bool=(Boolean)v;
        return bool.booleanValue()?"true":"false";
    }

    public Object decode(String txt) {
        if(txt==null)
            return false;
        return txt.equals("true");
    }

    public void validate(String txt) throws ValidationException {
        if(txt==null)
            throw new ValidationException("Boolean value should be either true or false!");
        if(!(txt.equals("true")||txt.equals("false")))
            throw new ValidationException("Boolean value should be either true or false!");
    }

    public String getJavaCode(Object value) {
        if(value==null)
            return "false";
        Boolean bool=(Boolean)value;
        return bool.booleanValue()?"true":"false";
    }

}
