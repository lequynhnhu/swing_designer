/*
 * SpinnerModelWrapper.java
 *
 * Created on 2007-8-31, 23:54:21
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.properties.ValidationException;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author William Chen
 */
public class SpinnerModelWrapper implements Encoder, Decoder, SourceCoder {

    public Object decode(String txt) {
        if (txt.equals("Date")) {
            return new SpinnerDateModel();
        } else if (txt.equals("List")) {
            return new SpinnerListModel();
        } else if (txt.equals("Number")) {
            return new SpinnerNumberModel();
        } else {
            return null;
        }
    }

    public String encode(Object v) {
        if (v instanceof SpinnerDateModel) {
            return "Date";
        } else if (v instanceof SpinnerListModel) {
            return "List";
        } else if (v instanceof SpinnerNumberModel) {
            return "Number";
        } else {
            return "Default";
        }
    }

    public void validate(String txt) throws ValidationException {
        if (txt.equals("Date")) {
        } else if (txt.equals("List")) {
        } else if (txt.equals("Number")) {
        } else if (txt.equals("Default")) {
        } else {
            throw new ValidationException("No such item!");
        }
    }

    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        if (value instanceof SpinnerDateModel) {
            return "new javax.swing.SpinnerDateModel()";
        } else if (value instanceof SpinnerListModel) {
            return "new javax.swing.SpinnerListModel()";
        } else if (value instanceof SpinnerNumberModel) {
            return "new javax.swing.SpinnerNumberModel()";
        } else {
            return "new javax.swing.SpinnerNumberModel()";
        }
    }
}