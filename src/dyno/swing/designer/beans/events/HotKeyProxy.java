/*
 * DesignerKeyListener.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.events;

import dyno.swing.designer.beans.*;
import dyno.swing.designer.beans.keys.CopyKey;
import dyno.swing.designer.beans.keys.CutKey;
import dyno.swing.designer.beans.keys.DeletionKey;
import dyno.swing.designer.beans.keys.PasteKey;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;


/**
 * 进行热键转发的代理处理器
 * @author William Chen
 */
public class HotKeyProxy implements KeyListener {
    private HashMap<HotKey, KeyListener> hotKeyProxies;
    /** Creates a new instance of DesignerKeyListener */
    public HotKeyProxy(SwingDesigner designer) {
        hotKeyProxies = new HashMap<HotKey, KeyListener>();
        //剪切、复制、粘帖、删除对应的处理器
        hotKeyProxies.put(HotKey.VK_CUT, new CutKey(designer));
        hotKeyProxies.put(HotKey.VK_COPY, new CopyKey(designer));
        hotKeyProxies.put(HotKey.VK_PASTE, new PasteKey(designer));
        hotKeyProxies.put(HotKey.VK_DELETE, new DeletionKey(designer));
    }

    /**
     * 代理keyTyped
     */
    @Override
    public void keyTyped(KeyEvent e) {
        int modifiers = e.getModifiers();
        int keyCode = e.getKeyCode();
        HotKey key = new HotKey(modifiers, keyCode);
        KeyListener proxy = hotKeyProxies.get(key);

        if (proxy != null) {
            proxy.keyPressed(e);
            e.consume();
        }
    }

    /**
     * 代理keyPressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int modifiers = e.getModifiers();
        int keyCode = e.getKeyCode();
        HotKey key = new HotKey(modifiers, keyCode);
        KeyListener proxy = hotKeyProxies.get(key);

        if (proxy != null) {
            proxy.keyPressed(e);
            e.consume();
        }
    }

    /**
     * 代理keyReleased
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int modifiers = e.getModifiers();
        int keyCode = e.getKeyCode();
        HotKey key = new HotKey(modifiers, keyCode);
        KeyListener proxy = hotKeyProxies.get(key);

        if (proxy != null) {
            proxy.keyReleased(e);
            e.consume();
        }
    }
}
