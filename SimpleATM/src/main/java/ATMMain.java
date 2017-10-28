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

        String sentence;
        String modifiedSentence;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

//        Socket clientSocket = new Socket("localhost", 6789);
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

//        sentence = inFromUser.readLine();
//        System.out.println("sending...");
//        outToServer.writeBytes("line1" + '\n');
//        outToServer.writeBytes("line1.5" + '\n');
//        modifiedSentence = inFromServer.readLine();
//        System.out.println("FROM SERVER: " + modifiedSentence);
//        outToServer.writeBytes("line2\n");
//        modifiedSentence = inFromServer.readLine();
//        System.out.println("FROM SERVER: " + modifiedSentence);
//        clientSocket.close();

//        Parser parser = new Parser();
//        RequestMessage requestMessage = new RequestMessage(RequestToken.Method.BALANCE, GeneralToken.CURRENT_VERSION, "5810400981", null, "today", 200, "atm", null, null);
//        try {
//            String msg = parser.parseToString(requestMessage);
//            System.out.println("---message to server---");
//            System.out.println(msg);
//            outToServer.writeBytes(msg);
//            ResponseMessage responseMessage = parser.parseToResponse(inFromServer);
//            clientSocket.close();
//            System.out.println("responseMessage.getBalance() = " + responseMessage.getBalance());
//        } catch (BtagNotSupportException e) {
//            e.printStackTrace();
//        } catch (VersionNotSupportException e) {
//            e.printStackTrace();
//        } catch (BadRequestException e) {
//            e.printStackTrace();
//        }
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
