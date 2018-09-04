import java.math.BigDecimal;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 * @author siarhei
 */
public class Ex05 {
    private static final BigDecimal A = BigDecimal.valueOf(25214903917L);
    private static final BigDecimal C = BigDecimal.valueOf(11L);
    private static final BigDecimal M = BigDecimal.valueOf(2^48);
    private static final BigDecimal SEED = BigDecimal.TEN;
    private static final UnaryOperator<BigDecimal> FORMULA = xn -> (A.multiply(xn).add(C)).remainder(M);

    /*
    * Using Stream.iterate, make an infinite stream of random numbers—not by calling Math.random
    * but by directly implementing a linear congruential generator. In such a generator, you start
    * with x0 = seed and then produce X(n + 1) = (a * X(n) + c) % m, for appropriate values of a, c, and m.
    * You should implement a method with parameters a, c, m, and seed that yields a Stream<Long>.
    * Try out a = 25214903917, c = 11, and m = 2^48.
    * */

    public static void main(String[] args) {
        Stream<BigDecimal> randStream = Stream.iterate(SEED, FORMULA);

        randStream.limit(5).forEach(System.out::println);
    }
}
