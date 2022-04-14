package utils.enums;

/**
 * Represents game difficulty setting
 */
public enum GameDifficulty {
    EASY,
    MEDIUM,
    HARD,
    NONE;

    public static GameDifficulty byIndex(int index) {
        return GameDifficulty.values()[index];
    }

    @Override
    public String toString() {
        return switch (this) {
            case EASY -> "Easy";
            case MEDIUM -> "Medium";
            case HARD -> "Hard";
            case NONE -> "-";
        };
    }
}
