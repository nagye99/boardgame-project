package boardgame.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class WinnerController {

    private StringProperty color = new SimpleStringProperty();
    private StringProperty winnerName = new SimpleStringProperty();
    private StringProperty loserName = new SimpleStringProperty();

    @FXML
    private Label winnerColorLabel;
    public Label winnerNameLabel;
    public Label loserNameLabel;

    @FXML
    private void initialize() {
        winnerColorLabel.textProperty().bind(Bindings.concat("A ", color, " játékos nyert."));
        winnerNameLabel.textProperty().bind(Bindings.concat("Szép volt ",winnerName,"!"));
        loserNameLabel.textProperty().bind(Bindings.concat("Ne csüggedj ",loserName,"! Legközelebb jobb lesz."));
    }

    public void setWinner(String color, String winnerName, String loserName) {
        this.color.set(color);
        this.winnerName.set(winnerName);
        this.loserName.set(loserName);
        if (color =="piros"){
            winnerColorLabel.setTextFill(Color.RED);
            winnerNameLabel.setTextFill(Color.RED);
            loserNameLabel.setTextFill(Color.BLUE);
        }
        else if(color == "kék"){
            winnerColorLabel.setTextFill(Color.BLUE);
            winnerNameLabel.setTextFill(Color.BLUE);
            loserNameLabel.setTextFill(Color.RED);
        }
    }

    public void handleResultButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WinnerController.class.getResource("/fxml/result.fxml"));
        Parent root = fxmlLoader.load();
        ResultController controller = fxmlLoader.<ResultController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        //WinnerController ez = new WinnerController();
        //ez.setWinner("piros", "A","B");
        //System.out.println(ez.color.get().equals("piros"));
    }
}
