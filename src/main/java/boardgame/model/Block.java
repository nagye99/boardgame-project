package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Block {
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();
    public Block(Position position){
        this.position.set(position);
    }
    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }
}
