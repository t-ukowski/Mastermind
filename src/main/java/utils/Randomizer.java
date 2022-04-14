package utils;

import utils.enums.Colors;
import utils.enums.GameDifficulty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides tools for obtaining random data
 */
public class Randomizer {
    /**
     * Generates random sequence of {@link Colors}
     *
     * @param boardWidth number of pins to randomize
     * @param difficulty game difficulty (affects the number of possible colors)
     * @return list of generated values
     */
    public static List<Colors> randomizeToGuess(int boardWidth, GameDifficulty difficulty) {
        List<Colors> toGuess = new ArrayList<>();
        int rand = 0;
        Random random = new Random();
        for (int i = 0; i < boardWidth; i++) {
            switch (difficulty) {
                case EASY -> rand = random.nextInt(4);
                case MEDIUM -> rand = random.nextInt(5);
                case HARD -> rand = random.nextInt(6);
                default -> {

                }
            }
            toGuess.add(Colors.byIndex(rand));
        }
        return toGuess;
    }
}
