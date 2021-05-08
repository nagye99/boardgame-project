package boardgame;

import boardgame.model.BlueDirection;
import boardgame.model.BoardGameModel;
import boardgame.model.Position;
import boardgame.model.RedDirection;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class BoardGameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private enum NextPlayer {
        RED_PLAYER,
        BLUE_PLAYER;

        public NextPlayer alter() {
            return switch (this) {
                case RED_PLAYER -> BLUE_PLAYER;
                case BLUE_PLAYER -> RED_PLAYER;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private NextPlayer nextPlayer = NextPlayer.RED_PLAYER;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private GridPane board;

    @FXML
    private void initialize() {
        createBoard();
        createPieces();
        createBlocks();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane square;
                    square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(40);
        piece.setFill(color);
        return piece;
    }

    private void createBlocks(){
        for(int i =0; i < model.getBlockCount(); i++){
            getSquare(model.getBlockPosition(i)).getStyleClass().remove("square");
            getSquare(model.getBlockPosition(i)).getStyleClass().add("denied");
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        Logger.debug("ClickSquare "+ nextPlayer + selectionPhase);
        switch (nextPlayer){
            case RED_PLAYER -> {
                switch (selectionPhase) {
                    case SELECT_FROM -> {
                        if (selectablePositions.contains(position)) {
                            selectPosition(position);
                            alterSelectionPhase();
                        }
                    }
                    case SELECT_TO -> {
                        if (selectablePositions.contains(position)) {
                            var pieceNumber = model.getPieceNumber(selected).getAsInt();
                            var direction = RedDirection.of(position.row() - selected.row(), position.col() - selected.col());
                            Logger.debug("Moving piece {} {}", pieceNumber, direction);
                            model.move(pieceNumber, direction);
                            deselectSelectedPosition();
                            nextPlayer = nextPlayer.alter();
                            alterSelectionPhase();
                        }
                    }
                }
            }
            case BLUE_PLAYER -> {
                switch (selectionPhase) {
                    case SELECT_FROM -> {
                        if (selectablePositions.contains(position)) {
                            selectPosition(position);
                            alterSelectionPhase();
                        }
                    }
                    case SELECT_TO -> {
                        if (selectablePositions.contains(position)) {
                            var pieceNumber = model.getPieceNumber(selected).getAsInt();
                            var direction = BlueDirection.of(position.row() - selected.row(), position.col() - selected.col());
                            Logger.debug("Moving piece {} {}", pieceNumber, direction);
                            model.move(pieceNumber, direction);
                            deselectSelectedPosition();
                            nextPlayer = nextPlayer.alter();
                            alterSelectionPhase();
                        }
                    }
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        Logger.debug("selectablePosition" + nextPlayer);
        switch (nextPlayer){
            case RED_PLAYER ->{
                switch (selectionPhase) {
                    case SELECT_FROM -> selectablePositions.addAll(model.getRedPositions());
                    case SELECT_TO -> {
                        var pieceNumber = model.getPieceNumber(selected).getAsInt();
                        for (var direction : model.getValidRedMoves(selected)) {
                            selectablePositions.add(selected.moveTo(direction));
                        }
                    }
                }
            }
            case BLUE_PLAYER -> {
                switch (selectionPhase) {
                    case SELECT_FROM -> selectablePositions.addAll(model.getBluePositions());
                    case SELECT_TO -> {
                        var pieceNumber = model.getPieceNumber(selected).getAsInt();
                        for (var direction : model.getValidBlueMoves(selected)) {
                            selectablePositions.add(selected.moveTo(direction));
                        }
                    }
                }
            }
        }

    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }
}
