/*
 * ExtendedPropertyEditor.java
 * 
 * Created on 2007-9-15, 21:22:44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.editors;

import dyno.swing.designer.properties.ValidationException;
import java.beans.PropertyEditor;

/**
 *
 * @author rehte
 */
public interface ExtendedPropertyEditor extends PropertyEditor{
    void validateValue()throws ValidationException;
    void setDefaultValue(Object v);
}
