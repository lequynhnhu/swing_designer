/*
 * EventHandler.java
 * 
 * Created on 2007-9-5, 21:26:02
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.types;

/**
 *
 * @author William Chen
 */
public class EventHandler {
    private String handler_name;
    private String code_buffer;
    public EventHandler(String mHandler){
        this.handler_name = mHandler;
    }
    public String getHandlerName() {
        return handler_name;
    }

    public String getCode_buffer() {
        return code_buffer;
    }

    public void setCode_buffer(String code_buffer) {
        this.code_buffer = code_buffer;
    }
}
