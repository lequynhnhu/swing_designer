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
public class IntegerWrapper implements Encoder, Decoder, SourceCoder {

    public String encode(Object v) {
        if (v == null) {
            return "0";
        }
        return v.toString();
    }

    public Object decode(String txt) {
        if (txt == null) {
            return Integer.valueOf(0);
        }
        return Integer.parseInt(txt);
    }

    public void validate(String txt) throws ValidationException {
        try {
            Integer.parseInt(txt);
        } catch (NumberFormatException nfe) {
            throw new ValidationException(nfe.getMessage());
        }
    }

    public String getJavaCode(Object value) {
        if (value == null) {
            return "0";
        }
        return value.toString();
    }
}
