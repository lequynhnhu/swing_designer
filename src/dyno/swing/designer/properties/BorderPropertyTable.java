/*
 * BorderPropertyTable.java
 *
 * Created on 2007-8-27, 16:36:31
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties;

import dyno.swing.designer.beans.AdapterBus;
import dyno.swing.designer.beans.BorderAdapter;
import dyno.swing.designer.beans.BorderGroupModel;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

/**
 *
 * @author William Chen
 */
public class BorderPropertyTable extends AbstractPropertyTable {

    private BorderItem borderItem;
    private BorderGroupModel bgmodel;

    public BorderPropertyTable() {
    }

    @Override
    public void setBean(Object bean) {
        if (bean != null) {
            groups = new ArrayList<PropertyGroup>();
            borderItem = (BorderItem) bean;
            if (borderItem != null) {
                Border border = borderItem.getBorder();
                if (border != null) {
                    BorderAdapter adapter = AdapterBus.getBorderAdapter(border);
                    bgmodel = adapter.getBorderProperties();
                    if (bgmodel != null) {
                        groups.add(new PropertyGroup(bgmodel.getGroupName(), bgmodel));
                        TableModel model = new BeanTableModel();
                        setModel(model);
                        repaint();
                        return;
                    }
                }
            }
        }
        setModel(default_table_model);
        repaint();
    }

    protected void fireComponentEdited() {
        borderItem.setBorder(bgmodel.getBorder());
    }
}