package visnode.commons.gui;

import java.awt.Container;
import javax.swing.JComponent;

/**
 * Composed component
 */
public abstract class ComposedComponent<T extends ComposedComponent> extends JComponent implements DefaultComponent<T>, DefaultContainer<T> {

    @Override
    public Container getSwingSelf() {
        return this;
    }

}
