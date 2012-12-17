/*
 * PrototypeClassLoader.java
 * 
 * Created on 2007-9-8, 18:07:02
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.code;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author rehte
 */
public class PrototypeClassLoader extends URLClassLoader{
    public PrototypeClassLoader(){
        super(new URL[0]);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File bufferDir=CodeSeed.getCodeBufferDir();
        if(!bufferDir.exists())
            throw new ClassNotFoundException(name);
        String file_name=name;
        String pack=null;
        int dot=name.lastIndexOf(".");
        if(dot!=-1){
            file_name=name.substring(dot+1);
            pack=name.substring(0, dot);            
        }
        if(pack!=null){
            bufferDir=new File(bufferDir, pack.replace('.', '/'));
            if(!bufferDir.exists())
                throw new ClassNotFoundException(name);
        }
        File source=new File(bufferDir, file_name+".class");
        if(!source.exists())
            throw new ClassNotFoundException(name);
        byte[]binaries=getBinaries(source);
        if(binaries==null)
            throw new ClassNotFoundException(name);
        return super.defineClass(binaries, 0, binaries.length);
    }

    private byte[] getBinaries(File source) {
        FileInputStream fis=null;
        try{
            fis=new FileInputStream(source);
            BufferedInputStream bis=new BufferedInputStream(fis);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            byte[]data=new byte[1024];
            int length;
            while((length=bis.read(data))>0){
                baos.write(data, 0, length);
            }
            baos.close();
            return baos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            if(fis!=null){
                try{
                    fis.close();
                }catch(Exception e){}
            }
        }
    }    
}
