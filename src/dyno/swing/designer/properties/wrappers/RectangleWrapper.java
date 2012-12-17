/*
 * RectangleWrapper.java
 *
 * Created on August 14, 2007, 6:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Rectangle;
import java.util.StringTokenizer;


/**
 *
 * @author William Chen
 */
public class RectangleWrapper implements Encoder, Decoder, SourceCoder {

    /** Creates a new instance of ColorWrapper */
    public RectangleWrapper() {
    }

    public Object decode(String txt) {
        if (Util.isStringNull(txt)) {
            return null;
        }

        txt = txt.trim();
        txt = txt.substring(1, txt.length() - 1).trim();

        StringTokenizer tokenizer = new StringTokenizer(txt, ",");

        return new Rectangle(Integer.parseInt(tokenizer.nextToken().trim()), Integer.parseInt(tokenizer.nextToken().trim()), Integer.parseInt(tokenizer.nextToken().trim()), Integer.parseInt(tokenizer.nextToken().trim()));
    }

    public String encode(Object v) {
        if (v == null) {
            return null;
        }

        Rectangle r = (Rectangle) v;

        return "[" + r.x + ", " + r.y + ", " + r.width + ", " + r.height + "]";
    }

    public void validate(String txt) throws ValidationException {
        if (Util.isStringNull(txt)) {
            return;
        }

        txt = txt.trim();

        if (txt.length() < 9) {
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
            } else {
                throwFormatException();
            }
        } else {
            throwFormatException();
        }
    }

    private void throwFormatException() throws ValidationException {
        throw new ValidationException("Rectangle string takes form like: [X, Y, width, height]!");
    }

    @Override
    public String getJavaCode(Object value) {
        if (value == null) {
            return "null";
        }
        Rectangle rect = (Rectangle) value;
        return "new java.awt.Rectangle(" + rect.x + ", " + rect.y + ", " + rect.width + ", " + rect.height + ")";
    }
}