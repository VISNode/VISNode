/*
 * PrototipoInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import java.awt.Dimension;
import javax.swing.JTextField;
import visnode.commons.gui.helpers.DocumentHelper;

/**
 * TextField
 */
public class TextField extends JTextField implements DefaultInput<String, TextField> {

    /** Default input support */
    private final DefaultInputSupport inputSupport;
    /** Old value for events */
    private String oldValue;
    
    
    /**
     * Creates the ranged input
     */
    public TextField() {
        super();
        this.inputSupport = new DefaultTextInputSupport(this);
        DocumentHelper.onChange(getDocument(), (evt) -> {
            firePropertyChange("value", oldValue, getText());
            oldValue = getText();
        });
        setPreferredSize(new Dimension(200, 25));
    }
    
    @Override
    public DefaultInputSupport<String, TextField> getDefaultInputSupport() {
        return inputSupport;
    }

}
