package nicolas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageComparer {

    public static void main(String[] args) throws IOException {
        BufferedImage visnode = ImageIO.read(new File(System.getProperty("user.home") + "/Desktop/visnode.png"));
        BufferedImage imagej = ImageIO.read(new File(System.getProperty("user.home") + "/Desktop/imagej.png"));

        System.out.println(imagej);
        System.out.println(visnode);

        int wrong = 0;
        boolean allGood = true;
        for (int x = 0; x < imagej.getWidth(); x++) {
            for (int y = 0; y < imagej.getHeight(); y++) {
                int rgb1 = imagej.getRGB(x, y);
                int rgb2 = visnode.getRGB(x, y);

                if (rgb1 != rgb2) {
                    allGood = false;
                    System.out.println("diff " + x + "," + y + " " + Integer.toHexString(rgb1) + " " + Integer.toHexString(rgb2));
                    wrong++;
                }
            }
        }
        if (allGood) {
            System.out.println("All good");
        } else {
            System.out.println(wrong + " pixels wrong " + (1d - ((double) wrong / (double) (imagej.getWidth() * imagej.getHeight()))));
        }
    }

}
