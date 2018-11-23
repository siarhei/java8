package ch03;

import java.awt.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Why can’t one call
 * <p>
 * {@code UnaryOperator op = Color::brighter;}
 * {@code Image finalImage = transform(image, op.compose(Color::grayscale));}
 * </p>
 * Look carefully at the return type of the compose method of UnaryOperator<T>.
 * Why is it not appropriate for the transform method? What does that say about
 * the utility of structural and nominal types when it comes to function composition?
 *
 * @author siarhei
 */
public class Ex10 {
    public static void main(String[] args) {
        UnaryOperator<Color> op = Color::brighter;
        //<V> Function<V, R> compose(Function<? super V, ? extends T> before)
        //Function<Color, Color> fn = op.compose(Color::darker);
        op.compose(c -> Color.BLACK); //Object to Color fn
    }
}
