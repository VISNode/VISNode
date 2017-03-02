package visnode.commons;

/**
 * Threshold
 */
public class Threshold extends Number {

    /** Threshold value */
    private final int threshold;

    /**
     * Creates a new threshold
     * 
     * @param threshold 
     */
    public Threshold(int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public int intValue() {
        return threshold;
    }

    @Override
    public long longValue() {
        return threshold;
    }

    @Override
    public float floatValue() {
        return threshold;
    }

    @Override
    public double doubleValue() {
        return threshold;
    }

}
