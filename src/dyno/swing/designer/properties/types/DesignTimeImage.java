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
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author William Chen
 */
public class DesignTimeImage extends Image {

    private Image image;
    private String path;

    public DesignTimeImage() {
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (path == null) {
            this.path=null;
            this.image=null;
        } else {
            this.path = path;
            Image img = Toolkit.getDefaultToolkit().getImage(path);
            if (img != null) {
                this.image = img;
            } else {
                this.path = null;
                this.image = null;
            }
        }
    }

    @Override
    public int getWidth(ImageObserver observer) {
        if(image!=null)
            return image.getWidth(observer);
        return -1;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        if(image!=null)
            return image.getHeight(observer);
        return -1;
    }

    @Override
    public ImageProducer getSource() {
        return image.getSource();
    }

    @Override
    public Graphics getGraphics() {
        return image.getGraphics();
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return image.getProperty(name, observer);
    }
}
