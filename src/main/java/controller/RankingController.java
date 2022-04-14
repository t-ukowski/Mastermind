package controller;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.GameManager;
import model.statistic.StatisticsManager;
import model.statistic.data_objects.RankingItem;


/**
 * Responsible for ranking dialog window
 */
public class RankingController {

    private GameManager gameManager;
    private StatisticsManager statisticsManager;
    private Stage dialogStage;
    @FXML
    private TableView<RankingItem> rankingTable;
    @FXML
    private TableColumn<RankingItem, Number> positionColumn;
    @FXML
    private TableColumn<RankingItem, String> nickColumn;
    @FXML
    private TableColumn<RankingItem, Number> gamesPlayedColumn;
    @FXML
    private TableColumn<RankingItem, Number> gamesWonColumn;
    @FXML
    private TableColumn<RankingItem, Number> bestScoreColumn;

    /**
     * Initializes the controller
     */
    @FXML
    private void initialize() {
        rankingTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        positionColumn.setCellValueFactory(dataValue -> dataValue.getValue().getPositionProperty());
        nickColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNickProperty());
        gamesPlayedColumn.setCellValueFactory(dataValue -> dataValue.getValue().getGamesPlayedProperty());
        //gamesWonColumn.setCellValueFactory(dataValue -> dataValue.getValue().getGamesWonProperty());
        bestScoreColumn.setCellValueFactory(dataValue -> dataValue.getValue().getBestScoreProperty());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setStatisticsManager(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
        rankingTable.setItems(statisticsManager.getRanking());
    }
}
