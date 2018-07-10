package visnode.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import visnode.application.ExceptionHandler;
import visnode.commons.ImageScale;

/**
 * User image converter
 */
public class UserImage {
    
    /** The image max size */
    private static final int MAX_SIZE = 256;
    
    /**
     * Convert a Base64 image to a buffered image
     *
     * @param image
     * @return BufferedImage
     */
    public BufferedImage fromBase64(String image) {
        try {
            if (image == null) {
                return ImageIO.read(getClass().getResourceAsStream("/user_256.png"));
            }
            byte[] imageByte = Base64.getDecoder().decode(image);
            BufferedImage imageBuff;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
                imageBuff = ImageIO.read(bis);
            }
            return imageBuff;
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return null;
    }

    /** 
     * Convert a buffered image to a Base64
     * 
     * @param image
     * @return String
     */
    public String toBase64(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(ImageScale.scale(image, MAX_SIZE), "JPG", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException ioe) {
            ExceptionHandler.get().handle(ioe);
        }
        return null;
    }
}
