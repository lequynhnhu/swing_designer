/*
 * StringSourceCoder.java
 *
 * Created on 2007-9-15, 22:07:52
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers.primitive;

import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.wrappers.*;
import java.io.BufferedReader;
import java.io.StringReader;

/**
 *
 * @author William Chen
 */
public class StringWrapper implements Encoder, Decoder, SourceCoder {

    @Override
    public String getJavaCode(Object value) {
        if (value == null) {
            return "null";
        } else {
            return "\"" + replaceCR((String) value) + "\"";
        }
    }

    String replaceCR(String string) {
        try {
            StringReader r = new StringReader(string);
            BufferedReader br = new BufferedReader(r);
            String line;
            String code = "";
            int count=0;
            while ((line = br.readLine()) != null) {
                if(count!=0)
                    code+="\\n";
                code += line;
                count++;
            }
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            return string;
        }
    }

    public String encode(Object v) {
        return (String)v;
    }

    public Object decode(String txt) {
        return txt;
    }

    public void validate(String txt) throws ValidationException {
    }
}