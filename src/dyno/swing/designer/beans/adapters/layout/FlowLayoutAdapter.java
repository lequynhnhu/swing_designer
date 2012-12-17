/*
 * FlowLayoutAdapter.java
 *
 * Created on 2007-8-2, 21:26:49
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dyno.swing.designer.beans.adapters.layout;

import dyno.swing.designer.beans.Constants;
import dyno.swing.designer.beans.GroupModel;
import dyno.swing.designer.beans.HoverPainter;
import dyno.swing.designer.beans.SwingDesigner;
import dyno.swing.designer.beans.Util;
import dyno.swing.designer.beans.painters.NullPainter;
import dyno.swing.designer.properties.FlowAlignmentWrapper;
import dyno.swing.designer.properties.FlowLayoutProperties;

import dyno.swing.designer.properties.wrappers.SourceCoder;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;


/**
 *
 * @author William Chen
 */
public class FlowLayoutAdapter extends AbstractLayoutAdapter
        implements Constants {

    public FlowLayoutAdapter(SwingDesigner designer, Container container) {
        super(designer, container);
    }

    @Override
    public boolean accept(Component bean, int x, int y) {
        return true;
    }

    @Override
    public boolean addBean(Component bean, int x, int y) {
        container.add(bean);
        Util.layoutContainer(container);

        return true;
    }

    @Override
    public HoverPainter getPainter() {
        return new NullPainter(designer, container);
    }

    @Override
    public GroupModel getLayoutProperties() {
        return new FlowLayoutProperties((FlowLayout) container.getLayout());
    }

    @Override
    public String getLayoutCode() {
        FlowLayout flowLayout = (FlowLayout) layout;
        int hgap = flowLayout.getHgap();
        int vgap = flowLayout.getVgap();
        int alignment = flowLayout.getAlignment();
        boolean alignOnBaseline = flowLayout.getAlignOnBaseline();
        if (hgap == 5 && vgap == 5 && alignment == FlowLayout.CENTER) {
            if (alignOnBaseline) {
                String var_name = Util.isRootComponent(container) ? "" : Util.getComponentName(container);
                var_name += "_flowLayout";
                String code = var_name + " = new java.awt.FlowLayout());\n";
                code += var_name + ".setAlignOnBaseline(true);\n";
                code += Constants.VAR_CONTAINER + ".setLayout(" + var_name + ");\n";
                return code;
            } else {
                return Constants.VAR_CONTAINER + ".setLayout(new java.awt.FlowLayout());\n";
            }
        } else if (hgap == 5 && vgap == 5 && alignment != FlowLayout.CENTER) {
            if (alignOnBaseline) {
                String var_name = Util.isRootComponent(container) ? "" : Util.getComponentName(container);
                var_name += "_flowLayout";
                String code = var_name + " = new java.awt.FlowLayout(" + alignmentCoder.getJavaCode(alignment) + "));\n";
                code += var_name + ".setAlignOnBaseline(true);\n";
                code += Constants.VAR_CONTAINER + ".setLayout(" + var_name + ");\n";
                return code;
            } else {
                return Constants.VAR_CONTAINER + ".setLayout(new java.awt.FlowLayout(" + alignmentCoder.getJavaCode(alignment) + "));\n";
            }
        } else {
            if (alignOnBaseline) {
                String var_name = Util.isRootComponent(container) ? "" : Util.getComponentName(container);
                var_name += "_flowLayout";
                String code = var_name + " = new java.awt.FlowLayout(" + hgap + ", " + vgap + ", " + alignmentCoder.getJavaCode(alignment) + "));\n";
                code += var_name + ".setAlignOnBaseline(true);\n";
                code += Constants.VAR_CONTAINER + ".setLayout(" + var_name + ");\n";
                return code;
            } else {
                return Constants.VAR_CONTAINER + ".setLayout(new java.awt.FlowLayout(" + hgap + ", " + vgap + ", " + alignmentCoder.getJavaCode(alignment) + "));\n";
            }
        }
    }
    private static SourceCoder alignmentCoder = new FlowAlignmentWrapper();
}
