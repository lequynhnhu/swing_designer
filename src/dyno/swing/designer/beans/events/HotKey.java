/*
 * HotKey.java
 *
 * Created on August 1, 2007, 10:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import java.awt.event.KeyEvent;


/**
 * 热键定义
 * @author William Chen
 */
public class HotKey {
    public static final HotKey VK_COPY = new HotKey(KeyEvent.CTRL_MASK,
            KeyEvent.VK_C);
    public static final HotKey VK_CUT = new HotKey(KeyEvent.CTRL_MASK,
            KeyEvent.VK_X);
    public static final HotKey VK_PASTE = new HotKey(KeyEvent.CTRL_MASK,
            KeyEvent.VK_V);
    public static final HotKey VK_DELETE = new HotKey(0, KeyEvent.VK_DELETE);

    /**
     * 热键的modifier
     */
    private int modifiers;

    /**
     * 热键对应的keyCode
     */
    private int keyCode;

    public HotKey(int m, int code) {
        modifiers = m;
        keyCode = code;
    }

    @Override
    public int hashCode() {
        return (modifiers << 16) | keyCode;
    }

    public int getModifiers() {
        return modifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof HotKey)) {
            return false;
        }

        HotKey another = (HotKey) o;

        return (modifiers == another.getModifiers()) &&
        (keyCode == another.getKeyCode());
    }

    public int getKeyCode() {
        return keyCode;
    }
}
