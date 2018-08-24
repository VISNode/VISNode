package visnode.challenge;

/**
 * Challenge puzzle piece
 */
public class ChallengePuzzlePiece {

    /** Label */
    private final int label;
    /** Width */
    private final float width;
    /** Height */
    private final float height;
    /** Sides */
    private final int[] side;

    public ChallengePuzzlePiece(int label, float width, float height) {
        this.label = label;
        this.width = width;
        this.height = height;
        this.side = new int[4];
    }

    /**
     * Returns the label
     *
     * @return int
     */
    public int getLabel() {
        return label;
    }

    /**
     * Returns the width
     *
     * @return float
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height
     *
     * @return float
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the sides
     *
     * @param side
     * @param option
     */
    public void setSide(int side, int option) {
        this.side[side] = option;
    }

    /**
     * Remove a side
     *
     * @param side
     */
    public void removeSide(int side) {
        this.side[side] = 0;
    }

    /**
     * Inverts a side
     *
     * @param side
     */
    public void invertSide(int side) {
        if (isFemale(side)) {
            setSideMale(side);
        } else {
            setSideFemale(side);
        }
    }

    /**
     * Inverts a side if necessary
     *
     * @param side
     * @param piece
     * @param side2
     */
    public void invertSideIfNecessary(int side, ChallengePuzzlePiece piece, int side2) {
        if (this.side[side] != piece.getSide(side2)) {
            invertSide(side);
        }
    }

    /**
     * Sets the side to male
     *
     * @param side
     */
    public void setSideMale(int side) {
        this.side[side] = 1;
    }

    /**
     * Sets the side to female
     *
     * @param side
     */
    public void setSideFemale(int side) {
        this.side[side] = 2;
    }

    /**
     * Returns true if the side is male
     *
     * @param side
     * @return boolean
     */
    public boolean isMale(int side) {
        return this.side[side] == 1;
    }

    /**
     * Returns true if the side is female
     *
     * @param side
     * @return boolean
     */
    public boolean isFemale(int side) {
        return this.side[side] == 2;
    }

    /**
     * Returns the side
     *
     * @param side
     * @return int
     */
    public int getSide(int side) {
        return this.side[side];
    }

}
