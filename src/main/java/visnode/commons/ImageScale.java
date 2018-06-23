package visnode.commons;

import java.awt.image.BufferedImage;

/**
 * Image scale
 */
public class ImageScale {

    /**
     * Scale image
     * 
     * @param original
     * @param thumbnailSize
     * @return BufferedImage
     */
    public static BufferedImage scale(BufferedImage original, int thumbnailSize) {
        int size = Math.max(original.getWidth(), original.getHeight());
        int newWidth = thumbnailSize * original.getWidth() / size;
        int newHeight = thumbnailSize * original.getHeight() / size;
        int x = (thumbnailSize - newWidth) / 2;
        int y = (thumbnailSize - newHeight) / 2;
        BufferedImage newImage = new BufferedImage(thumbnailSize, thumbnailSize, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        java.awt.Image scaledImage = original.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        newImage.getGraphics().drawImage(scaledImage, x, y, null);
        return newImage;
    }
}
