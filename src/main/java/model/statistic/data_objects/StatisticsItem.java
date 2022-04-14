package model.statistic.data_objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import utils.enums.GameDifficulty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a statistics table record
 */
public class StatisticsItem {

    private final IntegerProperty nr;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<GameDifficulty> difficulty;
    private final IntegerProperty moves;
    private final IntegerProperty score;

    public StatisticsItem(int nr, LocalDate date, GameDifficulty difficulty, int moves, int score) {
        this.nr = new SimpleIntegerProperty(nr);
        this.date = new SimpleObjectProperty<>(date);
        this.difficulty = new SimpleObjectProperty<>(difficulty);
        this.moves = new SimpleIntegerProperty(moves);
        this.score = new SimpleIntegerProperty(score);
    }

    public StatisticsItem(int nr, LocalDateTime date, GameDifficulty difficulty, int moves, int score) {
        this(nr, date.toLocalDate(), difficulty, moves, score);
    }

    public int getNr() {
        return nr.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public GameDifficulty getDifficulty() {
        return difficulty.get();
    }

    public int getMoves() {
        return moves.get();
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty getNrProperty() {
        return nr;
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public ObjectProperty<GameDifficulty> getDifficultyProperty() {
        return difficulty;
    }

    public IntegerProperty getMovesProperty() {
        return moves;
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("%-35s %-10s %-25d %-25d\n",
                getDate().toString(), getDifficulty().toString(), getMoves(), getScore());
    }
}
