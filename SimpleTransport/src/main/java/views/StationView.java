package views;

import controllers.CoreController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class StationView {
    @FXML private TextField priceTextField;
    @FXML private ChoiceBox sourceChoiceBox;
    @FXML private ChoiceBox destinationChoiceBox;
    @FXML private TextField balanceTextField;
    private CoreController controller;

    public void initialize(){
        if (controller != null)
            initChoiceBox();
    }
    public void setController(CoreController controller) {
        this.controller = controller;

        if (sourceChoiceBox != null)
            initChoiceBox();
    }

    @FXML
    private void onClickOK(ActionEvent actionEvent) {
        String source = sourceChoiceBox.getSelectionModel().getSelectedItem().toString();
        String dest = destinationChoiceBox.getSelectionModel().getSelectedItem().toString();
        double balance = controller.pay(source, dest);
        if (balance >= 0)
            balanceTextField.setText(balance + "");
    }

    public void setBalance(double balance){
        balanceTextField.setText(balance + "");
    }

    @FXML
    private void onClickBack(ActionEvent actionEvent) {
        controller.changeToHomeScene();
    }

    private void initChoiceBox(){
        sourceChoiceBox.setItems(FXCollections.observableList(controller.getStations()));
        destinationChoiceBox.setItems(FXCollections.observableList(controller.getStations()));

        sourceChoiceBox.getSelectionModel().selectFirst();
        destinationChoiceBox.getSelectionModel().selectFirst();
    }

    public void onSelectStation(ActionEvent actionEvent) {
        if (sourceChoiceBox.getSelectionModel().getSelectedItem()!=null && destinationChoiceBox.getSelectionModel().getSelectedItem()!=null){
            double price = controller.getPrice(sourceChoiceBox.getSelectionModel().getSelectedItem().toString(), destinationChoiceBox.getSelectionModel().getSelectedItem().toString());
            priceTextField.setText(price + "");
        }
    }
}
