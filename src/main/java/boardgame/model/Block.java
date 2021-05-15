package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

public class Block {
    private final Position position;
    public Block(Position position){
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }
}
