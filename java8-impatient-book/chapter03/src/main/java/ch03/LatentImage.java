package ch03;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author siarhei
 */
class LatentImage {
    private final Image in;
    private final List<ColorTransformer> pendingOperations;

    LatentImage(Image in) {
        this.in = in;
        pendingOperations = new ArrayList<>();
    }

    LatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add(ColorTransformer.turn(f));
        return this;
    }

    Image toImage() {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = in.getPixelReader().getColor(x, y);
                for (ColorTransformer operation : pendingOperations) {
                    c = operation.apply(x, y, c);
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        }
        return out;
    }
}
