package visnode.challenge;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import visnode.user.Base64Image;

/**
 * Challenge value
 */
public class MissionValue {

    /** Type */
    private final MissionValueType type;
    /** Value */
    private final String value;
    /** Base64 to image */
    private Base64Image base64Image;
    
    
    /**
     * Creates a new challenge value
     *
     * @param type
     * @param value
     */
    public MissionValue(MissionValueType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    /**
     * Creates a new challenge value
     *
     * @param value
     */
    public MissionValue(File value) {
        this.type = MissionValueType.IMAGE;
        this.value = getbBase64Image().toBase64(value);
    }

    /**
     * Returns the challenge type
     *
     * @return ChallengeType
     */
    public MissionValueType getType() {
        return type;
    }

    /**
     * Returns true if the value is a image
     *
     * @return boolean
     */
    public boolean isTypeImage() {
        return type.equals(MissionValueType.IMAGE);
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
        return getbBase64Image().fromBase64(value);
    }
    
    private Base64Image getbBase64Image() {
        if (base64Image == null) {
            base64Image = new Base64Image();
        }
        return base64Image;
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
        final MissionValue other = (MissionValue) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
}
