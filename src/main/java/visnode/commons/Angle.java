package visnode.commons;

/**
 * Angle value
 */
public class Angle extends Number {

    /** Angle value */
    private final int angle;

    /**
     * Creates a new angle
     *
     * @param angle
     */
    public Angle(int angle) {
        this.angle = angle;
    }

    @Override
    public int intValue() {
        return angle;
    }

    @Override
    public long longValue() {
        return angle;
    }

    @Override
    public float floatValue() {
        return angle;
    }

    @Override
    public double doubleValue() {
        return angle;
    }

}
