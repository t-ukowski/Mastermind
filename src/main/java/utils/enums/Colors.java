package utils.enums;

import javafx.scene.paint.Color;

/**
 * Represents possible colors of the pins in game
 */
public enum Colors {
    RED,
    LIMEGREEN,
    ORANGE,
    AQUA,
    YELLOW,
    MEDIUMPURPLE,
    NONE;

    /**
     * Saturation factor used by the {@code Colors.faint()} method
     */
    private final static double FAINT_SATURATION_FACTOR = 0.2;

    /**
     * Brightness factor used by the {@code Colors.dark()} method
     */
    private final static double STRONG_BRIGHTNESS_FACTOR = 0.6;

    /**
     * Gets the enum value of the given index
     *
     * @param index the index from 0 to 6
     * @return the enum value
     */
    public static Colors byIndex(int index) {
        return Colors.values()[index];
    }

    /**
     * Translates Colors enum to a respective {@link javafx.scene.paint.Color} object
     *
     * @return the {@code Color}
     */
    public Color getColor() {
        return switch (this) {
            case RED -> Color.RED;
            case LIMEGREEN -> Color.LIMEGREEN;
            case ORANGE -> Color.ORANGE;
            case AQUA -> Color.AQUA;
            case YELLOW -> Color.YELLOW;
            case MEDIUMPURPLE -> Color.MEDIUMPURPLE;
            case NONE -> Color.GRAY;
        };
    }

    /**
     * Makes a particular color faint (multiplies saturation by {@value FAINT_SATURATION_FACTOR})
     *
     * @return {@link javafx.scene.paint.Color} object representing faint version of respective color
     */
    public Color faint() {
        return this.getColor().deriveColor(0, FAINT_SATURATION_FACTOR, 1.0, 1.0);
    }

    /**
     * Makes a particular color darker (multiplies brightness by {@value STRONG_BRIGHTNESS_FACTOR})
     *
     * @return {@link javafx.scene.paint.Color} object representing darker version of respective color
     */
    public Color dark() {
        return this.getColor().deriveColor(0, 1, STRONG_BRIGHTNESS_FACTOR, 1.0);
    }
}
