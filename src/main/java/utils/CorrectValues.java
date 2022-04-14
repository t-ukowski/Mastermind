package utils;

/**
 * A pair of values, representing row results in game
 */
public class CorrectValues {
    public int correctPlaces;
    public int correctColors;

    public CorrectValues(int correctPlaces, int correctColors) {
        this.correctPlaces = correctPlaces;
        this.correctColors = correctColors;
    }

    @Override
    public String toString() {
        return "correct places: " + correctPlaces + "; correct colors: " + correctColors;
    }
}
