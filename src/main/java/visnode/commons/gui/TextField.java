/*
 * PrototipoInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import javax.swing.JTextField;

/**
 * TextField
 */
public class TextField extends JTextField implements DefaultComponent {

    /** Suporte to default components */
    private DefaultComponentSupport defaultComponentSupport;

    @Override
    public DefaultComponentSupport getDefaultComponentSupport() {
        if (defaultComponentSupport == null) {
            defaultComponentSupport = new DefaultComponentSupport(this);
        }
        return defaultComponentSupport;
    }

}
