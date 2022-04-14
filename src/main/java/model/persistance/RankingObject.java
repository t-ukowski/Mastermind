package model.persistance;

public class RankingObject {
    private String name;
    private String player_id;
    private int gamesPlayed;
    private int gamesWon;
    private int highestScore;

    public RankingObject(String player_id, int gamesPlayed, int highestScore) {
        this.player_id = player_id;
        this.gamesPlayed = gamesPlayed;
        this.highestScore = highestScore;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
}
