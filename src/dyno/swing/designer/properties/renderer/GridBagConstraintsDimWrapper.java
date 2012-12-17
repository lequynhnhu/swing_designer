/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.renderer;

import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.wrappers.Decoder;
import dyno.swing.designer.properties.wrappers.Encoder;
import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.GridBagConstraints;

/**
 *
 * @author rehte
 */
public class GridBagConstraintsDimWrapper implements Encoder, Decoder, SourceCoder {

    private boolean zero;

    public GridBagConstraintsDimWrapper() {
    }

    public GridBagConstraintsDimWrapper(boolean b) {
        zero = b;
    }

    public String encode(Object v) {
        int size = v == null ? 0 : ((Number) v).intValue();
        if (size == -1) {
            return "RELATIVE";
        } else if (size == 0) {
            return zero ? "0" : "REMAINDER";
        } else {
            return "" + size;
        }
    }

    public Object decode(String txt) {
        if (txt.equals("RELATIVE")) {
            return -1;
        } else if (txt.equals("REMAINDER")) {
            return 0;
        } else {
            return Integer.parseInt(txt);
        }
    }

    public void validate(String txt) throws ValidationException {
        if (txt.equals("RELATIVE")) {
        } else if (txt.equals("REMAINDER")) {
        } else {
            try{
            Integer.parseInt(txt);
            }catch(Exception e){
                throw new ValidationException("It should be RELATIVE, REMAINDER or an integer bigger and equate than -1.");
            }
        }
    }

    public String getJavaCode(Object value) {
        if(value==null)
            value=GridBagConstraints.RELATIVE;
        int v=((Number)value).intValue();
        if(v == -1)
            return "java.awt.GridBagConstraints.RELATIVE";
        else if(v == 0)
            return zero?"0":"java.awt.GridBagConstraints.REMAINDER";
        else
            return value.toString();
    }
}
