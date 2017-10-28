package views;

import controllers.CoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CheckBalanceView {
    @FXML private TextField balanceTextField;
    private CoreController controller;

    public void setController(CoreController controller) {
        this.controller = controller;
    }

    @FXML
    private void onClickBack(ActionEvent actionEvent) {
        controller.changeToHomeScene();
    }

    public void setBalace(double balance){
        balanceTextField.setText(balance + "");
    }
}
