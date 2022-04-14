package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.GameManager;
import model.Player;
import model.persistance.PersistenceException;
import utils.StringHelper;

import java.util.Objects;

/**
 * Represents a register dialog window
 */
public class RegisterPresenter {
    private GameManager gameManager;

    private Stage dialogStage;

    private boolean success = false;

    @FXML
    private Label error;

    @FXML
    private String ERROR_NOT_MATCHING;
    @FXML
    private String ERROR_USER_EXISTS;
    @FXML
    private String ERROR_EMPTY_FIELD;
    @FXML
    private String ERROR_INVALID_CHARACTERS;
    @FXML
    private String ERROR_INVALID_EMAIL;
    @FXML
    private String ERROR_SHORT_PASSWORD;
    @FXML
    private String ERROR_NONE;

    @FXML
    private TextField nick;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField rPassword;

    /**
     * Checks if registration inputs trigger errors
     *
     * @return "true" if any of the error conditions is fulfilled
     */
    private boolean findErrors() {
        if (StringHelper.anyBlank(nick, email, password, rPassword)) {
            error.setText(ERROR_EMPTY_FIELD);
        } else if (StringHelper.anyInvalid(nick, email, password, rPassword)) {
            error.setText(ERROR_INVALID_CHARACTERS);
        } else if (!StringHelper.isEmailValid(email.getText())) {
            error.setText(ERROR_INVALID_EMAIL);
        } else if (password.getText().length() < 8) {
            error.setText(ERROR_SHORT_PASSWORD);
        } else if (!Objects.equals(password.getText(), rPassword.getText())) {
            error.setText(ERROR_NOT_MATCHING);
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
            Player player = new Player(nick.getText(), email.getText(), password.getText());
            try {
                gameManager.registerPlayer(player);
                success = true;
                dialogStage.close();
            } catch (PersistenceException e) {
                error.setText(ERROR_USER_EXISTS);
            }
        }
    }

    /**
     * Action to be performed when Cancel button is clicked
     */
    public void handleCancelAction() {
        dialogStage.close();
    }

    /**
     * Checks registration outcome
     *
     * @return "true" if registration was successful
     */
    public boolean wasRegisterSuccessful() {
        return success;
    }
}
