import controllers.ClientManager;
import controllers.CoreController;
import controllers.StageController;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;
import exceptions.BtagNotSupportException;
import exceptions.VersionNotSupportException;
import javafx.application.Application;
import javafx.stage.Stage;
import parser.Parser;
import tokens.GeneralToken;
import tokens.RequestToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by PC301 on 25/10/2560.
 */
public class ATMMain extends Application {
    public static void main(String[] args){

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        CoreController coreController = new CoreController();
        StageController stageController = new StageController(primaryStage, coreController);
        ClientManager clientManager = new ClientManager();
        coreController.setClientManager(clientManager);

        primaryStage.show();
        stageController.setHomeScene();
    }
}
