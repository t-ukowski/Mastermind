package model.persistance;

import utils.enums.GameDifficulty;

public class StatisticsObject {
    private String date;
    private int points;
    private int numberOfMoves;
    private GameDifficulty gameDifficulty;

    public StatisticsObject(String date, int points, GameDifficulty gameDifficulty) {
        this.date = date;
        this.points = points;
        this.gameDifficulty = gameDifficulty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }
}
