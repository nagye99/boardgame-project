package boardgame.model;

import java.util.Objects;

/**
 * Class represents the positions of the gametable.
 *
 * @param row gives the row number of position
 * @param col gives the column number of position
 */
public record Position(int row, int col) {

    /**
     * This method calculates the position after a step of the given position.
     *
     * @param direction that represents that direction of move
     * @return the new position after the step
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}