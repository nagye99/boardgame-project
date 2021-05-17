package boardgame.controller;

import boardgame.results.BoardGameHandleResults;
import boardgame.results.Results;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResultController {

    @FXML
    private TableView<Results> ResultTable;

    @FXML
    private TableColumn<Results, String> red_player;

    @FXML
    private TableColumn<Results, String> blue_player;

    @FXML
    private TableColumn<Results, String> winner;

    @FXML
    private TableColumn<Results, Integer> steps;

    @FXML
    private TableColumn<Results, String> duration;

    @FXML
    private TableColumn<Results, LocalDateTime> gameTime;

    @FXML
    private void initialize() throws IOException {
        List<Results> resultList = BoardGameHandleResults.GetResults();

        ResultTable.setPlaceholder(new Label("Nincs még rögzített eredmény"));

        red_player.setCellValueFactory(new PropertyValueFactory<>("red_player"));
        blue_player.setCellValueFactory(new PropertyValueFactory<>("blue_player"));
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        gameTime.setCellValueFactory(new PropertyValueFactory<>("gameTime"));

        gameTime.setCellFactory(column -> {
            TableCell<Results, LocalDateTime> cell = new TableCell<Results, LocalDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                }
            };
            return cell;
        });

        ObservableList<Results> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(resultList);

        ResultTable.setItems(observableResult);
    }

    @FXML
    private void handleRestartButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResultController.class.getResource("/fxml/openPage.fxml"));
        Parent root = fxmlLoader.load();
        OpenPageController controller = fxmlLoader.<OpenPageController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleResultPlayerButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResultPlayerController.class.getResource("/fxml/resultPlayer.fxml"));
        Parent root = fxmlLoader.load();
        ResultPlayerController controller = fxmlLoader.<ResultPlayerController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit();
    }

}
