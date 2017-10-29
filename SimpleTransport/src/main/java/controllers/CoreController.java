package controllers;

import exceptions.IdNotFoundException;
import exceptions.InsufficientFundException;
import models.StationManager;

import java.util.List;

public class CoreController {
    private StageController stageController;
    private ClientManager clientManager;
    private String id;
    private StationManager stationManager = new StationManager();

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void changeToStationScene(){
        try {
            double balance = clientManager.getBalance(id);
            stageController.setStationScene(balance);
        } catch (IdNotFoundException e) {
            stageController.showAlertWrongId();
        }
    }

    public void changeToHomeScene(){
        stageController.setHomeScene();
    }

    public List<String> getStations(){
        return stationManager.getStations();
    }

    public double getPrice(String source, String dest){
        return stationManager.getPrice(source, dest);
    }

    public double pay(String source, String dest){
        double price = stationManager.getPrice(source, dest);
        try {
            double balance = clientManager.pay(id, source, dest, price);
            return balance;
        } catch (IdNotFoundException e) {
            stageController.showAlertWrongId();
        } catch (InsufficientFundException e) {
            stageController.showAlertInsufficientFund();
        }
        return -1;
    }
}
