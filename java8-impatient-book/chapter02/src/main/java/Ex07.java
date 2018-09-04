import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex07 {
    /*
    *
    * Your manager asks you to write a method public static <T> boolean isFinite(Stream<T> stream).
    * Why isn’t that such a good idea? Go ahead and write it anyway.
    *
    * */

    public static void main(String[] args) {
        //it's impossible
    }

    public static boolean isFinite(Stream<?> stream) {
        stream.forEach(
                System.out::println
        );
        return false;
    }
}
