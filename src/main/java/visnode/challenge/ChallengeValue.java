package visnode.challenge;

/**
 * Challenge value
 */
public class ChallengeValue {

    /** Type */
    private final ChallengeValueType type;
    /** Value */
    private final String value;

    /**
     * Creates a new challenge value
     *
     * @param type
     * @param value
     */
    public ChallengeValue(ChallengeValueType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the challenge type
     *
     * @return ChallengeType
     */
    public ChallengeValueType getType() {
        return type;
    }

    /**
     * Returns true if the value is a image
     *
     * @return boolean
     */
    public boolean isTypeImage() {
        return type.equals(ChallengeValueType.IMAGE);
    }

    /**
     * Returns the value
     *
     * @return String
     */
    public String getValue() {
        return value;
    }

}
