/*
 * CodeBlock.java
 *
 * Created on 2007-9-7, 20:57:05
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author William Chen
 */
public class CodeBlock {

    private CodeType type;
    private String param;
    private ArrayList<CodeBlock> subCodes;

    public CodeBlock(CodeType type, String para) {
        this.type = type;
        this.param = para;
    }
    public String toString(){
        return getSourceCode();
    }
    public void setParam(String p){
        param=p;
    }
    public String getSourceCode() {
        StringBuilder builder = new StringBuilder(100 * 1024);
        switch (type) {
            case source:
                appendSubCodes(builder);
                break;
            case code:
                builder.append(param + "\n");
                break;
            case declare:
                builder.append("\t//[declare\n");
                appendSubCodes(builder);
                builder.append("\t//]\n");
                break;
            case init:
                builder.append("\t//[init("+param+")\n");
                appendSubCodes(builder);
                builder.append("\t//]\n");
                break;
            case get:
                builder.append("\t//[get("+param+")\n");
                appendSubCodes(builder);
                builder.append("\t//]\n");
                break;
            case custom:
                builder.append("\t\t//[custom\n");
                appendSubCodes(builder);
                builder.append("\t\t//]\n");
                break;
            case listener:
                builder.append("\t\t\t//[listener("+param+")\n");
                appendSubCodes(builder);
                builder.append("\t\t\t//]\n");
                break;
            case event:
                builder.append("\t\t\t\t\t//[event("+param+")\n");
                appendSubCodes(builder);
                builder.append("\t\t\t\t\t//]\n");
                break;
            case packager:
                builder.append(param+"\n");
                break;
            case importer:
                builder.append(param+"\n");
                break;
        }
        return builder.toString();
    }

    private void appendSubCodes(StringBuilder builder) {
        if (subCodes != null) {
            for (CodeBlock sub : subCodes) {
                builder.append(sub.getSourceCode());
            }
        }
    }

    public void addCode(CodeBlock code) {
        if (subCodes == null) {
            subCodes = new ArrayList<CodeBlock>();
        }
        subCodes.add(code);
    }
    public void addAllCode(ArrayList<CodeBlock>codes){
        if (subCodes == null) {
            subCodes = new ArrayList<CodeBlock>();
        }
        subCodes.addAll(codes);
    }
    public CodeType getType() {
        return type;
    }

    public String getParam() {
        return param;
    }

    public ArrayList<CodeBlock> getCodes() {
        return subCodes;
    }
    public int getBlockCount(){
        return subCodes==null?0:subCodes.size();
    }
    public void insetCodeAt(int index, CodeBlock block){
        if(subCodes==null)
            subCodes=new ArrayList<CodeBlock>();
        subCodes.add(index, block);
    }
    public void clearCodes(){
        if(subCodes!=null)
            subCodes.clear();
    }
    public void addCodeLine(String line){
        addCode(new CodeBlock(CodeType.code, line));
    }
    public void addSourceCode(String source, String tabs){
        try {
            BufferedReader reader = new BufferedReader(new StringReader(source));
            String line;
            while ((line = reader.readLine()) != null) {
                addCodeLine(tabs+line);
            }
        } catch (IOException ex) {
        }
    }
    public void addSourceCode(String source){
        try {
            BufferedReader reader = new BufferedReader(new StringReader(source));
            String line;
            while ((line = reader.readLine()) != null) {
                addCodeLine(line);
            }
        } catch (IOException ex) {
        }
    }
}