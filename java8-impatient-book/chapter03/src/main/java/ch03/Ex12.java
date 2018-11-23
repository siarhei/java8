package ch03;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Enhance the LatentImage class in Section 3.6, “Laziness,” on page 56, so that it supports both
 * UnaryOperator<Color> and ColorTransformer. Hint: Adapt the former to the latter.
 * @author siarhei
 */
public class Ex12 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("queen-mary.png");
        LatentImage lImage = new LatentImage(image);

        lImage.transform(Color::invert);

        primaryStage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(lImage.toImage()))));
        primaryStage.show();
    }

}
