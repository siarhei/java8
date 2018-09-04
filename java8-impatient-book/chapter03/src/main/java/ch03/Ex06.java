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
public class Ex06 extends Application {
    /*Complete the method
        public static <T> Image transform(Image in, BiFunction<Color, T> f, T arg)
    from Section 3.4, “Returning Functions,” on page 53.*/

    private static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y), arg));
        return out;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("queen-mary.png");

        Image transformed = transform(image,
                (c, brightFactor) -> c.deriveColor(0, 1, brightFactor, 1),
                2);

        primaryStage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(transformed))));
        primaryStage.show();
    }
}
