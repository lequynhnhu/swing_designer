/*
 * EventHandlerWrapper.java
 *
 * Created on 2007-9-5, 21:30:17
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.types.EventHandler;

/**
 *
 * @author William Chen
 */
public class EventHandlerWrapper implements Encoder {

    public String encode(Object v) {
        if (v == null) {
            return null;
        } else {
            EventHandler handler = (EventHandler) v;
            String code = handler.getCode_buffer();
            if (Util.isStringNull(code)) {
                return "";
            } else {
                return handler.getHandlerName();
            }
        }
    }
}