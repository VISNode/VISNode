/*
 * TesteInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.JComponent;

/**
 * Support for default components
 *
 * @param <T>
 */
public class DefaultComponentSupport<T extends DefaultComponent> implements Component<T> {

    /** Component */
    protected final T component;
    /** Components ID */
    private String id;

    /**
     * Creates the component support
     *
     * @param component
     */
    public DefaultComponentSupport(T component) {
        this.component = component;
    }

    @Override
    public T id(String id) {
        this.id = id;
        return component;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public T enabled(boolean enabled) {
        swingComponent().setEnabled(enabled);
        return component;
    }

    @Override
    public T enabled(Observable<Boolean> enabled) {
        enabled.subscribe((bool) -> {
            swingComponent().setEnabled(bool);
        });
        return component;
    }

    @Override
    public boolean enabled() {
        return swingComponent().isEnabled();
    }

    @Override
    public Observable<Boolean> enabledObservable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public T focusable(boolean focusable) {
        swingComponent().setFocusable(focusable);
        return component;
    }

    @Override
    public T focusable(Observable<Boolean> focusable) {
        focusable.subscribe((bool) -> {
            enabled(bool);
        });
        return component;
    }

    @Override
    public boolean focusable() {
        return swingComponent().isFocusable();
    }

    @Override
    public Observable<Boolean> focusableObservable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T background(Color background) {
        swingComponent().setBackground(background);
        return component;
    }

    @Override
    public T background(Observable<Color> background) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color background() {
        return swingComponent().getBackground();
    }

    @Override
    public Observable<Color> backgroundObservable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public T onClick(Consumer<MouseEvent> consumer) {
        swingComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return component;
    }

    @Override
    public <K> K as(Class<K> type) {
        throw new UnsupportedOperationException("Not handled by support");
    }

    @Override
    public T style(String style) {
        component.styler().apply(style);
        return component;
    }
    
    /**
     * Returns the swing component
     *
     * @return JComponent
     */
    protected JComponent swingComponent() {
        return (JComponent) component;
    }

}
