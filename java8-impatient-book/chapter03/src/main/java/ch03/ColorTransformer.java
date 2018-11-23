package ch03;

import javafx.scene.paint.Color;

import java.util.function.UnaryOperator;

/**
 * @author siarhei
 */
@FunctionalInterface
interface ColorTransformer {
    Color apply(int x, int y, Color colorAtXY);

    /**
     * Ex 11: Implement static methods that can compose two ColorTransformer objects
     */
    static ColorTransformer compose(ColorTransformer ct1, ColorTransformer ct2) {
        return (x, y, c) -> ct2.apply(x, y, ct1.apply(x, y, c));
    }

    /**
     * Ex 11: a static method that turns a UnaryOperator<Color> into a ColorTransformer that ignores the x- and y-coordinates
     */
    static ColorTransformer turn(UnaryOperator<Color> op) {
        return (x, y, c) -> op.apply(c);
    }
}
