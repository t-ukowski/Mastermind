package utils;

import javafx.scene.paint.Color;

/**
 * Class representing color palette available in game
 *
 * @deprecated
 */
@Deprecated
public class ColorPalette {
    /**
     * Saturation factor for faint() method
     */
    private final static double FAINT_SATURATION_FACTOR = 0.2;

    /**
     * Brightness factor for darkened() method
     */
    private final static double STRONG_BRIGHTNESS_FACTOR = 0.7;

    /**
     * Colors available in the game
     */
    private final static Color[] colors = {
            Color.RED,
            Color.LIMEGREEN,
            Color.ORANGE,
            Color.AQUA,
            Color.YELLOW,
            Color.MEDIUMPURPLE
    };

    /**
     * Get color with given index
     *
     * @param index color's index number
     * @return color under given index number
     */
    public static Color getColor(int index) {
        return colors[index];
    }

    /**
     * Creates a new color with saturation set to {@value #FAINT_SATURATION_FACTOR} of the given color's saturation
     *
     * @param index index number of chosen color
     * @return desaturated version of chosen color
     */
    public static Color faint(int index) {
        return colors[index].deriveColor(0, FAINT_SATURATION_FACTOR, 1.0, 1.0);
    }

    /**
     * Creates a new color with brightness set to {@value #STRONG_BRIGHTNESS_FACTOR} of the given color's brightness
     *
     * @param index index number of the chosen color
     * @return darkened version of chosen color
     */
    public static Color darkened(int index) {
        return colors[index].deriveColor(0, 1, STRONG_BRIGHTNESS_FACTOR, 1.0);
    }
}
