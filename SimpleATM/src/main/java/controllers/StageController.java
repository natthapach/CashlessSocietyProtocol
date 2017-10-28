package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import views.*;

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
            loader.setLocation(getClass().getResource("/HomeView.fxml"));
            Pane layout = loader.load();
            HomeView controller = loader.getController();
            controller.setController(this.controller);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDepositScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/DepositView.fxml"));
            Pane layout = loader.load();
            DepositView controller = loader.getController();
            controller.setController(this.controller);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setWithdrawScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/WithdrawView.fxml"));
            Pane layout = loader.load();
            WithdrawView controller = loader.getController();
            controller.setController(this.controller);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setTransferScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/TransferView.fxml"));
            Pane layout = loader.load();
            TransferView controller = loader.getController();
            controller.setController(this.controller);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setCheckBalanceScene(double balance){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/CheckBalanceView.fxml"));
            Pane layout = loader.load();
            CheckBalanceView controller = loader.getController();
            controller.setController(this.controller);
            controller.setBalace(balance);

            Scene sc = new Scene(layout);
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAlertInsufficientFund(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ATM ERROR");
        alert.setHeaderText("Insufficient fund");
        alert.setContentText("Your balance is not enough for this transaction");

        alert.showAndWait();
    }
    public void showAlertWrongId(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ATM ERROR");
        alert.setHeaderText("Wrong ID");
        alert.setContentText("This ID is not found in server. Please enter new ID");

        alert.showAndWait();
    }
}
