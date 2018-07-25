package visnode.challenge;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import visnode.user.Base64Image;

/**
 * Challenge value
 */
public class ChallengeValue {

    /** Type */
    private final ChallengeValueType type;
    /** Value */
    private final String value;
    /** Base64 to image */
    private final Base64Image base64Image;
    
    
    /**
     * Creates a new challenge value
     *
     * @param type
     * @param value
     */
    public ChallengeValue(ChallengeValueType type, String value) {
        this.type = type;
        this.value = value;
        this.base64Image = new Base64Image();
    }
    
    /**
     * Creates a new challenge value
     *
     * @param value
     */
    public ChallengeValue(File value) {
        this.base64Image = new Base64Image();
        this.type = ChallengeValueType.IMAGE;
        this.value = base64Image.toBase64(value);
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
    
    /**
     * Returns a buffered image
     * 
     * @return BufferedImage
     */
    public BufferedImage getValueBufferedImage() {
        if (!isTypeImage()) {
            return null;
        }
        return base64Image.fromBase64(value);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.value);
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
        final ChallengeValue other = (ChallengeValue) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
}
