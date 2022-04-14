package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import utils.enums.GameDifficulty;

import java.time.LocalDateTime;

/**
 * Represents a single gameplay
 */
public class Game implements IGameObject {
    private String id;
    private int points;
    private final IntegerProperty pointsProperty;
    private final IntegerProperty moveProperty;
    private Player player;
    //private Time time;
    private LocalDateTime timestamp;
    private GameDifficulty difficulty;

    // New

    public Game(Player player, GameDifficulty difficulty) {
        this(0, player, difficulty);
    }

    public Game(Integer points, Player player, GameDifficulty difficulty) {
        this(points, player, difficulty, null);
    }

    public Game(Integer points, Player player, GameDifficulty difficulty, LocalDateTime timestamp) {
        this.pointsProperty = new SimpleIntegerProperty(points);
        this.moveProperty = new SimpleIntegerProperty(1);
        this.player = player;
        //this.time = time;
        this.timestamp = timestamp;
        this.difficulty = difficulty;
    }

    public int getPoints() {
        return pointsProperty.get();
    }

    public Player getPlayer() {
        return player;
    }

//    public Time getTime() {
//        return time;
//    }

    public LocalDateTime getTimestamp() {
        if (timestamp == null) {
//            Date date = new Date();
            timestamp = LocalDateTime.now();
        }
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setPoints(int points) {
        this.pointsProperty.setValue(points);
    }

    public void addPoints(int points) {
        this.pointsProperty.setValue(this.pointsProperty.get() + points);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

//    public void setTime(Time time) {
//        this.time = time;
//    }

    public int getMove() {
        return moveProperty.get();
    }

    public void setMove(int move) {
        moveProperty.set(move);
    }

    public IntegerProperty getPointsProperty() {
        return pointsProperty;
    }

    public IntegerProperty getMoveProperty() {
        return moveProperty;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Increases move number
     */
    public void nextMove() {
        moveProperty.set(moveProperty.get() + 1);
    }

    public void setId(String id) {
        this.id = id;
    }
}
