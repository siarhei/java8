import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex10 {
    /*
    *
    * Write a call to reduce that can be used to compute the average of a Stream<Double>.
    * Why can’t you simply compute the sum and divide by count()?
    * */

    public static void main(String[] args) {
        System.out.println("Avg: " + avg(Stream.of(2.0, 4.0, 6.0)));
    }

    private static Double avg(Stream<Double> numbers) {

        Double[] pairs = numbers.collect(
                () -> new Double[]{0.0, 0.0}, //supplier
                (a, b) -> { //accumulator
                    a[0] += 1.0;
                    a[1] += b;
                },
                (a, b) -> { //combiner
                    a[0] += b[0];
                    a[1] += b[1];
                }
        );

        return pairs[0] != 0.0 ? pairs[1] / pairs[0] : Double.NaN;
    }
}
