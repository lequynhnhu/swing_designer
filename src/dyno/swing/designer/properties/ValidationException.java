/*
 * ValidationException.java
 *
 * Created on 2007��8��13��, ����11:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties;


/**
 *
 * @author William Chen
 */
public class ValidationException extends Exception {
    /** Creates a new instance of ValidationException */
    public ValidationException() {
    }

    public ValidationException(String msg) {
        super(msg);
    }
}
