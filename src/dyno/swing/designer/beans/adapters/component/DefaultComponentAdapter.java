

/*
 * DefaultComponentAdapter.java
 *
 * Created on 2007年5月2日, 下午11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.component;

import dyno.swing.designer.beans.SwingDesigner;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Rectangle;


/**
 *
 * @author William Chen
 */
public class DefaultComponentAdapter extends AbstractComponentAdapter {
    private static int TEXT_WIDTH_PAD=4;
    private static int TEXT_HEIGHT_PAD=2;
    /** Creates a new instance of DefaultComponentAdapter */
    public DefaultComponentAdapter(SwingDesigner designer, Component c) {
        super(designer,c);
    }

    protected Rectangle getTextEditorBounds(String text, Component bean) {
        FontMetrics fm = bean.getFontMetrics(bean.getFont());
        int width = bean.getWidth();
        int height = bean.getHeight();

        Rectangle bounds = new Rectangle();
        bounds.x = ((width - fm.stringWidth(text)) / 2)-TEXT_WIDTH_PAD;
        bounds.y = ((height - fm.getHeight()) / 2)-TEXT_HEIGHT_PAD;
        bounds.width = fm.stringWidth(text)+2*TEXT_WIDTH_PAD;
        bounds.height = fm.getHeight()+2*TEXT_HEIGHT_PAD;

        return bounds;
    }
}
