package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utils.CorrectValues;
import utils.PinRow;
import utils.enums.Colors;
import utils.enums.GameDifficulty;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for displaying the main game window
 */
public class GameOverviewController {
    private final static double DEFAULT_STROKE = 2.0;
    private final static double BOLD_STROKE = 7.0;

    @FXML
    private String player_LABEL1;
    @FXML
    private String player_LABEL2;
    @FXML
    private Label player_label1;
    @FXML
    private Label player_label2;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button rankingButton;
    @FXML
    private Button statsButton;
    @FXML
    private String login;
    @FXML
    private String logout;
    @FXML
    private Label difficulty_label;
    @FXML
    private String D_NONE;
    @FXML
    private String D_EASY;
    @FXML
    private String D_MEDIUM;
    @FXML
    private String D_HARD;
    @FXML
    private Label move;
    @FXML
    private Label points;

    private MainAppController appController;

    private int selectedPaletteElem = 0;

    @FXML
    private VBox pin_area;

    @FXML
    private VBox palette_box;

    @FXML
    private HBox code_box;

    @FXML
    private Button newPlayerButton;

    private PinRow[] pinRows;

    private Circle[] codePins;

    private final List<Circle> palette = new ArrayList<>();

    /**
     * Paints given circle
     *
     * @param thing circle to be painted
     * @param color new circle's color
     */
    @FXML
    public void paint(Circle thing, Color color) {
        thing.setFill(color);
    }

    /**
     * Paints given circle
     *
     * @param thing circle to be painted
     * @param index color's index as in {@link Colors}
     */
    @FXML
    public void paint(Circle thing, int index) {
        paint(thing, Colors.byIndex(index).getColor());
    }

    /**
     * Paints given circle with the color selected in game
     *
     * @param thing circle to be painted
     */
    @FXML
    public void paint(Circle thing) {
        paint(thing, selectedPaletteElem);
    }

    /**
     * Paints given circle
     *
     * @param thing circle to be painted
     * @param color new circle's color
     */
    @FXML
    public void paint(Circle thing, Colors color) {
        paint(thing, color.getColor());
    }

    /**
     * Initializes the controller
     */
    @FXML
    public void initialize() {
        ObservableList<Node> pin_areaChildren = pin_area.getChildren();
        pinRows = new PinRow[pin_areaChildren.size()];
        for (int i = 0; i < pin_areaChildren.size(); i++) {
            pinRows[i] = new PinRow((HBox) pin_areaChildren.get(i));
        }

        ObservableList<Node> code_boxChildren = code_box.getChildren();
        codePins = new Circle[code_boxChildren.size()];
        for (int i = 0; i < code_boxChildren.size(); i++) {
            codePins[i] = (Circle) code_boxChildren.get(i);
        }

        ObservableList<Node> palette_boxChildren = palette_box.getChildren();
        for (Node palette_boxChild : palette_boxChildren) {
            HBox hBox = (HBox) palette_boxChild;
            ObservableList<Node> hBoxChildren = hBox.getChildren();
            for (Node hBoxChild : hBoxChildren) {
                palette.add((Circle) hBoxChild);
            }
        }

        for (int m = 0; m < pinRows.length; m++) {
            for (int i = 0; i < PinRow.bigPinCount; i++) {
                int finalM = m;
                int finalI = i;
                pinRows[m].getBigPin(i).setOnMouseClicked(event -> bigPinClickHandler(finalM, finalI));
            }
        }

        for (int i = 0; i < palette.size(); i++) {
            int finalI = i;
            palette.get(i).setOnMouseClicked(event -> paletteClickHandler(palette, finalI));
            palette.get(i).setOnMouseEntered(event -> paletteHoverHandler(palette, finalI));
            palette.get(i).setOnMouseExited(event -> paletteUnhoverHandler(palette, finalI));
        }

        paletteRepaintAll(palette);
        paletteRestrokeAll(palette);
    }

