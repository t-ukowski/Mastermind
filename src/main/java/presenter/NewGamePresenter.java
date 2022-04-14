package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GameManager;
import utils.enums.GameDifficulty;

/**
 * Represents new game dialog window
 */
public class NewGamePresenter {
    @FXML
    private Label description;
    @FXML
    private RadioButton easyButton;
    @FXML
    private RadioButton mediumButton;
    @FXML
    private RadioButton hardButton;
    @FXML
    private ToggleGroup group;
    @FXML
    private String LABEL_01;
    @FXML
    private String LABEL_02;
    @FXML
    private String LABEL_03;

    private GameManager gameManager;
    private Stage dialogStage;
    private GameDifficulty difficulty;
    private boolean success;

    @FXML
    private void initialize() {
        mediumButton.setSelected(true);
        difficulty = GameDifficulty.MEDIUM;
        success = false;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Action to be performed on clicking OK button
     */
    public void handleNewGameAction() {
        gameManager.newGame(difficulty);
        success = true;
        dialogStage.close();
    }

    /**
     * Action to be performed on clicking Cancel button
     */
    public void handleCancelAction() {
        dialogStage.close();
    }

    /**
     * Action to be performed on selecting the Easy mode
     */
    public void handleEasyAction(MouseEvent mouseEvent) {
        description.setText(LABEL_01);
        difficulty = GameDifficulty.EASY;
    }

    /**
     * Action to be performed on selecting the Medium mode
     */
    public void handleMediumAction(MouseEvent mouseEvent) {
        description.setText(LABEL_02);
        difficulty = GameDifficulty.MEDIUM;
    }

    /**
     * Action to be performed on selecting the Hard mode
     */
    public void handleHardAction(MouseEvent mouseEvent) {
        description.setText(LABEL_03);
        difficulty = GameDifficulty.HARD;
    }

    /**
     * Returns new game dialog results
     *
     * @return chosen difficulty on success, {@link GameDifficulty}.NONE on cancellation or fail
     */
    public GameDifficulty newGameProperties() {
        if (success)
            return difficulty;
        return GameDifficulty.NONE;
    }
}
