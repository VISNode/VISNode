package visnode.commons.gui;

/**
 * Factory for Input types
 */
public class Inputs {

    /**
     * Private constructor
     */
    private Inputs() {
    }
    
    /**
     * Creates a new TextField
     * 
     * @return TextField
     */
    public static TextField text() {
        return new TextField();
    }
    
    /**
     * Creates a new Integer input
     * 
     * @return Numeric Input
     */
    public static NumericInput<Integer> integer() {
        return new NumericInput<>();
    }
    
    /**
     * Create a new input for ranged inputs
     * 
     * @return RangedIntegerInput
     */
    public static RangedIntegerInput rangedInteger() {
        return new RangedIntegerInput();
    }
    
}
