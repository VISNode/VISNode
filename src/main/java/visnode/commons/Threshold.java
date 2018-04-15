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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.threshold;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Threshold other = (Threshold) obj;
        if (this.threshold != other.threshold) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Threshold of " + threshold;
    }

}
