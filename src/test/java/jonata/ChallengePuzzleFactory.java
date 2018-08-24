/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonata;

import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.paim.commons.Image;
import org.paim.commons.ImageConverter;
import org.paim.commons.ImageFactory;
import visnode.ImageAssert;
import visnode.challenge.ChallengePuzzle;
import static visnode.challenge.ChallengePuzzleFactory.createPolygon;
import visnode.challenge.ChallengePuzzlePiece;
import visnode.user.Base64Image;

/**
 *
 * @author jonatabecker
 */
public class ChallengePuzzleFactory {

    private static final int SIZE_WITH = 300;
    private static final int SIZE_HEIGHT = 400;

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 600));
        frame.setLayout(new BorderLayout());
        
        
//        
        File file = new File("/home/jonatabecker/Desktop/TCC/feneraria/mission1.jpg");
        BufferedImage buff = ImageIO.read(file);
        
        
        Base64Image b = new Base64Image();
        
        Image image = ImageFactory.buildRGBImage(buff);
        
        String teste = b.toBase64(file);
        
                
        buff = b.fromBase64(teste);
        
        frame.add(new JLabel(new ImageIcon(buff)));
        
        Image image2 = ImageFactory.buildRGBImage(buff);
        
        ImageAssert.assertImage(image, image2);
        
        
//        frame.add(new Panel(create(16)));
        frame.setVisible(true);

    }

    public static class Panel extends JPanel {

        private final ChallengePuzzle puzzle;

        public Panel(ChallengePuzzle puzzle) {
            this.puzzle = puzzle;
        }

        @Override
        public void paint(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            int posX;
            int posY = 0;
            for (int y = 0; y < puzzle.getPieces()[0].length; y++) {
                posX = 0;
                int height = 0;
                for (int x = 0; x < puzzle.getPieces().length; x++) {
                    ChallengePuzzlePiece piece = puzzle.getPieces()[x][y];
                    if (piece == null) {
                        return;
                    }
                    int width = (int) Math.floor(piece.getWidth() * SIZE_WITH);
                    height = (int) Math.floor(piece.getHeight() * SIZE_HEIGHT);
                    Polygon p = createPolygon(piece, width, height, posX, posY);

                    g2d.drawPolygon(p);

                    posX += width;

                }
                posY += height;
            }

        }

    }
}
