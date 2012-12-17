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
import dyno.swing.designer.properties.types.DesignTimeIcon;
import java.io.File;
import java.lang.reflect.Field;
import javax.swing.ImageIcon;

/**
 *
 * @author William Chen
 */
public class IconWrapper implements Encoder, Decoder, SourceCoder {

    public Object decode(String txt) {
        if (Util.isStringNull(txt) || txt.equals("binaries")) {
            return null;
        } else {
            DesignTimeIcon dti = new DesignTimeIcon();
            dti.setPath(txt);
            return dti;
        }
    }

    public String encode(Object v) {
        if (v == null) {
            return "";
        }
        if (v instanceof DesignTimeIcon) {
            DesignTimeIcon dti = (DesignTimeIcon) v;
            return dti.getPath();
        } else if (v instanceof ImageIcon) {
            ImageIcon ii = (ImageIcon) v;
            String path = getImageIconFile(ii);
            if (path != null) {
                return path;
            } else {
                return "binaries";
            }
        } else {
            return "binaries";
        }
    }

    private String getImageIconFile(ImageIcon ii) {
        try {
            Field field = ImageIcon.class.getDeclaredField("filename");
            field.setAccessible(true);
            return (String) field.get(ii);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void validate(String txt) throws ValidationException {
    }

    @Override
    public String getJavaCode(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof DesignTimeIcon) {
            String path = ((DesignTimeIcon) value).getPath();
            return "new javax.swing.ImageIcon(\"" + getOSPath(path) + "\")";
        } else if (value instanceof ImageIcon) {
            String path = getImageIconFile((ImageIcon) value);
            if (path != null) {
                return "new javax.swing.ImageIcon(\"" + getOSPath(path) + "\")";
            }
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