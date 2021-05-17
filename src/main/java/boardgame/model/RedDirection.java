package boardgame.model;

/**
 * This class represents the {@link boardgame.model.Direction} where the red pieces can move.
 */
public enum RedDirection implements Direction {

    /**
     * This is the {@code DOWN_RIGHT} direction which means the piece step one down and after that one right so it is a diagonal step.
     */
    DOWN_RIGHT(1, 1),

    /**
     * This is the {@code DOWN} direction when the piece step one field down.
     */
    DOWN(1, 0),

    /**
     * This is the {@code DOWN_LEFT} direction which means the piece step one down and after that one left so it is a diagonal step.
     */
    DOWN_LEFT(1, -1);

    private final int rowChange;
    private final int colChange;

    /**
     * Creates a {@code RedDirection} object.
     *
     * @param rowChange the row coordinate of the change of position
     * @param colChange the column coordinate of the change of position
     */
    RedDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * The getter for the first paramater of the direction.
     * It represents the change of the row number.
     *
     * @return the difference between old and new row number
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * The getter for the second paramater of the direction.
     * It represents the change of the column number.
     *
     * @return the difference between old and new column number
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * It deciphers the direction of the move about the row and column changes.
     *
     * @param rowChange the difference between the old and new row number
     * @param colChange the difference between the old and new column number
     * @return the appropiate direction of the {@code RedDirection}
     */
    public static RedDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
