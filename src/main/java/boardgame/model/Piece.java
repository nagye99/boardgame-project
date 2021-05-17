package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class Piece {

    private final PieceColor type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceColor type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    public PieceColor getType() {
        return type;
    }

    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

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
