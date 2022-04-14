import controller.MainAppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;

    private MainAppController appController;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MasterMind");

        this.appController = new MainAppController(primaryStage);
        this.appController.initRootLayout();

    }

//    public static void main(String[] args) {
//        launch(args);
//    }


}