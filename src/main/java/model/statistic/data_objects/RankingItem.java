package model.statistic.data_objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Comparator;

/**
 * Represents a ranking table record
 */
public class RankingItem implements Comparator<RankingItem>, Comparable<RankingItem> {

    private final IntegerProperty position;
    private final StringProperty nick;
    private final IntegerProperty gamesPlayed;
    private final IntegerProperty gamesWon;
    private final IntegerProperty bestScore;

    public RankingItem(int position, String nick, int gamesPlayed, int gamesWon, int bestScore) {
        this.position = new SimpleIntegerProperty(position);
        this.nick = new SimpleStringProperty(nick);
        this.gamesPlayed = new SimpleIntegerProperty(gamesPlayed);
        this.gamesWon = new SimpleIntegerProperty(gamesWon);
        this.bestScore = new SimpleIntegerProperty(bestScore);
    }

    public int getPosition() {
        return position.get();
    }

    public String getNick() {
        return nick.get();
    }

    public int getGamesPlayed() {
        return gamesPlayed.get();
    }

    public int getGamesWon() {
        return gamesWon.get();
    }

    public int getBestScore() {
        return bestScore.get();
    }

    public IntegerProperty getPositionProperty() {
        return position;
    }

    public StringProperty getNickProperty() {
        return nick;
    }

    public IntegerProperty getGamesPlayedProperty() {
        return gamesPlayed;
    }

    public IntegerProperty getGamesWonProperty() {
        return gamesWon;
    }

    public IntegerProperty getBestScoreProperty() {
        return bestScore;
    }

    public void setPosition(int position) {
        this.position.set(position);
    }

    @Override
    public int compareTo(RankingItem o) {
        return compare(this, o);
    }

    @Override
    public int compare(RankingItem o1, RankingItem o2) {
        return o2.getBestScore() - o1.getBestScore();
    }

    @Override
    public String toString() {
        return String.format("%-70s %-10d %-10d %-10d\n",
                getNick(), getGamesPlayed(), getGamesWon(), getBestScore());
    }
}
