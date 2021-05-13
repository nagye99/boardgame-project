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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.commons.lang3.time.DurationFormatUtils;

import javax.xml.transform.Result;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
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
    private void initialize() throws IOException {
        List<Results> resultList = BoardGameHandleResults.GetResults();

        red_player.setCellValueFactory(new PropertyValueFactory<>("red_player"));
        blue_player.setCellValueFactory(new PropertyValueFactory<>("blue_player"));
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        ObservableList<Results> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(resultList);

        ResultTable.setItems(observableResult);
    }

    public void handleRestartButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResultController.class.getResource("/fxml/openPage.fxml"));
        Parent root = fxmlLoader.load();
        OpenPageController controller = fxmlLoader.<OpenPageController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleResultPlayerButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResultPlayerController.class.getResource("/fxml/resultPlayer.fxml"));
        Parent root = fxmlLoader.load();
        ResultPlayerController controller = fxmlLoader.<ResultPlayerController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleExitButton(ActionEvent event) throws IOException {
        Platform.exit();
    }

}
