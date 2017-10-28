package views;

import controllers.CoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TransferView {

    @FXML private TextField amountTextField;
    @FXML private TextField targetAccountTextField;
    private CoreController controller;

    public void setController(CoreController controller) {
        this.controller = controller;
    }

    @FXML
    private void onClickCancel(ActionEvent actionEvent) {
        controller.changeToHomeScene();
    }

    @FXML
    private void onClickTransfer(ActionEvent actionEvent) {
        try {
            controller.transfer(targetAccountTextField.getText(), Double.parseDouble(amountTextField.getText()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
