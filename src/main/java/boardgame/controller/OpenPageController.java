package boardgame.controller;

import boardgame.BoardGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class OpenPageController {
    @FXML
    private TextField player1TextField;
    @FXML
    private TextField player2TextField;
    @FXML
    private Label errorLabel;

    public void handleGameButton(ActionEvent event) throws IOException {
        if(player1TextField.getText().isEmpty() || player2TextField.getText().isEmpty()){
            errorLabel.setText("Kötelező kitölteni a két játékos nevének a mezőjét!");
        }
        else{
            FXMLLoader fxmlLoader = new FXMLLoader(OpenPageController.class.getResource("/boardGame.fxml"));
            Logger.warn(OpenPageController.class.getResource("/boardGame.fxml"));
            Parent root = fxmlLoader.load();
            BoardGameController controller = fxmlLoader.<BoardGameController>getController();
            controller.setName(player1TextField.getText(), player2TextField.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void handleRuleButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OpenPageController.class.getResource("/rules.fxml"));
        Logger.error(OpenPageController.class.getResource("/rules.fxml"));
        Parent root = fxmlLoader.load();
        RulesController controller = fxmlLoader.<RulesController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