    /**
     * Sets palette element's border width depending on whether it is selected or not
     *
     * @param paletteElem palette element to restroke
     * @param index       palette element's index
     */
    @FXML
    public void paletteRestroke(Circle paletteElem, int index) {
        if (index == selectedPaletteElem) {
            paletteElem.setStrokeWidth(BOLD_STROKE);
        } else {
            paletteElem.setStrokeWidth(DEFAULT_STROKE);
        }
    }

    /**
     * Paints palette element with its default color
     *
     * @param paletteElem palette element to repaint
     * @param index       palette element's index
     */
    @FXML
    public void paletteRepaint(Circle paletteElem, int index) {
        paint(paletteElem, index);
    }

    /**
     * Restrokes all palette elements
     *
     * @param palette List containing palette elements
     */
    @FXML
    public void paletteRestrokeAll(List<Circle> palette) {
        for (int i = 0; i < palette.size(); i++) {
            paletteRestroke(palette.get(i), i);
        }
    }

    /**
     * Repaints all palette elements
     *
     * @param palette List containing palette elements
     */
    @FXML
    public void paletteRepaintAll(List<Circle> palette) {
        for (int i = 0; i < palette.size(); i++) {
            paletteRepaint(palette.get(i), i);
        }
    }

    /**
     * Handles clicking on a palette element
     *
     * @param palette List containing palette elements
     * @param index   palette element's index
     */
    @FXML
    public void paletteClickHandler(List<Circle> palette, int index) {
        selectedPaletteElem = index;
        paletteRestrokeAll(palette);
    }

    /**
     * Handles hovering over a palette element
     *
     * @param palette List containing palette elements
     * @param index   palette element's index
     */
    @FXML
    public void paletteHoverHandler(List<Circle> palette, int index) {
        paint(palette.get(index), Colors.byIndex(index).dark());
    }

    /**
     * Handles hovering off a palette element
     *
     * @param palette List containing palette elements
     * @param index   palette element's index
     */
    @FXML
    public void paletteUnhoverHandler(List<Circle> palette, int index) {
        paletteRepaint(palette.get(index), index);
    }

    /**
     * Handles clicking on a big pin from game area
     *
     * @param pinRowIndex index number of the MoveArea
     * @param bigPinIndex index number of the big pin within the MoveArea
     */
    @FXML
    public void bigPinClickHandler(int pinRowIndex, int bigPinIndex) {
        if (pinRowIndex == (10 - appController.getGameManager().getMove()) && !appController.getGameManager().gameEnded()) {
            paint(pinRows[pinRowIndex].getBigPin(bigPinIndex));
            appController.getGameManager().boardSpaceColored(bigPinIndex, Colors.byIndex(selectedPaletteElem));
            if (appController.getGameManager().isRowReportReady()) {
                CorrectValues rowReport = appController.getGameManager().getCorrect();
                pinRows[pinRowIndex].paintRowReport(rowReport);
                pinRows[pinRowIndex].lowlightRow();
                if (pinRowIndex > 0 && !appController.getGameManager().isGameWon()) {
                    pinRows[pinRowIndex - 1].highlightRow();
                } else {
                    showCode(appController.getGameManager().getToGuess());
                }
            }
        }
    }

