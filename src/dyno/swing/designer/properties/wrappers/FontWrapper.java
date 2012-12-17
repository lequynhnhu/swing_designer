/*
 * FontWrapper.java
 *
 * Created on 2007年8月13日, 下午9:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;

import java.awt.Font;

import java.util.StringTokenizer;


/**
 *
 * @author William Chen
 */
public class FontWrapper implements Encoder, Decoder, SourceCoder {
    /** Creates a new instance of FontWrapper */
    public FontWrapper() {
    }

    public Object decode(String txt) {
        if (Util.isStringNull(txt)) {
            return null;
        }

        txt = txt.trim();
        txt = txt.substring(1, txt.length() - 1).trim();

        StringTokenizer tokenizer = new StringTokenizer(txt, ",");

        return new Font(tokenizer.nextToken().trim(),
            Integer.parseInt(tokenizer.nextToken().trim()),
            Integer.parseInt(tokenizer.nextToken().trim()));
    }

    public String encode(Object v) {
        if (v == null) {
            return null;
        }

        Font f = (Font) v;

        return "[" + f.getFamily() + ", " + f.getStyle() + ", " + f.getSize() +
        "]";
    }

    public void validate(String txt) throws ValidationException {
        if (Util.isStringNull(txt)) {
            return;
        }

        txt = txt.trim();

        if (txt.length() < 7) {
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
            tokenizer.nextToken();

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
    }

    private void throwFormatException() throws ValidationException {
        throw new ValidationException(
            "Font string takes form like: [family, style, size]!");
    }

    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        Font font=(Font)value;
        String f=font.getFamily();
        int style=font.getStyle();
        int size=font.getSize();
        return "new java.awt.Font(\""+f+"\", "+style+", "+size+")";
    }
}
