package visnode.challenge;

import java.awt.Polygon;

/**
 * Challenge puzzle factory
 */
public class ChallengePuzzleFactory {

    /**
     * Creates a new challenge puzzle
     * 
     * @param pieces
     * @return ChallengePuzzle
     */
    public static ChallengePuzzle create(int pieces) {
        double size = Math.sqrt(pieces);
        int sizeX;
        int sizeY;
        if (size % 1 == 0) {
            sizeX = sizeY = (int) size;
        } else {
            sizeX = (int) Math.ceil(size);
            sizeY = (int) Math.ceil((float) pieces / sizeX);
        }
        ChallengePuzzle puzzle = new ChallengePuzzle(sizeX, sizeY);
        int c = 0;
        for (int y = 0; y < sizeY; y++) {
            if ((c + sizeX) > pieces) {
                sizeX = pieces - c;
            }
            for (int x = 0; x < sizeX; x++) {
                c++;
                float width = (float) 1 / sizeX;
                float height = (float) 1 / sizeY;
                ChallengePuzzlePiece piece = new ChallengePuzzlePiece(c, width, height);
                puzzle.setPiece(x, y, piece);
                for (int i = 0; i < 4; i++) {
                    piece.setSide(i, (int) Math.ceil(Math.random() * 2));
                }
                if (x == 0) {
                    piece.removeSide(3);
                }
                if (x == sizeX - 1) {
                    piece.removeSide(1);
                }
                if (y == 0) {
                    piece.removeSide(0);
                }
                if (y == sizeY - 1) {
                    piece.removeSide(2);
                }
                if (x > 0) {
                    piece.invertSideIfNecessary(3, puzzle.getPieces()[x - 1][y], 1);
                }
                if (y > 0) {
                    piece.invertSideIfNecessary(0, puzzle.getPieces()[x][y - 1], 2);
                }
            }
        }
        return puzzle;
    }
    
    /**
     * Creates a new challenge puzzle piece polygon
     * 
     * @param piece
     * @param width
     * @param height
     * @param posX
     * @param posY
     * @return Polygon
     */
    public static Polygon createPolygon(ChallengePuzzlePiece piece, int width, int height, int posX, int posY) {
        Polygon p = new Polygon();
        p.addPoint(posX, posY);
        if (piece.isMale(0)) {
            p.addPoint((posX + width / 2) - 7, posY);
            p.addPoint((posX + width / 2) - 14, posY - 14);
            p.addPoint((posX + width / 2), posY - 28);
            p.addPoint((posX + width / 2) + 14, posY - 14);
            p.addPoint((posX + width / 2) + 7, posY);
        }
        if (piece.isFemale(0)) {
            p.addPoint((posX + width / 2) - 7, posY);
            p.addPoint((posX + width / 2) - 14, posY + 14);
            p.addPoint((posX + width / 2), posY + 28);
            p.addPoint((posX + width / 2) + 14, posY + 14);
            p.addPoint((posX + width / 2) + 7, posY);
        }
        p.addPoint(posX + width, posY);
        if (piece.isMale(1)) {
            p.addPoint(posX + width, (posY + height / 2) - 7);
            p.addPoint(posX + width - 15, (posY + height / 2) - 15);
            p.addPoint(posX + width - 30, (posY + height / 2));
            p.addPoint(posX + width - 15, (posY + height / 2) + 15);
            p.addPoint(posX + width, (posY + height / 2) + 7);
        }
        if (piece.isFemale(1)) {
            p.addPoint(posX + width, (posY + height / 2) - 7);
            p.addPoint(posX + width + 15, (posY + height / 2) - 15);
            p.addPoint(posX + width + 30, (posY + height / 2));
            p.addPoint(posX + width + 15, (posY + height / 2) + 15);
            p.addPoint(posX + width, (posY + height / 2) + 7);
        }
        p.addPoint(posX + width, posY + height);
        if (piece.isMale(2)) {
            p.addPoint((posX + width / 2) + 7, posY + height);
            p.addPoint((posX + width / 2) + 14, posY + height - 14);
            p.addPoint((posX + width / 2), posY + height - 28);
            p.addPoint((posX + width / 2) - 14, posY + height - 14);
            p.addPoint((posX + width / 2) - 7, posY + height);
        }
        if (piece.isFemale(2)) {
            p.addPoint((posX + width / 2) + 7, posY + height);
            p.addPoint((posX + width / 2) + 14, posY + height + 14);
            p.addPoint((posX + width / 2), posY + height + 28);
            p.addPoint((posX + width / 2) - 14, posY + height + 14);
            p.addPoint((posX + width / 2) - 7, posY + height);
        }
        p.addPoint(posX, posY + height);
        if (piece.isMale(3)) {
            p.addPoint(posX, (posY + height / 2) + 7);
            p.addPoint(posX - 15, (posY + height / 2) + 15);
            p.addPoint(posX - 30, (posY + height / 2));
            p.addPoint(posX - 15, (posY + height / 2) - 15);
            p.addPoint(posX, (posY + height / 2) - 7);
        }
        if (piece.isFemale(3)) {
            p.addPoint(posX, (posY + height / 2) + 7);
            p.addPoint(posX + 15, (posY + height / 2) + 15);
            p.addPoint(posX + 30, (posY + height / 2));
            p.addPoint(posX + 15, (posY + height / 2) - 15);
            p.addPoint(posX, (posY + height / 2) - 7);
        }
        return p;
    }

}
