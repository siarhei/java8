package ch03;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Convolution filters such as blur or edge detection compute a pixel from
 * neighboring pixels. To blur an image, replace each color value by the average
 * of itself and its eight neighbors. For edge detection, replace each color value
 * c with 4c – n – e – s – w, where the other colors are those of the pixel to the
 * north, east, south, and west. Note that these cannot be implemented lazily,
 * using the approach of Section 3.6, “Laziness,” on page 56, since they require
 * the image from the previous stage (or at least the neighboring pixels) to have
 * been computed. Enhance the lazy image processing to deal with these operations.
 * Force computation of the previous stage when one of these operators
 * is evaluated.
 *
 * @author sshchahratsou
 */
public class Ex13 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image blur = blur(image);

        primaryStage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(blur))));
        primaryStage.show();
    }

    private Image blur(Image in) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        System.out.printf("width: %d; height: %d", width, height);
        PixelReader inPixelReader = in.getPixelReader();
        WritableImage out = new WritableImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, blur(inPixelReader.getColor(x, y),
                        findNeighbourColors(width, height, x, y, inPixelReader)));
            }
        }

        return out;
    }

    private Color[] findNeighbourColors(int width, int height, int x, int y, PixelReader inPixelReader) {
        Color[] neighbourColors = new Color[4];
        if (y - 1 >= 0) { //North
            neighbourColors[0] = inPixelReader.getColor(x, y - 1);
        }
        if (x + 1 < width) {//East
            neighbourColors[1] = inPixelReader.getColor(x + 1, y);
        }
        if (y + 1 < height) {//South
            neighbourColors[2] = inPixelReader.getColor(x, y + 1);
        }
        if (x - 1 >= 0) {//West
            neighbourColors[3] = inPixelReader.getColor(x - 1, y);
        }
        return neighbourColors;
    }

    private Color blur(Color xyColor, Color... neighborColors) {
        if (neighborColors != null) {
            List<double[]> list = Arrays.stream(neighborColors)
                    .filter(Objects::nonNull)
                    .map(c -> new double[]{c.getRed(), c.getGreen(), c.getBlue()})
                    .collect(Collectors.toList());
            int numberOfPixels = list.size() + 1; //+1 it's a pixel XY itself
            double[] base = new double[]{xyColor.getRed() * numberOfPixels, xyColor.getGreen() * numberOfPixels, xyColor.getBlue() * numberOfPixels};
            base = list.stream().reduce(base, this::distract);

            final String[] colorNames = new String[]{"Red", "Green", "Blue"};
            for (int i = 0; i < 3; i++) {
                if (base[i] < 0 || base[i] > 1.0) {
                    System.out.printf("Out of Color (%s): %f\n", colorNames[i], base[i]);
                }
            }

            return new Color(base[0] < 0 ? 0 : base[0] > 1.0 ? 1.0 : base[0],
                    base[1] < 0 ? 0 : base[1] > 1.0 ? 1.0 : base[1],
                    base[2] < 0 ? 0 : base[2] > 1.0 ? 1.0 : base[2],
                    xyColor.getOpacity());
        }
        throw new IllegalArgumentException("Cannot obtain neighbour pixels");
    }

    private double[] distract(double[] base, double[] ref) {
        return new double[]{base[0] - ref[0], base[1] - ref[1], base[2] - ref[2]};
    }
}
