package boardgame.model;

import java.util.Objects;

public record Position(int row, int col) {

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

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

    public static void main(String[] args) {
        Position pos = new Position(1,1);
        System.out.println(pos.equals(new Position(1,1)));
    }

}