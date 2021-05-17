package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

/**
 * The class is the model of the pieces in the table.
 */
public class Piece {

    private final PieceColor type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    /**
     * Creates {@code Piece} object.
     *
     * @param type the colour of the piece
     * @param position the {@code Position} of the piece
     */
    public Piece(PieceColor type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * It gives the colour of the actual piece.
     *
     * @return {@code PieceColor} object which represents the colour of the piece
     */
    public PieceColor getType() {
        return type;
    }

    /**
     * It gives the position of the actual piece.
     *
     * @return {@code Position} object which represents the position of the actual piece
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * It gives the position of the actual piece as property.
     *
     * @return the {@ObjectProperty} of the {@Position} the actual piece
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * It represents a move in the table.
     * It change the property of position of the piece. The method calculate the new {@code Position} about the given direction and the actual position of the piece. It replace the old position of the piece to its new position.
     *
     * @param direction that represents the {@code Direction} of the move
     */
    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return type == piece.type && position.get().equals(piece.position.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position.get());
    }

    @Override
    public String toString() {
        return "\ncolor: " + type.toString() + " position: " + position.get().toString() ;
    }
}
