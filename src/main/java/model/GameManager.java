package model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.reactivex.rxjava3.core.Observable;
import model.guice.GameModule;
import model.persistance.GamePlayerDAO;
import model.persistance.PersistenceException;
import model.statistic.INotifiable;
import model.statistic.INotifier;
import model.statistic.MailNotifier;
import model.statistic.StatisticsManager;
import utils.CorrectValues;
import utils.Randomizer;
import utils.enums.Colors;
import utils.enums.GameDifficulty;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages app's game data
 */
public class GameManager implements INotifiable {
    private static final int CORRECT_PLACE = 10;
    private static final int CORRECT_COLOR = 4;

    private Game currentGame;
    private Player loggedPlayer;
    private StatisticsManager statisticsManager;
    private final GamePlayerDAO gamePlayerDAO;
    private Board board;
    private boolean rowReportReady = false;
    private CorrectValues correct;
    private final int boardHeight = 10;
    private final int boardWidth = 4;
    private List<INotifier> notifiersList;

    /**
     * Deprecated constructor - not compatible with m3 version
     */
    @Deprecated
    public GameManager() {
        Injector injector = Guice.createInjector(new GameModule());
        this.gamePlayerDAO = injector.getInstance(GamePlayerDAO.class);
        this.loggedPlayer = null;
    }

    /**
     * Constructor compatible with m3
     *
     * @param statisticsManager game's statistic manager
     */
    public GameManager(StatisticsManager statisticsManager) {
        Injector injector = Guice.createInjector(new GameModule());
        this.gamePlayerDAO = injector.getInstance(GamePlayerDAO.class);
        this.statisticsManager = statisticsManager;
        this.loggedPlayer = null;

        notifiersList = new ArrayList<>();
        subscribe(new MailNotifier());
    }

    public void registerPlay() {

    }

