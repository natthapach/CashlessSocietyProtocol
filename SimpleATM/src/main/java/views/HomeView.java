package views;

import controllers.CoreController;
import controllers.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeView {
    @FXML private TextField accountTextField;
    private CoreController controller;

    public void setController(CoreController controller) {
        this.controller = controller;
    }

    @FXML
    private void onClickDeposit(){
        controller.setAccount(accountTextField.getText());
        controller.changeToDepositScene();
    }
    @FXML
    private void onClickWithdraw(){
        controller.setAccount(accountTextField.getText());
        controller.changeToWithdrawScene();
    }
    @FXML
    private void onClickCheckBalance(){
        controller.setAccount(accountTextField.getText());
        controller.changeToBalanceScene();
    }
    @FXML
    private void onClickTransfer(){
        controller.setAccount(accountTextField.getText());
        controller.changeToTransferScene();
    }
}
