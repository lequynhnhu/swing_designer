/*
 * IconWrapper.java
 *
 * Created on 2007-8-19, 23:45:56
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.types.DesignTimeImage;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author William Chen
 */
public class ImageWrapper implements Encoder, Decoder, SourceCoder {

    public Object decode(String txt) {
        if (Util.isStringNull(txt) || txt.equals("binaries")) {
            return null;
        } else {
            DesignTimeImage dti = new DesignTimeImage();
            dti.setPath(txt);
            return dti;
        }
    }

    public String encode(Object v) {
        if (v == null) {
            return "";
        }
        if (v instanceof DesignTimeImage) {
            DesignTimeImage dti = (DesignTimeImage) v;
            return dti.getPath();
        } else {
            return "binaries";
        }
    }

    public void validate(String txt) throws ValidationException {
    }

    @Override
    public String getJavaCode(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof DesignTimeImage) {
            String path = ((DesignTimeImage) value).getPath();
            return "java.awt.Toolkit.getDefaultToolkit().getImage(\"" + getOSPath(path) + "\")";
        } 
        return null;
    }

    String getOSPath(String path) {
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("win") != -1) {
            return replacePath(path);
        } else {
            return path;
        }
    }

    String replacePath(String path) {
        int start = 0;
        int slash;
        String newpath = "";
        while ((slash = path.indexOf(File.separatorChar, start)) != -1) {
            newpath += path.substring(start, slash) + File.separatorChar + File.separatorChar;
            start = slash + 1;
        }
        newpath += path.substring(start);
        return newpath;
    }
}