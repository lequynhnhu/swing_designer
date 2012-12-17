/*
 * BorderWrapper.java
 *
 * Created on August 14, 2007, 6:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dyno.swing.designer.properties.wrappers;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.wrappers.borders.BevelBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.CompoundBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.EmptyBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.EtchedBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.LineBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.MatteBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.SoftBevelBorderCoder;
import dyno.swing.designer.properties.wrappers.borders.TitledBorderCoder;
import java.util.HashMap;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;


/**
 *
 * @author William Chen
 */
public class BorderWrapper implements Encoder, SourceCoder, Decoder {

    /** Creates a new instance of BorderWrapper */
    public BorderWrapper() {
    }
    public String encode(Object v) {
        if (v == null) {
            return "(No Border)";
        }

        String class_name = v.getClass().getName();
        String name = class_name;
        int dot = class_name.lastIndexOf('.');
        if (dot != -1) {
            name = class_name.substring(dot + 1);
        }
        dot = name.indexOf('$');
        if(dot!=-1){
            String prefix=name.substring(0, dot);
            String afix=name.substring(dot+1);
            if(prefix.equals("XPStyle"))
                return "["+afix+"]";
            else
                return prefix+"["+afix+"]";
        }
        return name;
    }
    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        Border border=(Border)value;
        SourceCoder coder=sourceCoders.get(border.getClass());
        if(coder!=null){
            return coder.getJavaCode(value);
        }else{
            return "new "+border.getClass().getName()+"()";
        }
    }
    private static HashMap<Class<? extends Border>, SourceCoder>sourceCoders;
    static{
        sourceCoders=new HashMap<Class<? extends Border>, SourceCoder>();
        sourceCoders.put(LineBorder.class, new LineBorderCoder());
        sourceCoders.put(BevelBorder.class, new BevelBorderCoder());
        sourceCoders.put(CompoundBorder.class, new CompoundBorderCoder());
        sourceCoders.put(EmptyBorder.class, new EmptyBorderCoder());
        sourceCoders.put(EtchedBorder.class, new EtchedBorderCoder());
        sourceCoders.put(MatteBorder.class, new MatteBorderCoder());
        sourceCoders.put(SoftBevelBorder.class, new SoftBevelBorderCoder());
        sourceCoders.put(TitledBorder.class, new TitledBorderCoder());
    }

    public Object decode(String txt) {
        if(Util.isStringNull(txt))
            return null;
        if(txt.equals("(No Border)"))
            return null;
        return null;
    }

    public void validate(String txt) throws ValidationException {
    }
}