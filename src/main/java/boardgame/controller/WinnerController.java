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
import org.tinylog.Logger;

import java.io.IOException;

public class WinnerController {

    @FXML
    private Label winnerColorLabel;
    public Label winnerNameLabel;
    public Label loserNameLabel;

    @FXML
    private void initialize() {
        System.out.println(this.color);
        if (this.color.get()=="piros"){
            winnerColorLabel.setTextFill(Color.RED);
            winnerNameLabel.setTextFill(Color.RED);
            loserNameLabel.setTextFill(Color.BLUE);
        }
        else{
            winnerColorLabel.setTextFill(Color.BLUE);
            winnerNameLabel.setTextFill(Color.BLUE);
            loserNameLabel.setTextFill(Color.RED);
        }
        winnerColorLabel.textProperty().bind(Bindings.concat("A ", color, " játékos nyert."));
        winnerNameLabel.textProperty().bind(Bindings.concat("Szép volt ",winnerName,"!"));
        loserNameLabel.textProperty().bind(Bindings.concat("Ne csüggedj ",loserName,"! Legközelebb jobb lesz."));
    }

    private StringProperty color = new SimpleStringProperty();
    private StringProperty winnerName = new SimpleStringProperty();
    private StringProperty loserName = new SimpleStringProperty();

    public void setWinner(String color, String winnerName, String loserName) {
        this.color.set(color);
        Logger.error(color);
        this.winnerName.set(winnerName);
        this.loserName.set(loserName);
    }

    public void handleResultButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WinnerController.class.getResource("/result.fxml"));
        Parent root = fxmlLoader.load();
        ResultController controller = fxmlLoader.<ResultController>getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        WinnerController ez = new WinnerController();
        ez.setWinner("piros", "A","B");
        System.out.println(ez.color.get().equals("piros"));
    }
}