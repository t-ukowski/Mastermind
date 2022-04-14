package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.GameManager;
import model.statistic.StatisticsManager;
import model.statistic.data_objects.StatisticsItem;
import utils.enums.GameDifficulty;

import java.time.LocalDate;

/**
 * Responsible for statistics dialog window
 */
public class StatisticsController {

    private GameManager gameManager;
    private StatisticsManager statisticsManager;
    private Stage dialogStage;
    @FXML
    private Label nickLabel;
    @FXML
    private TableView<StatisticsItem> statisticsTable;
    @FXML
    private TableColumn<StatisticsItem, Number> nrColumn;
    @FXML
    private TableColumn<StatisticsItem, LocalDate> dateColumn;
    @FXML
    private TableColumn<StatisticsItem, GameDifficulty> difficultyColumn;
    @FXML
    private TableColumn<StatisticsItem, Number> movesColumn;
    @FXML
    private TableColumn<StatisticsItem, Number> scoreColumn;

    /**
     * Initializes the controller
     */
    @FXML
    private void initialize() {
        statisticsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nrColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNrProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        difficultyColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDifficultyProperty());
        //movesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getMovesProperty());
        scoreColumn.setCellValueFactory(dataValue -> dataValue.getValue().getScoreProperty());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
        nickLabel.textProperty().bind(gameManager.getLoggedPlayer().getNickProperty());
    }

    public void setStatisticsManager(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
        statisticsTable.setItems(statisticsManager.getStatistics());
    }
}
