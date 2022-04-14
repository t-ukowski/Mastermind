package model;

import utils.CorrectValues;
import utils.enums.Colors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model side of the Board
 */
public class Board {
    private List<Colors> toGuess;
    private List<Colors> currentRow;
    private final List<List<Colors>> rows;
    private boolean readyToMoveOn;
    private boolean gameWon = false;
    private int correctPlaces;
    private int correctColors;

    public Board(List<Colors> toGuess, List<Colors> currentRow, List<List<Colors>> rows) {
        this.toGuess = toGuess;
        this.currentRow = currentRow;
        this.rows = rows;
        this.readyToMoveOn = false;
        this.correctPlaces = 0;
        this.correctColors = 0;
    }

    /**
     * Checks whether game has already been won
     *
     * @return {@code true} if yes
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Evaluates the results of the last finished row
     *
     * @return {@link CorrectValues} representing the results
     */
    public CorrectValues evaluatePoints() {
        List<Colors> toGuessCopy = new ArrayList<>(toGuess);
        List<Colors> currentRowCopy = new ArrayList<>(currentRow);
        for (int i = 0; i < currentRowCopy.size(); i++) {
            if (currentRowCopy.get(i) == toGuessCopy.get(i)) {
                correctPlaces++;
                toGuessCopy.set(i, Colors.NONE);
                currentRowCopy.set(i, Colors.NONE);
            }
        }
        for (int i = 0; i < currentRowCopy.size(); i++) {
            for (int j = 0; j < toGuessCopy.size(); j++) {
                if (currentRowCopy.get(i) == toGuessCopy.get(j) && currentRowCopy.get(i) != Colors.NONE) {
                    correctColors++;
                    toGuessCopy.set(j, Colors.NONE);
                    currentRowCopy.set(i, Colors.NONE);
                    break;
                }
            }
        }
        if (correctPlaces == 4) {
            gameWon = true;
        }
        return new CorrectValues(correctPlaces, correctColors);
    }

    /**
     * Switches to the row having given number (according to {@code Game.moveProperty})
     *
     * @param moves number of the move (1st move has the number of 1, not 0)
     * @see Game
     */
    public void moveToNextRow(int moves) {
        readyToMoveOn = false;
        currentRow = rows.get(moves - 1);
        correctPlaces = 0;
        correctColors = 0;
    }

    /**
     * Sets the code player are supposed to guess
     *
     * @param toGuess list of new code colors
     */
    public void setToGuess(List<Colors> toGuess) {
        this.toGuess = toGuess;
    }

    /**
     * Gets the code player is supposed to guess
     *
     * @return list of code colors
     */
    public List<Colors> getToGuess() {
        return toGuess;
    }

    /**
     * Sets the color of a chosen pin. If all pins are colored, signals readiness to proceed to the next row
     *
     * @param i index of the pin in the current row
     * @param c chosen color
     */
    public void spaceColored(int i, Colors c) {
        currentRow.set(i, c);
        if (!currentRow.contains(Colors.NONE))
            readyToMoveOn = true;
    }

    /**
     * Checks whether moving to the next row is possible
     *
     * @return {@code true} if game can proceed
     */
    public boolean isReadyToMoveOn() {
        return readyToMoveOn;
    }
}
