/*
 * ColorPalette.java
 *
 * Created on 2007-10-1, 15:58:54
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors.accessibles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;

/**
 *
 * @author William Chen
 */
public class ColorPalette extends JPopupMenu {
    private Border BLACK_BORDER=BorderFactory.createLineBorder(new Color(127, 157, 185));
    private Object[] colors = new Object[]{
        new Object[]{"ºÚÉ«", new Color(0, 0, 0)}, 
        new Object[]{"Éîºì", new Color(128, 0, 0)}, 
        new Object[]{"ºìÉ«", new Color(255, 0, 0)}, 
        new Object[]{"·Ûºì", new Color(255, 0, 255)}, 
        new Object[]{"Ãµ¹åºì", new Color(255, 153, 204)}, 
        new Object[]{"ºÖÉ«", new Color(153, 51, 0)}, 
        new Object[]{"½Û»Æ", new Color(255, 102, 0)}, 
        new Object[]{"Ç³½Û»Æ", new Color(255, 153, 0)}, 
        new Object[]{"½ðÉ«", new Color(255, 204, 0)}, 
        new Object[]{"×Ø»Æ", new Color(255, 204, 153)}, 
        new Object[]{"", new Color(51, 51, 0)}, 
        new Object[]{"", new Color(128, 128, 0)}, 
        new Object[]{"", new Color(153, 204, 0)}, 
        new Object[]{"", new Color(255, 255, 0)}, new Object[]{"", new Color(255, 255, 153)}, new Object[]{"", new Color(0, 51, 0)}, new Object[]{"", new Color(0, 128, 0)}, new Object[]{"", new Color(51, 153, 102)}, new Object[]{"", new Color(172, 168, 153)}, new Object[]{"", new Color(204, 255, 204)}, new Object[]{"", new Color(0, 51, 102)}, new Object[]{"", new Color(0, 128, 128)}, new Object[]{"", new Color(51, 204, 204)}, new Object[]{"", new Color(0, 255, 255)}, new Object[]{"", new Color(204, 255, 255)}, new Object[]{"", new Color(0, 0, 128)}, new Object[]{"", new Color(0, 0, 255)}, new Object[]{"", new Color(51, 102, 255)}, new Object[]{"", new Color(0, 204, 255)}, new Object[]{"", new Color(153, 204, 255)}, new Object[]{"", new Color(51, 51, 153)}, new Object[]{"", new Color(102, 102, 153)}, new Object[]{"", new Color(128, 0, 128)}, new Object[]{"", new Color(153, 51, 102)}, new Object[]{"", new Color(204, 153, 255)}, new Object[]{"", new Color(51, 51, 51)}, new Object[]{"", new Color(128, 128, 128)}, new Object[]{"", new Color(153, 153, 153)}, new Object[]{"", new Color(192, 192, 192)}, new Object[]{"", new Color(255, 255, 255)}};
    
    private JButton btnDefault;
    private JButton btnCustom;
    private JToggleButton[] btnColors;
    private ButtonGroup group;

    public ColorPalette() {
        setBorder(
                BorderFactory.createCompoundBorder(
                BLACK_BORDER, 
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        setLayout(new BorderLayout());
        JToolBar top_bar = getTopBar();
        add(top_bar, BorderLayout.NORTH);
        JToolBar palette_bar = getPaletteBar();
        add(palette_bar, BorderLayout.CENTER);
        JToolBar bottom_bar = getBottomBar();
        add(bottom_bar, BorderLayout.SOUTH);
    }

    public void addDefaultAction(ActionListener l) {
        btnDefault.addActionListener(l);
    }

    public void addCustomAction(ActionListener l) {
        btnCustom.addActionListener(l);
    }

    public void addColorAction(ActionListener l) {
        for (JToggleButton button : btnColors) {
            button.addActionListener(l);
        }
    }

    private JToolBar getBottomBar() {
        JToolBar bottom_bar = new JToolBar();
        bottom_bar.setOpaque(false);
        bottom_bar.setLayout(new GridLayout(1, 1));
        bottom_bar.setBorderPainted(false);
        bottom_bar.setFloatable(false);
        btnCustom = getBtnCustom();
        bottom_bar.add(btnCustom);
        return bottom_bar;
    }

    private JButton getBtnCustom() {
        if (btnCustom == null) {
            btnCustom = new JButton();
            btnCustom.setBorder(BLACK_BORDER);
            btnCustom.setFocusPainted(false);
            btnCustom.setText("More colors ...");
        }
        return btnCustom;
    }

    private JButton getBtnDefault() {
        if (btnDefault == null) {
            btnDefault = new JButton();
            btnDefault.setBorder(BLACK_BORDER);
            btnDefault.setFocusPainted(false);
            btnDefault.setText("Use default color");
        }
        return btnDefault;
    }

    private JToolBar getPaletteBar() {
        JToolBar palette_bar = new JToolBar();
        palette_bar.setOpaque(false);
        GridLayout layout = new GridLayout(5, 8, 1, 1);
        palette_bar.setLayout(layout);
        palette_bar.setBorderPainted(false);
        palette_bar.setFloatable(false);
        group = new ButtonGroup();
        btnColors = new JToggleButton[colors.length];
        for (int i = 0; i < colors.length; i++) {
            btnColors[i] = new JToggleButton();
            Object[] color_object = (Object[]) colors[i];
            String name = (String) color_object[0];
            Color color = (Color) color_object[1];
            btnColors[i].setIcon(new ColorIcon(name, color));
            btnColors[i].setToolTipText(name);
            btnColors[i].setFocusPainted(false);
            group.add(btnColors[i]);
            palette_bar.add(btnColors[i]);
        }
        return palette_bar;
    }

    private JToolBar getTopBar() {
        JToolBar top_bar = new JToolBar();
        top_bar.setBorderPainted(false);
        top_bar.setOpaque(false);
        top_bar.setFloatable(false);
        top_bar.setLayout(new GridLayout(1, 1));
        btnDefault = getBtnDefault();
        top_bar.add(btnDefault);
        return top_bar;
    }

    public void setChoosedColor(Color choosedColor) {
        group.clearSelection();
        if (choosedColor != null) {
            for (int i = 0; i < colors.length; i++) {
                Color c=(Color)((Object[])colors[i])[1];
                if (c.equals(choosedColor)) {
                    btnColors[i].setSelected(true);
                    return;
                }
            }
        }
    }
}