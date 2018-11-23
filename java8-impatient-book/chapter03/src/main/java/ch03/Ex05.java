package ch03;

import java.util.function.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

/**
 * @author siarhei
 */
public class Ex05 extends Application {
    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y)));
        return out;
    }

    public static Image transform(Image in, ColorTransformer f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(x, y, in.getPixelReader().getColor(x, y)));
        return out;
    }

    @Override
    public void start(Stage stage) {
        Image image = new Image("queen-mary.png");
        Image brightenedImage = transform(image, Color::brighter);
        ColorTransformer original = (x, y, c) -> (x / 5) % 2 == (y / 5) % 2 ? Color.GRAY : c;

        Image withFrame = transform(image, createFrame(image, 50, Color.AZURE));
        Image brightenedWithFrame = transform(image,
                ColorTransformer.compose(ColorTransformer.turn(Color::brighter), createFrame(image, 20, Color.GOLD)));

        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(brightenedWithFrame))));
        stage.show();
    }

    /*
     * Ex 08
     * Generalize Exercise 5 by writing a static method that yields a ColorTransformer
     * that adds a frame of arbitrary thickness and color to an image.
     */
    private static ColorTransformer createFrame(Image image, int thickness, Color color) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        if (thickness * 2 > width || thickness * 2 > height) {
            throw new IllegalArgumentException("Cannot create frame");
        }

        return (x, y, c) -> (x < thickness || width - x < thickness)
                || (y < thickness || height - y < thickness)
                ? color : c;
    }
}
