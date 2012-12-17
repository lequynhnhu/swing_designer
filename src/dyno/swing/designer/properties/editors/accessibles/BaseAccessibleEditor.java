/*
 * BaseEditor.java
 *
 * Created on 2007��8��13��, ����8:42
 */

package dyno.swing.designer.properties.editors.accessibles;

import dyno.swing.designer.beans.Util;
import dyno.swing.designer.properties.ValidationException;
import dyno.swing.designer.properties.editors.ITextComponent;
import dyno.swing.designer.properties.editors.TextField;
import dyno.swing.designer.properties.wrappers.Decoder;
import dyno.swing.designer.properties.wrappers.Encoder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  William Chen
 */
public class BaseAccessibleEditor extends JPanel implements AccessibleEditor {

    private ArrayList<ChangeListener> listeners;
    private boolean showButton;
    protected Encoder encoder;
    private Decoder decoder;

    public BaseAccessibleEditor(Encoder enc, Decoder dec, boolean sb) {
        listeners = new ArrayList<ChangeListener>();
        this.showButton = sb;
        this.encoder = enc;
        this.decoder = dec;
        initComponents();
        txtValue.setEditable(dec != null);
        ((JComponent)txtValue).setBorder(null);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        if (decoder != null) {
            ((JComponent)txtValue).requestFocus();
        } else if (showButton) {
            btPopup.requestFocus();
        }
    }
    protected ITextComponent createTextField(){
        return new TextField();
    }
    private void initComponents() {

        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));
        txtValue = createTextField();
        setLayout(new BorderLayout());

        txtValue.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                txtValueActionPerformed(evt);
            }
        });
        add((JComponent)txtValue, BorderLayout.CENTER);
        setOpaque(false);

        if (showButton) {
            btPopup = new JButton();
            initPopupButton();
            btPopup.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    btPopupActionPerformed(evt);
                }
            });
            add(btPopup, BorderLayout.EAST);
        }
    }

    private void btPopupActionPerformed(ActionEvent evt) {
        popupDialog();
    }

    protected void popupDialog() {
    }

    protected void initPopupButton() {
        if (!isComboButton()) {
            btPopup.setText("...");
            btPopup.setPreferredSize(new Dimension(15, 20));
        } else {
            btPopup.setRolloverEnabled(true);
            btPopup.setFocusPainted(false);
            btPopup.setPreferredSize(new Dimension(15, 19));
            btPopup.setBorderPainted(false);
            btPopup.setContentAreaFilled(false);
            btPopup.setMargin(new Insets(0, 0, 0, 0));
            btPopup.setIcon(new ImageIcon(getClass().getResource("drop_up.png")));
            btPopup.setPressedIcon(new ImageIcon(getClass().getResource("drop_down.png")));
            btPopup.setRolloverIcon(new ImageIcon(getClass().getResource("drop_over.png")));
        }
    }

    protected boolean isComboButton() {
        return false;
    }

    private void txtValueActionPerformed(ActionEvent evt) {
        try {
            validateValue();
            fireStateChanged();
        } catch (ValidationException e) {
            Util.showMessage(e.getMessage(), this);
            txtValue.selectAll();
            ((JComponent)txtValue).requestFocus();
        }
    }

    public Component getEditor() {
        return this;
    }

    public Object getValue() {
        return decoder.decode(txtValue.getText());
    }

    public void setValue(Object v) {
        txtValue.setText(encoder.encode(v));
        txtValue.setValue(v);
    }

    public void addChangeListener(ChangeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void removeChangeListener(ChangeListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    protected void fireStateChanged() {
        ChangeEvent e = new ChangeEvent(this);
        for (ChangeListener l : listeners) {
            l.stateChanged(e);
        }
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public void validateValue() throws ValidationException {
        if (decoder != null) {
            decoder.validate(txtValue.getText());
        }
    }

    protected JButton getPopupButton() {
        return btPopup;
    }
    private JButton btPopup;
    protected ITextComponent txtValue;
}