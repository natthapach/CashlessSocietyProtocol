package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import views.HomeTransportView;
import views.StationView;

import java.io.IOException;

public class StageController {
    private Stage stage;
    private CoreController controller;
    public StageController(Stage stage, CoreController controller) {
        this.stage = stage;
        this.controller = controller;
        controller.setStageController(this);
    }

    public void setHomeScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HomeTransportView.fxml"));
            Pane layout = loader.load();
            HomeTransportView controller = loader.getController();
            controller.setController(this.controller);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStationScene(double balance){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/StationView.fxml"));
            Pane layout = loader.load();
            StationView controller = loader.getController();
            controller.setController(this.controller);
            controller.setBalance(balance);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlertInsufficientFund(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("STATION MACHINE ERROR");
        alert.setHeaderText("Insufficient fund");
        alert.setContentText("Your balance is not enough for this transaction");

        alert.showAndWait();
    }
    public void showAlertWrongId(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("STATION MACHINE ERROR");
        alert.setHeaderText("Wrong ID");
        alert.setContentText("This ID is not found in server. Please enter new ID");

        alert.showAndWait();
    }
}
