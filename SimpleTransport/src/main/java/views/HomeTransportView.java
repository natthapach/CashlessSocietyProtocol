package views;

import controllers.CoreController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomeTransportView {
    @FXML private TextField idTextField;
    private CoreController controller;

    public void setController(CoreController controller) {
        this.controller = controller;
    }

    @FXML
    private void onClickOk(){
        controller.setId(idTextField.getText());
        controller.changeToStationScene();
    }
}
