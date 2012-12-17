/*
 * DimensionWrapper.java
 *
 * Created on August 14, 2007, 6:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;

import java.awt.Dimension;

import java.util.StringTokenizer;


/**
 *
 * @author William Chen
 */
public class DimensionWrapper implements Encoder, Decoder, SourceCoder {
    /** Creates a new instance of ColorWrapper */
    public DimensionWrapper() {
    }

    public Object decode(String txt) {
        if (Util.isStringNull(txt)) {
            return null;
        }

        txt = txt.trim();
        txt = txt.substring(1, txt.length() - 1).trim();

        StringTokenizer tokenizer = new StringTokenizer(txt, ",");

        return new Dimension(Integer.parseInt(tokenizer.nextToken().trim()),
            Integer.parseInt(tokenizer.nextToken().trim()));
    }

    public String encode(Object v) {
        if (v == null) {
            return null;
        }

        Dimension dim = (Dimension) v;

        return "[" + dim.width + ", " + dim.height + "]";
    }

    public void validate(String txt) throws ValidationException {
        if (Util.isStringNull(txt)) {
            return;
        }

        txt = txt.trim();

        if (txt.length() < 5) {
            throwFormatException();
        }

        char c = txt.charAt(0);

        if (c != '[') {
            throwFormatException();
        }

        c = txt.charAt(txt.length() - 1);

        if (c != ']') {
            throwFormatException();
        }

        txt = txt.substring(1, txt.length() - 1).trim();

        StringTokenizer tokenizer = new StringTokenizer(txt, ",");

        if (tokenizer.hasMoreTokens()) {
            try {
                Integer.parseInt(tokenizer.nextToken().trim());
            } catch (NumberFormatException nfe) {
                throwFormatException();
            }

            if (tokenizer.hasMoreTokens()) {
                try {
                    Integer.parseInt(tokenizer.nextToken().trim());
                } catch (NumberFormatException nfe) {
                    throwFormatException();
                }
            } else {
                throwFormatException();
            }
        } else {
            throwFormatException();
        }
    }

    private void throwFormatException() throws ValidationException {
        throw new ValidationException(
            "Point string takes form like: [width, height]!");
    }

    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        else{
            Dimension dim=(Dimension)value;
            return "new java.awt.Dimension("+dim.width+", "+dim.height+")";
        }
    }

}
