/*
 * TesteInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import java.awt.Container;
import javax.swing.JPanel;

/**
 * Panel
 */
public class Panel extends JPanel implements DefaultComponent<Panel>, DefaultContainer<Panel> {

    /** Suporte to default components */
    private DefaultComponentSupport defaultComponentSupport;

    @Override
    public DefaultComponentSupport getDefaultComponentSupport() {
        if (defaultComponentSupport == null) {
            defaultComponentSupport = new DefaultComponentSupport(this);
        }
        return defaultComponentSupport;
    }

    @Override
    public Container getSwingSelf() {
        return this;
    }

}
