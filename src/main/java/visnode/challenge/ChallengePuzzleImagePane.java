package visnode.challenge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Challenge puzzle image
 */
public class ChallengePuzzleImagePane extends JPanel {

    /** Puzzle */
    private final ChallengePuzzle puzzle;
    /** Image */
    private final BufferedImage buff;
    /** Mission completed */
    private final int missionCompleted;
    
    public ChallengePuzzleImagePane(ChallengePuzzle puzzle, BufferedImage buff, int missionCompleted) {
        this.puzzle = puzzle;
        this.buff = buff;
        this.missionCompleted = missionCompleted;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(buff, 0, 0, this);
        int posX;
        int posY = 0;
        for (int y = 0; y < puzzle.getPieces()[0].length; y++) {
            posX = 0;
            int height = 0;
            for (int x = 0; x < puzzle.getPieces().length; x++) {
                ChallengePuzzlePiece piece = puzzle.getPieces()[x][y];
                if (piece == null) {
                    continue;
                }
                int width = (int) Math.floor(piece.getWidth() * buff.getWidth());
                height = (int) Math.floor(piece.getHeight() * buff.getHeight());
                Polygon p = ChallengePuzzleFactory.createPolygon(piece, width, height, posX, posY);
                if (piece.getLabel() > missionCompleted) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillPolygon(p);
                }
                g2d.setColor(Color.GRAY);
                g2d.drawPolygon(p);
                posX += width;
            }
            posY += height;
        }

    }

}
