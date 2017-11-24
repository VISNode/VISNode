package visnode.commons.gui;

import io.reactivex.Observable;
import java.awt.BorderLayout;

/**
 * Ranged integer input
 */
public class RangedIntegerInput extends ComposedComponent<RangedIntegerInput> implements DefaultInput<Integer, RangedIntegerInput> {

    /** Default input support */
    private final DefaultInputSupport inputSupport;

    /**
     * Creates the ranged input
     */
    public RangedIntegerInput() {
        super();
        NumericInput valueInput = Inputs.integer();
        this.inputSupport = new DefaultInputSupport(this) {
            @Override
            public Object value() {
                return valueInput.value();
            }

            @Override
            public GenericInput value(Object value) {
                valueInput.value(value);
                return (GenericInput) component;
            }

            @Override
            public Observable valueObservable() {
                return valueInput.valueObservable();
            }

            @Override
            public GenericInput value(Observable value) {
                valueInput.value(value);
                return (GenericInput) component;
            }
        };
        setLayout(new BorderLayout());
        add(Buttons.create(), BorderLayout.WEST);
//        put(valueInput);
        put(Labels.create().text(valueObservable().map((v) -> v.toString())));
        add(Buttons.create(), BorderLayout.EAST);
    }
    
    @Override
    public DefaultInputSupport<Integer, RangedIntegerInput> getDefaultInputSupport() {
        return inputSupport;
    }
    
}
