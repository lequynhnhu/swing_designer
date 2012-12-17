/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beaninfo;

/**
 *
 * @author William Chen
 */
public class DefaultValue {
    private boolean set;
    private Object value;
    public DefaultValue(){
    }
    public Object getValue(){
        return value;
    }
    public boolean isSet(){
        return set;
    }
    public void setValue(Object v){
        value=v;
        set=true;
    }
}
