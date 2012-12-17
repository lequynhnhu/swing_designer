/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers;

import java.awt.GridBagConstraints;

/**
 *
 * @author William Chen
 */
public class GridBagConstraintsSourceCoder implements SourceCoder{

    public String getJavaCode(Object value) {
        GridBagConstraints contraints=(GridBagConstraints)value;
        return "new java.awt.GridBagConstraints()";
    }

}
