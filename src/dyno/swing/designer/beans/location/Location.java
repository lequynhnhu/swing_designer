/*
 * DraggingType.java
 *
 * Created on August 1, 2007, 9:50 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.location;

import java.awt.Component;

/**
 *
 * @author William Chen
 */
public enum Location {

    outer(new Outer()), inner(new Inner()), left_top(new LeftTop()), top(new Top()), right_top(new RightTop()), right(new Right()), right_bottom(new RightBottom()), bottom(new Bottom()), left_bottom(new LeftBottom()), left(new Left());
    private Direction direction;

    private Location(Direction l) {
        direction = l;
    }

    public void drag(int dx, int dy, Component comp) {
        direction.drag(dx, dy, comp);
    }

    public int getCursor() {
        return direction.getCursor();
    }
}