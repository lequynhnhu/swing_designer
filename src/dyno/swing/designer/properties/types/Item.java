/*
 * Item.java
 *
 * Created on 2007-8-19, 16:38:20
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.types;

public class Item {

    private Object value;
    private String name;
    private String code;
    
    public Item(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    public Item(String name, Object value, String code) {
        this.name = name;
        this.value = value;
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Item) {
            Item a = (Item) o;
            Object av = a.getValue();
            if (value == null) {
                if(av==null)
                    return true;
                else
                    return false;
            } else {
                if(av==null)
                    return false;
                else
                    return value.equals(av);
            }
        } else {
            return false;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}