package visnode.challenge;

/**
 * Challenge puzzle
 */
public class ChallengePuzzle {

    /** Puzzle pieces */
    private final ChallengePuzzlePiece[][] pieces;
    
    public ChallengePuzzle(int width, int height) {
        this.pieces = new ChallengePuzzlePiece[width][height];
    }

    /**
     * Sets the challenge puzzle pieces
     * 
     * @param x
     * @param y
     * @param piece 
     */
    public void setPiece(int x, int y, ChallengePuzzlePiece piece) {
        pieces[x][y] = piece;
    }
    
    /**
     * Returns the challenge puzzle pieces
     * 
     * @return {@code ChallengePuzzlePiece[][]}
     */
    public ChallengePuzzlePiece[][] getPieces() {
        return pieces;
    }
    
}
