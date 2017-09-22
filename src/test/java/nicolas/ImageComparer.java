package nicolas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageComparer {

   public static void main(String[] args) throws IOException {
        BufferedImage imagej = ImageIO.read(new File("/home/jonatabecker/Desktop/imagej.png"));
        BufferedImage visnode = ImageIO.read(new File("/home/jonatabecker/Desktop/visnode.png"));
        
       System.out.println(imagej);
        System.out.println(visnode);
        
       int wrong = 0;
        boolean allGood = true;
        for (int x = 0; x < imagej.getWidth(); x++) {
            for (int y = 0; y < imagej.getHeight(); y++) {
                int rgb1 = imagej.getRGB(x, y);
                int rgb2 = visnode.getRGB(x, y);
                
                Color rgb1Color = new Color(rgb1);
                Color rgb2Color = new Color(rgb2);
                
                if (rgb1 != rgb2) {
                    allGood = false;
                    System.out.println("diff " + x + "," + y + " " + rgb1Color + " " + rgb2Color);
                    wrong ++;
                }
            }
        }
        if (allGood) System.out.println("All good");
        else System.out.println(wrong + " pixels wrong");
    }
    
}