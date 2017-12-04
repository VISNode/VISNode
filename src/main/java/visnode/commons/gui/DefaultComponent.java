/*
 * PrototipoInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import io.reactivex.Observable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Default implementation for a component
 * @param <T>
 */
public interface DefaultComponent<T extends DefaultComponent> extends Component<T> {

    @Override
    public default T id(String id) {
        return getDefaultComponentSupport().id(id);
    }

    @Override
    public default String id() {
        return getDefaultComponentSupport().id();
    }

    @Override
    public default T enabled(boolean enabled) {
        return getDefaultComponentSupport().enabled(enabled);
    }

    @Override
    public default T enabled(Observable<Boolean> enabled) {
        return getDefaultComponentSupport().enabled(enabled);
    }

    @Override
    public default boolean enabled() {
        return getDefaultComponentSupport().enabled();
    }

    @Override
    public default Observable<Boolean> enabledObservable() {
        return getDefaultComponentSupport().enabledObservable();
    }
    
    @Override
    public default T focusable(boolean focusable) {
        return getDefaultComponentSupport().focusable(focusable);
    }

    @Override
    public default T focusable(Observable<Boolean> focusable) {
        return getDefaultComponentSupport().focusable(focusable);
    }

    @Override
    public default boolean focusable() {
        return getDefaultComponentSupport().focusable();
    }

    @Override
    public default Observable<Boolean> focusableObservable() {
        return getDefaultComponentSupport().focusableObservable();
    }

    @Override
    public default T background(Color background) {
        return getDefaultComponentSupport().background(background);
    }

    @Override
    public default T background(Observable<Color> background) {
        return getDefaultComponentSupport().background(background);
    }

    @Override
    public default Color background() {
        return getDefaultComponentSupport().background();
    }

    @Override
    public default Observable<Color> backgroundObservable() {
        return getDefaultComponentSupport().backgroundObservable();
    }

    @Override
    public default T preferredSize(Dimension preferredSize) {
        return getDefaultComponentSupport().preferredSize(preferredSize);
    }

    @Override
    public default T preferredSize(Observable<Dimension> preferredSize) {
        return getDefaultComponentSupport().preferredSize(preferredSize);
    }

    @Override
    public default Dimension preferredSize() {
        return getDefaultComponentSupport().preferredSize();
    }

    @Override
    public default Observable<Dimension> preferredSizeObservable() {
        return getDefaultComponentSupport().preferredSizeObservable();
    }
    
    @Override
    public default T onClick(Consumer<MouseEvent> consumer) {
        return getDefaultComponentSupport().onClick(consumer);
    }
    
    @Override
    public default <K> K as(Class<K> type) {
        return (K) this;
    }

    @Override
    public default T style(String style) {
        return getDefaultComponentSupport().style(style);
    }
    
    /**
     * Returns this component styler
     * 
     * @return Styler
     */
    public default Styler<T> styler() {
        return new DefaultComponentStyler<>((T)this);
    }

    /**
     * Returns the support for default components
     *
     * @return DefaultComponentSupport
     */
    public DefaultComponentSupport<T> getDefaultComponentSupport();

}
