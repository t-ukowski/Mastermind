package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GameManager;
import model.statistic.StatisticsManager;
import presenter.LoginPresenter;
import presenter.NewGamePresenter;
import presenter.RegisterPresenter;
import utils.enums.GameDifficulty;

import java.io.IOException;

/**
 * Responsible for managing dialog windows
 */
public class MainAppController {
    private GameManager gameManager;
    private StatisticsManager statisticsManager;

    private final Stage primaryStage;

    public MainAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
    }

    /**
     * Initializes the controller
     */
    public void initRootLayout() {
        try {
            statisticsManager = new StatisticsManager();
            gameManager = new GameManager(statisticsManager);

//            statisticsManager.addStatisticsItem(new StatisticsItem(
//                    1, LocalDate.of(2021, 12, 6), GameDifficulty.EASY, 6, 122));
//            statisticsManager.addStatisticsItem(new StatisticsItem(
//                    2, LocalDate.of(2021, 11, 7), GameDifficulty.MEDIUM, 8, 179));
//            statisticsManager.addRankingItem(new RankingItem(
//                    1, "Shrek", 3, 1, 234));
//            statisticsManager.addRankingItem(new RankingItem(
//                    2, "Fiona", 5, 3, 211));

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppController.class.getResource("../view/GameView.fxml"));
            BorderPane rootLayout = loader.load();

            // set initial data into controller
            GameOverviewController controller = loader.getController();
            controller.setAppController(this);

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Shows registration dialog window
     *
     * @return "true" if registration was successful
     */
    public boolean showRegisterDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppController.class.getResource("../view/RegisterView.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("MasterMind - rejestracja");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RegisterPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setGameManager(gameManager);

            dialogStage.showAndWait();

            return presenter.wasRegisterSuccessful();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Shows login dialog window
     *
     * @return "true" if login was successful
     */
    public boolean showLoginDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppController.class.getResource("../view/LoginView.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("MasterMind - logowanie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LoginPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setGameManager(gameManager);

            dialogStage.showAndWait();

            return presenter.wasLoginSuccessful();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Shows new game dialog window
     *
     * @return chosen game difficulty
     */
    public GameDifficulty showNewGameDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppController.class.getResource("../view/NewGameView.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("MasterMind - nowa gra");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            NewGamePresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setGameManager(gameManager);

            dialogStage.showAndWait();

            return presenter.newGameProperties();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Shows ranking dialog window
     */
    public void showRankingDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppController.class.getResource("../view/RankingView.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("MasterMind - ranking graczy");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RankingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGameManager(gameManager);
            controller.setStatisticsManager(statisticsManager);

            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Show's statistics dialog window
     */
    public void showStatisticsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppController.class.getResource("../view/StatisticsView.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("MasterMind - statystyki");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            StatisticsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGameManager(gameManager);
            controller.setStatisticsManager(statisticsManager);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return app's GameManager
     */
    public GameManager getGameManager() {
        return gameManager;
    }
}