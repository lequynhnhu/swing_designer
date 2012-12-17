/*
 * ColorWrapper.java
 *
 * Created on 2007年8月13日, 下午9:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;
import java.awt.Color;
import java.util.StringTokenizer;


/**
 *
 * @author William Chen
 */
public class ColorWrapper implements Encoder, Decoder, SourceCoder {

    /** Creates a new instance of ColorWrapper */
    public ColorWrapper() {
    }

    public Object decode(String txt) {
        if (Util.isStringNull(txt)) {
            return null;
        }
        if (txt.equals("null")) 
            return null;
        else if(txt.equals("black"))
            return Color.black;
        else if(txt.equals("blue"))
            return Color.blue;
        else if(txt.equals("cyan"))
            return Color.cyan;
        else if(txt.equals("darkGray"))
            return Color.darkGray;
        else if(txt.equals("gray"))
            return Color.gray;
        else if(txt.equals("green"))
            return Color.green;
        else if(txt.equals("lightGray"))
            return Color.lightGray;
        else if(txt.equals("magenta"))
            return Color.magenta;
        else if(txt.equals("orange"))
            return Color.orange;
        else if(txt.equals("pink"))
            return Color.pink;
        else if(txt.equals("red"))
            return Color.red;
        else if(txt.equals("white"))
            return Color.white;
        else if(txt.equals("yellow"))
            return Color.yellow;
        txt = txt.trim();
        txt = txt.substring(1, txt.length() - 1).trim();

        StringTokenizer tokenizer = new StringTokenizer(txt, ",");

        return new Color(Integer.parseInt(tokenizer.nextToken().trim()), Integer.parseInt(tokenizer.nextToken().trim()), Integer.parseInt(tokenizer.nextToken().trim()));
    }

    public String encode(Object v) {
        if (v == null) {
            return "null";
        }

        Color c = (Color) v;
        if(c.equals(Color.black))
            return "black";
        else if(c.equals(Color.blue))
            return "blue";
        else if(c.equals(Color.cyan))
            return "cyan";
        else if(c.equals(Color.darkGray))
            return "darkGray";
        else if(c.equals(Color.gray))
            return "gray";
        else if(c.equals(Color.green))
            return "green";
        else if(c.equals(Color.lightGray))
            return "lightGray";
        else if(c.equals(Color.magenta))
            return "magenta";
        else if(c.equals(Color.orange))
            return "orange";
        else if(c.equals(Color.pink))
            return "pink";
        else if(c.equals(Color.red))
            return "red";
        else if(c.equals(Color.white))
            return "white";
        else if(c.equals(Color.yellow))
            return "yellow";
        return "[" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + "]";
    }

    public void validate(String txt) throws ValidationException {
        if (Util.isStringNull(txt)) {
            return;
        }
        if (txt.equals("null")) 
            return;
        else if(txt.equals("black"))
            return;
        else if(txt.equals("blue"))
            return;
        else if(txt.equals("cyan"))
            return;
        else if(txt.equals("darkGray"))
            return;
        else if(txt.equals("gray"))
            return;
        else if(txt.equals("green"))
            return;
        else if(txt.equals("lightGray"))
            return;
        else if(txt.equals("magenta"))
            return;
        else if(txt.equals("orange"))
            return;
        else if(txt.equals("pink"))
            return;
        else if(txt.equals("red"))
            return;
        else if(txt.equals("white"))
            return;
        else if(txt.equals("yellow"))
            return;
        
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
    }

    private void throwFormatException() throws ValidationException {
        throw new ValidationException("Color string takes form like: [red, green, blue], or null, black, white, red, green, blue etc.!");
    }

    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        Color c=(Color)value;
        String prefix="java.awt.Color.";
        if(c.equals(Color.black))
            return prefix+"black";
        else if(c.equals(Color.blue))
            return prefix+"blue";
        else if(c.equals(Color.cyan))
            return prefix+"cyan";
        else if(c.equals(Color.darkGray))
            return prefix+"darkGray";
        else if(c.equals(Color.gray))
            return prefix+"gray";
        else if(c.equals(Color.green))
            return prefix+"green";
        else if(c.equals(Color.lightGray))
            return prefix+"lightGray";
        else if(c.equals(Color.magenta))
            return prefix+"magenta";
        else if(c.equals(Color.orange))
            return prefix+"orange";
        else if(c.equals(Color.pink))
            return prefix+"pink";
        else if(c.equals(Color.red))
            return prefix+"red";
        else if(c.equals(Color.white))
            return prefix+"white";
        else if(c.equals(Color.yellow))
            return prefix+"yellow";
        return "new java.awt.Color("+c.getRed()+", "+c.getGreen()+", "+c.getBlue()+")";
    }
}