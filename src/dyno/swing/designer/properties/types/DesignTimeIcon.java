/*
 * DesignTimeIcon.java
 *
 * Created on 2007-8-19, 23:28:29
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.types;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author William Chen
 */
public class DesignTimeIcon implements Icon {

    private Icon icon;
    private String path;

    public DesignTimeIcon() {
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (getIcon() != null) {
            getIcon().paintIcon(c, g, x, y);
        }
    }

    public int getIconWidth() {
        if (getIcon() != null) {
            return getIcon().getIconWidth();
        }
        return 0;
    }

    public int getIconHeight() {
        if (getIcon() != null) {
            return getIcon().getIconHeight();
        }
        return 0;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (path == null) {
            this.path=null;
            this.icon=null;
        } else {
            this.path = path;
            Image img = Toolkit.getDefaultToolkit().getImage(path);
            if (img != null) {
                this.icon = new ImageIcon(img);
            } else {
                this.path = null;
                this.icon = null;
            }
        }
    }
}
