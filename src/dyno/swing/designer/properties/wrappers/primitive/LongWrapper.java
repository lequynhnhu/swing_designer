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
public class LongWrapper implements Encoder, Decoder, SourceCoder {


    public String encode(Object v) {
        if (v == null) {
            return "0";
        }
        return v.toString();
    }

    public Object decode(String txt) {
        if (txt == null) {
            return Long.valueOf(0);
        }
        return Long.parseLong(txt);
    }

    public void validate(String txt) throws ValidationException {
        try {
            Long.parseLong(txt);
        } catch (NumberFormatException nfe) {
            throw new ValidationException(nfe.getMessage());
        }
    }

    public String getJavaCode(Object value) {
        if (value == null) {
            return "0l";
        }
        return value.toString()+"l";
    }

}
