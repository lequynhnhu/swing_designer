/*
 * Wrapper.java
 *
 * Created on 2007年8月13日, 下午8:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.properties.ValidationException;


/**
 *
 * @author William Chen
 */
public interface Decoder {
    Object decode(String txt);
    void validate(String txt) throws ValidationException;
}
