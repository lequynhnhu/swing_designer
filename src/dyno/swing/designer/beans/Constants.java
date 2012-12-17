/*
 * Location.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.beans;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.border.Border;


/**
 * 一些常量
 *
 * @author William Chen
 */
public interface Constants {
    int BOX_SIZE = 5;
    Color BOX_BORDER_COLOR = new Color(143, 171, 196);
    Color BOX_INNER_COLOR = Color.white;
    Color SELECTION_COLOR = new Color(255, 164, 0);
    Color AREA_BORDER_COLOR = new Color(224, 224, 255);
    Color LAYOUT_HOTSPOT_COLOR = new Color(64, 240, 0);
    Color LAYOUT_FORBIDDEN_COLOR = new Color(240, 64, 0);
    float HOVER_TRANSLUCENCY = 0.5f;
    int BORDER_THICK = 4;
    Border areaBorder = BorderFactory.createLineBorder(AREA_BORDER_COLOR,
            BORDER_THICK);
    Stroke DASH_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL, 1, new float[] { 5, 5 }, 0);
    BasicStroke STROKE2=new BasicStroke(2);
    BasicStroke STROKE3=new BasicStroke(3);
    BasicStroke STROKE4=new BasicStroke(4);
    String VAR_CONTAINER="${PARENT}";
}
