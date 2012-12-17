/*
 * Prototype.java
 *
 * Created on 2007-9-8, 9:11:01
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.beans.code;

import dyno.swing.designer.properties.types.Item;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author William Chen
 */
public class Prototype {

    private CodeBlock packager;
    private ArrayList<CodeBlock> importers;
    private CodeBlock declaration;
    private CodeBlock init;
    private ArrayList<CodeBlock> gets;

    public Prototype() {
    }

    public CodeBlock getPackager() {
        return packager;
    }

    public void setPackager(CodeBlock packager) {
        this.packager = packager;
    }

    public ArrayList<CodeBlock> getImporters() {
        return importers;
    }

    public void addImporter(CodeBlock block) {
        if (importers == null) {
            importers = new ArrayList<CodeBlock>();
        }
        importers.add(block);
    }

    public CodeBlock getDeclaration() {
        return declaration;
    }

    public void setDeclaration(CodeBlock declaration) {
        this.declaration = declaration;
    }

    public CodeBlock getInit() {
        return init;
    }

    public void setInit(CodeBlock init) {
        this.init = init;
    }

    public ArrayList<CodeBlock> getGets() {
        return gets;
    }

    public void addGet(CodeBlock block) {
        if (gets == null) {
            gets = new ArrayList<CodeBlock>();
        }
        gets.add(block);
    }

    public String generatePrototypeSourceCode(PrintWriter writer, Item guitype) {
        if (packager != null) {
            writer.println(packager.getParam());
        }
        if (importers != null) {
            for (CodeBlock importer : importers) {
                writer.println(importer.getParam());
            }
        }
        String prototypeName = createNewPrototypeName();
        writer.println("public class " + prototypeName + " extends "+guitype.getCode()+" {");
        writer.println("\tpublic " + prototypeName + "(){");
        writer.println("\t\tinitComponents();");
        writer.println("\t}");
        if (declaration != null) {
            ArrayList<CodeBlock> declares = declaration.getCodes();
            if (declares != null) {
                for (CodeBlock declare : declares) {
                    writer.println(declare.getParam());
                }
            }
        }
        if (init != null) {
            ArrayList<CodeBlock> lines = init.getCodes();
            if (lines != null) {
                for (CodeBlock line : lines) {
                    CodeType type = line.getType();
                    switch (type) {
                        case listener:
                            break;
                        case custom:
                            break;
                        default:
                            writer.println(line.getParam());
                            break;
                    }
                }
            }
        }
        if (gets != null) {
            for (CodeBlock get : gets) {
                ArrayList<CodeBlock> lines = get.getCodes();
                if (lines != null) {
                    for (CodeBlock line : lines) {
                        CodeType type = line.getType();
                        switch (type) {
                            case listener:
                                break;
                            case custom:
                                break;
                            default:
                                writer.println(line.getParam());
                                break;
                        }
                    }
                }
            }
        }
        writer.println("}");
        String pack = getPackageName();
        return (pack == null ? "" : (pack + ".")) + prototypeName;
    }

    String getPackageName() {
        if (packager == null) {
            return null;
        } else {
            String line = packager.getParam();
            String pack_line = line.trim();
            if (pack_line.startsWith("package")) {
                pack_line = pack_line.substring(7);
                pack_line = pack_line.trim();
                int colon = pack_line.lastIndexOf(";");
                if (colon != -1) {
                    return pack_line.substring(0, colon).trim();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    private String createNewPrototypeName() {
        return PROTOTYPE_PREFIX + System.currentTimeMillis();
    }
    public static final String PROTOTYPE_PREFIX = "_$$Prototype$";
}