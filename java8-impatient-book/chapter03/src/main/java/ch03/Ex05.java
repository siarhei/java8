package ch03;

import java.util.function.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

@FunctionalInterface
interface ColorTransformer {
    Color apply(int x, int y, Color colorAtXY);
}

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

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        ColorTransformer frame10px = (x, y, c) -> (x < 10 || width - x < 10) || (y < 10 || height - y < 10) ? Color.GRAY : c;
        Image image2 = transform(image, frame10px);

        stage.setScene(new Scene(new HBox(new ImageView(image), /*new ImageView(brightenedImage),*/ new ImageView(image2))));
        stage.show();
    }
}
