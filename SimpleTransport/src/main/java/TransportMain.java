import controllers.ClientManager;
import controllers.CoreController;
import controllers.StageController;
import javafx.application.Application;
import javafx.stage.Stage;

public class TransportMain extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        CoreController coreController = new CoreController();
        ClientManager clientManager = new ClientManager();
        StageController stageController = new StageController(primaryStage, coreController);
        coreController.setClientManager(clientManager);
        coreController.setStageController(stageController);

        stageController.setHomeScene();
        primaryStage.show();
    }
}
