/*
 * PrototipoInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import javax.swing.JCheckBox;

/**
 * CheckBox
 */
public class CheckBox extends JCheckBox implements DefaultCheckBox {

    /** Support to default checkboxes */
    private DefaultCheckBoxSupport defaultComponentSupport;

    public CheckBox() {
        super();
        addActionListener((evt) -> {
            boolean oldValue = this.isSelected();
            firePropertyChange("selected", !oldValue, oldValue);
        });
    }

    @Override
    public DefaultCheckBoxSupport getDefaultCheckBoxSupport() {
        if (defaultComponentSupport == null) {
            defaultComponentSupport = new DefaultCheckBoxSupport(this);
        }
        return defaultComponentSupport;
    }

    @Override
    public void setSelected(boolean b) {
        boolean oldValue = this.isSelected();
        super.setSelected(b);
        firePropertyChange("selected", oldValue, b);
    }

}
