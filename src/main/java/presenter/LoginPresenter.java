package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.GameManager;
import model.Player;
import utils.StringHelper;

/**
 * Represents a login dialog window
 */
public class LoginPresenter {
    private GameManager gameManager;

    private Stage dialogStage;

    private boolean success = false;

    @FXML
    private Label error;

    @FXML
    private String ERROR_EMPTY_FIELD;
    @FXML
    private String ERROR_INVALID_CHARACTERS;
    @FXML
    private String ERROR_WRONG_CREDENTIALS;
    @FXML
    private String ERROR_NONE;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    /**
     * Checks if login inputs trigger errors
     *
     * @return "true" if any of the error conditions is fulfilled
     */
    private boolean findErrors() {
        if (StringHelper.anyBlank(email, password)) {
            error.setText(ERROR_EMPTY_FIELD);
        } else if (StringHelper.anyInvalid(email, password)) {
            error.setText(ERROR_INVALID_CHARACTERS);
        } else if (!gameManager.isPasswordCorrect(email.getText(), password.getText())) {
            error.setText(ERROR_WRONG_CREDENTIALS);
        } else {
            error.setText(ERROR_NONE);
            return false;
        }
        return true;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Action to be performed when OK button is clicked
     */
    public void handleOkAction() {
        if (!findErrors()) {
            Player player = gameManager.getPlayerFromDAO(email.getText());
//            if (player == null) {
//                System.out.println("This player does not exists!");
//            }
            gameManager.loginPlayer(player);
            success = true;
            dialogStage.close();
        }
    }

    /**
     * Action to be performed when Cancel button is clicked
     */
    public void handleCancelAction() {
        dialogStage.close();
    }

    /**
     * Checks login outcome
     *
     * @return "true" if login was successful
     */
    public boolean wasLoginSuccessful() {
        return success;
    }
}
