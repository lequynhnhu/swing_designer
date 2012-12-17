/*
 * LoggingWriter.java
 * 
 * Created on 2007-9-16, 0:00:12
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.main;

import java.io.IOException;
import java.io.Writer;
import javax.swing.JTextArea;

/**
 *
 * @author rehte
 */
public class LoggingWriter extends Writer{
    private static final int BUFFER_SIZE=10*1024;
    private JTextArea txtLogging;
    private StringBuilder builder;
    public LoggingWriter(JTextArea txtLogging) {
        this.txtLogging=txtLogging;
        builder=new StringBuilder(BUFFER_SIZE);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        int capacity=builder.capacity();
        int length=builder.length();
        if(length+len>=capacity)
            flush();
        builder.append(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        txtLogging.append(builder.toString());
        builder.setLength(0);
    }

    @Override
    public void close() throws IOException {
        if(builder.length()>0)
            flush();
        builder=null;
        txtLogging=null;
    }
}
