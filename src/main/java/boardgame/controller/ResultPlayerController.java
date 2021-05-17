package boardgame.controller;

import boardgame.results.BoardGameHandleResults;
import boardgame.results.Player;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ResultPlayerController {

    @FXML
    private TableView<Player> ResultPlayerTable;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Integer> plays;

    @FXML
    private TableColumn<Player, Integer> wins;

    @FXML
    private void initialize() throws IOException {
        List<Player> playerList = BoardGameHandleResults.GetPlayer();

        ResultPlayerTable.setPlaceholder(new Label("Nincs még rögzített eredmény"));

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        plays.setCellValueFactory(new PropertyValueFactory<>("plays"));
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));

        ObservableList<Player> observablePlayer = FXCollections.observableArrayList();
        observablePlayer.addAll(playerList);

        ResultPlayerTable.setItems(observablePlayer);
    }

    @FXML
    private void handleRestartButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResultPlayerController.class.getResource("/fxml/openPage.fxml"));
        Parent root = fxmlLoader.load();
        OpenPageController controller = fxmlLoader.<OpenPageController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleResultButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResultPlayerController.class.getResource("/fxml/result.fxml"));
        Parent root = fxmlLoader.load();
        ResultController controller = fxmlLoader.<ResultController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExitButton(ActionEvent event) throws IOException {
        Platform.exit();
    }

}
