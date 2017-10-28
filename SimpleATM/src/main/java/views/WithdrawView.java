package views;

import controllers.CoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WithdrawView {

    @FXML private TextField amountTextField;
    private CoreController controller;

    public void setController(CoreController controller) {
        this.controller = controller;
    }

    @FXML
    private void onClickCancel(ActionEvent actionEvent) {
        controller.changeToHomeScene();
    }

    @FXML
    private void onClickWithDraw(ActionEvent actionEvent) {
        try {
            controller.withdraw(Double.parseDouble(amountTextField.getText()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
