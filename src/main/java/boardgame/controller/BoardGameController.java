package boardgame.controller;

import boardgame.model.*;
import boardgame.results.BoardGameHandleResults;
import boardgame.results.Results;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.tinylog.Logger;
import javafx.scene.control.Label;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;
    private ObjectProperty<NextPlayer> nextPlayer = new SimpleObjectProperty<>();
    private List<Position> selectablePositions = new ArrayList<>();
    private Position selected;
    private BoardGameModel model;
    private Timeline stopWatchTimeline;
    private Instant startTime;
    private IntegerProperty steps = new SimpleIntegerProperty(0);
    private StringProperty redName = new SimpleStringProperty();
    private StringProperty blueName = new SimpleStringProperty();


    @FXML
    private GridPane board;

    @FXML
    private Label redPlayerLabel;

    @FXML
    private Label bluePlayerLabel;

    @FXML
    private Label nextPlayerLabel;

    @FXML
    private Label stopWatchLabel;

    @FXML
    private Label stepsLabel;

    @FXML
    private void initialize() {
        model = new BoardGameModel();
        Bindings.bindBidirectional(nextPlayer, model.nextPlayer);
        model.nextPlayerProperty().addListener(this::nextPlayerChange);
        createBoard();
        createPieces();
        createBlocks();
        setSelectablePositions();
        showSelectablePositions();
        writeOutName();
        createStopWatch();
        stepsLabel.textProperty().bind(steps.asString());
        Logger.info("Init ready");
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

    private void createBlocks() {
        for (int i = 0; i < model.getBlockCount(); i++) {
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
        Logger.debug("ClickSquare " + nextPlayer + selectionPhase);
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selected.equals(position)) {
                    deselectSelectedPosition();
                    alterSelectionPhase();
                } else if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected).getAsInt();
                    var direction = model.getDirection(selected, position);
                    Logger.debug("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    steps.set(steps.get() + 1);
                    deselectSelectedPosition();
                    if (!model.canBlueMove() && nextPlayer.get() == NextPlayer.BLUE_PLAYER || !model.canRedMove() && nextPlayer.get() == NextPlayer.RED_PLAYER) {
                        endGame(nextPlayer.get());
                    }
                    alterSelectionPhase();
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

    private void setSelectablePositions() {
        selectablePositions.clear();
        Logger.debug("selectablePosition" + nextPlayer);
        switch (nextPlayer.get()) {
            case RED_PLAYER -> {
                switch (selectionPhase) {
                    case SELECT_FROM -> selectablePositions.addAll(model.getRedPositions());
                    case SELECT_TO -> {
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

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void endGame(NextPlayer nextPlayer) {
        stopWatchTimeline.stop();
        String winner;
        String loser;
        String color;
        switch (nextPlayer) {
            case RED_PLAYER -> {
                winner = blueName.get();
                loser = redName.get();
                color = "kék";
            }
            case BLUE_PLAYER -> {
                winner = redName.get();
                loser = blueName.get();
                color = "piros";
            }
            default -> throw new IllegalStateException("Unexpected value: " + nextPlayer);
        }
        Results result = new Results(redName.get(), blueName.get(), winner, steps.get(), DurationFormatUtils.formatDuration(startTime.until(Instant.now(), ChronoUnit.MILLIS), "HH:mm:ss"), LocalDateTime.now());
        BoardGameHandleResults.SaveResult(result);
        BoardGameHandleResults.SaveWinnerPlayer(winner);
        BoardGameHandleResults.SaveLoserPlayer(loser);
        changeScene(color, winner, loser);
    }

    @FXML
    private void changeScene(String color, String winnerName, String loserName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OpenPageController.class.getResource("/fxml/winner.fxml"));
            Logger.warn(BoardGameController.class);
            //Logger.error(BoardGameController.class.getResource("/fxml/winner.fxml"));
            Parent root = fxmlLoader.load();
            WinnerController controller = fxmlLoader.<WinnerController>getController();
            controller.setWinner(color, winnerName, loserName);
            Stage stage = (Stage) ((Node) board).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
            e.getCause();
            e.getMessage();
            //Logger.error("Nem található az fxml.");
        }
    }

    public void setName(String player1, String player2) {
        this.redName.set(player1);
        this.blueName.set(player2);
    }

    private void writeOutName() {
        redPlayerLabel.textProperty().bind(redName);
        bluePlayerLabel.textProperty().bind(blueName);
    }

    private void createStopWatch() {
        startTime = Instant.now();
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

    @FXML
    private void handleResultButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WinnerController.class.getResource("/fxml/result.fxml"));
        Parent root = fxmlLoader.load();
        ResultController controller = fxmlLoader.<ResultController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleRestartButton(InputEvent event) throws IOException {
        Logger.error(event);
        FXMLLoader fxmlLoader = new FXMLLoader(ResultController.class.getResource("/fxml/openPage.fxml"));
        Parent root = fxmlLoader.load();
        OpenPageController controller = fxmlLoader.<OpenPageController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void nextPlayerChange(ObservableValue<? extends NextPlayer> observable, NextPlayer oldPlayer, NextPlayer newPlayer) {
        if (newPlayer == NextPlayer.RED_PLAYER) {
            nextPlayerLabel.setText("A piros játékos következik");
            nextPlayerLabel.setTextFill(Color.RED);
        } else if (newPlayer == NextPlayer.BLUE_PLAYER) {
            nextPlayerLabel.setText("A kék játékos következik");
            nextPlayerLabel.setTextFill(Color.BLUE);
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