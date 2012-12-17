/*
 * TableModelSourceCoder.java
 * 
 * Created on 2007-9-16, 22:05:16
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.wrappers;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author rehte
 */
public class TableModelSourceCoder implements SourceCoder{

    @Override
    public String getJavaCode(Object value) {
        if(value==null)
            return "null";
        TableModel model=(TableModel)value;
        DefaultTableModel dtm=new DefaultTableModel();
        int row=model.getRowCount();
        int column=model.getColumnCount();
        String code="new javax.swing.table.DefaultTableModel(new Object[][]{";
        for(int i=0;i<row;i++){
            if(i!=0)
            code+=", ";
            code+="{";
            for(int j=0;j<column;j++){
                if(j!=0)
                    code+=", ";
                code+="\""+model.getValueAt(i, j)+"\"";
            }
            code+="}";
        }
        code+="}, new Object[]{";
        for(int j=0;j<column;j++){
            if(j!=0)
                code+=", ";
            code+="\""+model.getColumnName(j)+"\"";
        }
        code+="})";
        return code;
    }    
}
