/*
 * TesteInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

import io.reactivex.Observable;

/**
 * Default checkbox component
 * 
 * @param <T>
 */
public interface DefaultCheckBox<T extends DefaultCheckBox> extends DefaultComponent<T>, GenericCheckBox<T> {

    @Override
    public default T selected(boolean selected) {
        return getDefaultCheckBoxSupport().selected(selected);
    }

    @Override
    public default T selected(Observable selected) {
        return getDefaultCheckBoxSupport().selected(selected);
    }

    @Override
    public default boolean selected() {
        return getDefaultCheckBoxSupport().selected();
    }

    @Override
    public default Observable selectedObservable() {
        return getDefaultCheckBoxSupport().selectedObservable();
    }

    @Override
    public default DefaultComponentSupport<T> getDefaultComponentSupport() {
        return (DefaultComponentSupport<T>) getDefaultCheckBoxSupport();
    }

    /**
     * Returns the checkbox support
     *
     * @return DefaultCheckBoxSupport
     */
    public DefaultCheckBoxSupport<T> getDefaultCheckBoxSupport();

}
