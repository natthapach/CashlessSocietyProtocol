package views;

import controllers.CoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DepositView {

    @FXML private TextField amountTextField;
    private CoreController controller;

    public void setController(CoreController controller) {
        this.controller = controller;
    }

    @FXML
    private void onClickDeposit(ActionEvent actionEvent) {
        try {
            controller.deposit(Double.parseDouble(amountTextField.getText()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickCancel(ActionEvent actionEvent) {
        controller.changeToHomeScene();
    }
}