    /**
     * Handles interactions with New Game button
     *
     * @param actionEvent event that triggered the handler
     */
    public void handleNewGameAction(ActionEvent actionEvent) {
        GameDifficulty result = appController.showNewGameDialog();
        if (result != GameDifficulty.NONE) {
            hideCode();
            resetAllPins();
            move.textProperty().bind(appController.getGameManager().getCurrentGame().getMoveProperty().asString());
            points.textProperty().bind(appController.getGameManager().getCurrentGame().getPointsProperty().asString());
            pinRows[9].highlightRow();
        }
        switch (result) {
            case EASY -> {
                difficulty_label.setText(D_EASY);
                palette.get(4).setDisable(true);
                paint(palette.get(4), Colors.byIndex(4).getColor().darker());
                palette.get(5).setDisable(true);
                paint(palette.get(5), Colors.byIndex(5).getColor().darker());
                paletteClickHandler(palette, 0);
            }
            case MEDIUM -> {
                difficulty_label.setText(D_MEDIUM);
                palette.get(4).setDisable(false);
                paint(palette.get(4), Colors.byIndex(4).getColor());
                palette.get(5).setDisable(true);
                paint(palette.get(5), Colors.byIndex(5).getColor().darker());
                paletteClickHandler(palette, 0);
            }
            case HARD -> {
                difficulty_label.setText(D_HARD);
                palette.get(4).setDisable(false);
                paint(palette.get(4), Colors.byIndex(4).getColor());
                palette.get(5).setDisable(false);
                paint(palette.get(5), Colors.byIndex(5).getColor());
                paletteClickHandler(palette, 0);
            }
            default -> {

            }
        }
    }

    /**
     * Resets all pins on the board
     *
     * @see PinRow
     */
    private void resetAllPins() {
        for (PinRow pinRow : pinRows) {
            pinRow.reset();
        }
        hideCode();
    }

    /**
     * Handles interactions with Ranking button
     *
     * @param actionEvent event that triggered the handler
     */
    public void handleRankingAction(ActionEvent actionEvent) {
        appController.showRankingDialog();
    }

    /**
     * Handles interactions with Stats button
     *
     * @param actionEvent event that triggered the handler
     */
    public void handleStatsAction(ActionEvent actionEvent) {
        appController.showStatisticsDialog();
    }

    public void setAppController(MainAppController appController) {
        this.appController = appController;
    }

    /**
     * Handles interactions with Log In button
     *
     * @param actionEvent event that triggered the handler
     */
    public void handleLoginAction(ActionEvent actionEvent) {
        if (appController.showLoginDialog()) {
            playerLoggedIn();
        }
    }

    /**
     * Handles interactions with Log Out button
     *
     * @param actionEvent event that triggered the handler
     */
    public void handleLogoutAction(ActionEvent actionEvent) {
        appController.getGameManager().logoutPlayer();
        playerLoggedOut();
    }

    /**
     * Handles interactions with Register button
     *
     * @param actionEvent event that triggered the handler
     */
    public void handleRegisterAction(ActionEvent actionEvent) {
        if (appController.showRegisterDialog()) {
            playerLoggedIn();
        }
    }

    /**
     * Triggers actions necessary when player has logged in
     */
    private void playerLoggedIn() {
        registerButton.setDisable(true);
        newGameButton.setDisable(false);
        rankingButton.setDisable(false);
        statsButton.setDisable(false);
        loginButton.setText(logout);
        loginButton.setOnAction(this::handleLogoutAction);
        player_label1.setText(player_LABEL2);
        player_label2.textProperty().bind(appController.getGameManager().getLoggedPlayer().getNickProperty());
    }

    /**
     * Triggers actions necessary when player has logged out
     */
    private void playerLoggedOut() {
        registerButton.setDisable(false);
        newGameButton.setDisable(true);
        rankingButton.setDisable(true);
        statsButton.setDisable(true);
        loginButton.setText(login);
        loginButton.setOnAction(this::handleLoginAction);
        player_label1.setText(player_LABEL1);
        player_label2.textProperty().unbind();
        player_label2.setText("");

        difficulty_label.setText(D_NONE);
        move.textProperty().unbind();
        move.setText("-");
        points.textProperty().unbind();
        points.setText("-");

        resetAllPins();
    }

    /**
     * Displays a sequence of Colors in the uppermost pin row of the game window
     *
     * @param code code to be displayed
     */
    public void showCode(List<Colors> code) {
        for (int i = 0; i < codePins.length; i++) {
            codePins[i].setFill(code.get(i).getColor());
        }
    }

    /**
     * Resets the uppermost row to its default, uncolored state
     */
    public void hideCode() {
        for (Circle codePin : codePins) {
            codePin.setFill(Colors.NONE.getColor());
        }
    }
}