    /**
     * Starts a new game
     *
     * @param difficulty difficulty chosen for the next gameplay
     */
    public void newGame(GameDifficulty difficulty) {
        currentGame = new Game(loggedPlayer, difficulty);
        List<Colors> toGuess = new ArrayList<>();

        toGuess = Randomizer.randomizeToGuess(boardWidth, difficulty);

        List<List<Colors>> rows = new ArrayList<>();
        for (int i = 0; i < boardHeight; i++) {
            rows.add(new ArrayList<>());
        }
        for (List<Colors> row : rows) {
            for (int i = 0; i < boardWidth; i++) {
                row.add(Colors.NONE);
            }
        }

        board = new Board(toGuess, rows.get(0), rows);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Checks whether an email-password combination exists in database
     *
     * @param email    given email
     * @param password given password
     * @return {@code true} when credentials are correct
     */
    public boolean isPasswordCorrect(String email, String password) {
        return gamePlayerDAO.isPasswordCorrect(email, password);
    }

    /**
     * Checks whether an email exists in database
     *
     * @param email given email
     * @return {@code true} when account with given email exists
     * @deprecated
     */
    @Deprecated
    public boolean emailExists(String email) {
        return gamePlayerDAO.emailExists(email);
    }

    public Player getLoggedPlayer() {
        return loggedPlayer;
    }

    public Player getPlayerFromDAO(String email) {
        return gamePlayerDAO.getPlayer(email);
    }

    /**
     * Sets given player as the current one
     *
     * @param player player to be logged in
     */
    public void loginPlayer(Player player) {
        this.loggedPlayer = player;
        fetchData();
    }

    /**
     * Creates new player and loggs them in
     *
     * @param player player object representing the new player
     * @throws PersistenceException player creation in database failed
     */
    public void registerPlayer(Player player) throws PersistenceException {
        gamePlayerDAO.addPlayer(player);
        update("Welcome in MasterMind game by BagnoGameStudio!\nWe wish you luck and plenty of fun!", player);
        loginPlayer(player);
    }

    /**
     * Loggs current player out
     */
    public void logoutPlayer() {
        this.loggedPlayer = null;
    }

    public int getMove() {
        if (currentGame == null)
            return -1;
        return currentGame.getMove();
    }

    /**
     * Moves game to next row
     */
    public void nextMove() {
        correct = board.evaluatePoints();
        int correctPlaces = correct.correctPlaces;
        int correctColors = correct.correctColors;
        double modifier = 0.0;
        switch (currentGame.getDifficulty()) {
            case EASY -> modifier = 1.0;
            case MEDIUM -> modifier = 1.5;
            case HARD -> modifier = 2.0;
            default -> {
            }
        }
        currentGame.addPoints((int) (modifier * (correctPlaces * CORRECT_PLACE + correctColors * CORRECT_COLOR)));
        if (isGameWon()) {
            int movesLeft = 10 - currentGame.getMove();
            currentGame.addPoints((int) (movesLeft * modifier * 4 * CORRECT_PLACE));
            gameWonHandler();
        } else if (isMoveLast()) {
            gameLostHandler();
        } else {
            currentGame.nextMove();
            board.moveToNextRow(currentGame.getMove());
        }
    }

    /**
     * Action to be performed when a pin has been colored
     *
     * @param bigPinIndex index of the coloured pin
     * @param color       color given to the pin
     */
    public void boardSpaceColored(int bigPinIndex, Colors color) {
        board.spaceColored(bigPinIndex, color);
        if (board.isReadyToMoveOn()) {
            nextMove();
            rowReportReady = true;
        } else {
            rowReportReady = false;
        }
    }

    public boolean isRowReportReady() {
        return rowReportReady;
    }

    public CorrectValues getCorrect() {
        return correct;
    }

    public boolean isGameWon() {
        return board.isGameWon();
    }

    public boolean isMoveLast() {
        return currentGame.getMove() >= 10;
    }

    /**
     * Checks if game has already ended
     *
     * @return "true" if any of the end of game conditions is fulfilled
     */
    public boolean gameEnded() {
        return board.isGameWon() || (board.isReadyToMoveOn() && currentGame.getMove() >= 10);
    }

    /**
     * Actions to perform when game ended
     */
    private void gameEndHandler() {
        gamePlayerDAO.addGame(currentGame);
        fetchData();
    }

    /**
     * Actions to perform when the game has been won
     */
    private void gameWonHandler() {
        gameEndHandler();
        System.out.println("WYGRANA");
        update(String.format("You won and got stunning %d points!!!\nCONGRATULATIONS!!!", currentGame.getPoints()),
                loggedPlayer);
    }

    /**
     * Actions to perform when the game has been lost
     */
    private void gameLostHandler() {
        gameEndHandler();
        System.out.println("PRZEGRANA");
        update(String.format("Unfortunately you lost..\nDon't worry, you still got %d points!!!\nKEEP IT UP!!!", currentGame.getPoints()),
                loggedPlayer);
    }

    /**
     * Obtains game's target sequence
     *
     * @return the sequence to guess
     */
    public List<Colors> getToGuess() {
        return board.getToGuess();
    }

    /**
     * Updates ranking table with data fetched from the database
     */
    public void fetchRanking() {
        statisticsManager.replaceRanking(gamePlayerDAO.getRanking());
    }

    /**
     * Updates statistics table with data fetched from the database
     */
    public void fetchStatistics() {
        statisticsManager.replaceStatistics(gamePlayerDAO.getStatistics(loggedPlayer));
    }

    /**
     * Updates both ranking and statistics tables with data fetched from the database
     */
    public void fetchData() {
        fetchRanking();
        fetchStatistics();
    }

    @Override
    public void subscribe(INotifier notifier) {
        notifiersList.add(notifier);
    }

    @Override
    public void unsubscribe(INotifier notifier) {
        notifiersList.remove(notifier);
    }

    @Override
    public void update(String message, Player player) {
        for (INotifier notifier : notifiersList) {
            notifier.sendNotification(message, player);
        }
    }

    @Override
    public <T> void update(String message, Player player, List<T> list) {
        for (INotifier notifier : notifiersList) {
            notifier.sendNotification(message, player, list);
        }
    }

    @Override
    public void update(String message, Observable<Player> players) {
        for (INotifier notifier : notifiersList) {
            notifier.sendNotification(message, players);
        }
    }

    @Override
    public <T> void update(String message, Observable<Player> players, List<T> list) {
        for (INotifier notifier : notifiersList) {
            notifier.sendNotification(message, players, list);
        }
    }

}

