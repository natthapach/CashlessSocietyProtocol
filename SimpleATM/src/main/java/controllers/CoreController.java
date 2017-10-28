package controllers;

import exceptions.IdNotFoundException;
import exceptions.InsufficientFundException;

public class CoreController {
    private StageController stageController;
    private ClientManager clientManager;
    private String account;

    public void deposit(double amt){
        try {
            double balance = clientManager.deposit(account, amt);
            changeToBalanceScene(balance);
        } catch (IdNotFoundException e) {
            stageController.setHomeScene();
            stageController.showAlertWrongId();
        }
    }

    public void withdraw(double amt){
        try {
            double balance = clientManager.withdraw(account, amt);
            changeToBalanceScene(balance);
        } catch (InsufficientFundException e) {
            stageController.showAlertInsufficientFund();
        } catch (IdNotFoundException e) {
            stageController.setHomeScene();
            stageController.showAlertWrongId();
        }
    }

    public void transfer(String targetAccount, double amt){
        try {
            double balance = clientManager.transfer(account, targetAccount, amt);
            changeToBalanceScene(balance);
        } catch (InsufficientFundException e) {
            stageController.showAlertInsufficientFund();
        } catch (IdNotFoundException e) {
            stageController.setHomeScene();
            stageController.showAlertWrongId();
        }
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void changeToHomeScene(){
        stageController.setHomeScene();
    }
    public void changeToDepositScene(){
        stageController.setDepositScene();
    }
    public void changeToWithdrawScene(){
        stageController.setWithdrawScene();
    }
    public void changeToTransferScene(){
        stageController.setTransferScene();
    }
    public void changeToBalanceScene(){
        try {
            double balance = clientManager.checkBalance(account);
            changeToBalanceScene(balance);
        } catch (IdNotFoundException e) {
            stageController.setHomeScene();
            stageController.showAlertWrongId();
        }
    }
    public void changeToBalanceScene(double balance){
        stageController.setCheckBalanceScene(balance);
    }


}
