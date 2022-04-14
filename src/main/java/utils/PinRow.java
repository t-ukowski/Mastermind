package utils;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utils.enums.Colors;

/**
 * Visual representation of a row of pins on the board
 */
public class PinRow {
    private final static Color CORRECT_PLACE = Color.BLACK;
    private final static Color CORRECT_COLOR = Color.WHITE;
    private final static Color HIGHLIGHT = Color.web("#f2e461");
    private final static Color LOWLIGHT = Color.web("#ffbe63");
    private final static Insets insets = new Insets(5, 10, 5, 10);

    public final static int bigPinCount = 4;

    private final Circle[] bigPins = new Circle[bigPinCount];
    private final Circle[] resultPins = new Circle[bigPinCount];

    private final HBox rowBox;

    /**
     * Creates a PinRow instance from a {@link HBox} with following structure:
     * <pre>{@code
     * <HBox>
     *     <Circle/>
     *     ...
     *     <Circle/>
     *     <GridPane>
     *         <Circle/>
     *         ...
     *         <Circle/>
     *     </GridPane>
     * </HBox>
     * }</pre> (every Cricle tag {@value #bigPinCount} times)
     *
     * @param hBox {@link HBox} with correct structure
     */
    public PinRow(HBox hBox) {
        rowBox = hBox;
        ObservableList<Node> childrenNodes = hBox.getChildren();
        for (int i = 0; i < bigPinCount; i++) {
            bigPins[i] = (Circle) childrenNodes.get(i);
        }
        GridPane resultPane = (GridPane) childrenNodes.get(4);
        ObservableList<Node> grandchildrenNodes = resultPane.getChildren();
        for (int i = 0; i < bigPinCount; i++) {
            resultPins[i] = (Circle) grandchildrenNodes.get(i);
        }
    }

    /**
     * @param index index number of the big pin (index from 0 to {@value bigPinCount})
     * @return big pin having given index
     */
    public Circle getBigPin(int index) {
        return bigPins[index];
    }

    /**
     * @param index index number of the result pin (index from 0 to {@value bigPinCount})
     * @return result pin having given index
     */
    public Circle getResultPin(int index) {
        return resultPins[index];
    }

    /**
     * Changes colors of the result pins depending on the results in this row
     *
     * @param correct the row result
     */
    public void paintRowReport(CorrectValues correct) {
        int correctPlaces = correct.correctPlaces;
        int correctColors = correct.correctColors;
        for (int i = 0; i < correctPlaces + correctColors; i++) {
            if (i >= bigPinCount) break;
            else {
                if (i < correctPlaces) {
                    resultPins[i].setFill(CORRECT_PLACE);
                } else {
                    resultPins[i].setFill(CORRECT_COLOR);
                }
            }
        }
    }

    /**
     * Visually highlights the row's {@link HBox}
     */
    public void highlightRow() {
        this.rowBox.setBackground(new Background(new BackgroundFill(HIGHLIGHT, null, insets)));
    }

    /**
     * Visually removes the highlight of the row's {@link HBox}
     */
    public void lowlightRow() {
        this.rowBox.setBackground(new Background(new BackgroundFill(LOWLIGHT, null, insets)));
    }

    /**
     * Resets the row to the default state - not highlighted and uncolored pins (both big and result ones)
     */
    public void reset() {
        this.lowlightRow();
        for (Circle pin : bigPins) {
            pin.setFill(Colors.NONE.getColor());
        }
        for (Circle pin : resultPins) {
            pin.setFill(Colors.NONE.getColor());
        }
    }
}

