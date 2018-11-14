package ch03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

/**
 * Write a method that generates a Comparator<String> that can be normal or reversed,
 * case-sensitive or case-insensitive, space-sensitive or space-insensitive,
 * or any combination thereof. Your method should return a lambda expression.
 *
 * @author sshchahratsou
 */
public class Ex07 {

    private static Comparator<String> generate(boolean reverse, Boolean caseSensitive, Boolean spaceSensitive) {
        //direct mapping
        Function<String, String> keyExtractor = Function.identity();
        if (Boolean.FALSE.equals(caseSensitive)) {
            //case insensitive
            keyExtractor = keyExtractor.andThen(String::toLowerCase);
        }
        if (Boolean.FALSE.equals(spaceSensitive)) {
            //space insensitive
            keyExtractor = keyExtractor.andThen(String::trim);
        }


        Comparator<String> comparator = Comparator.comparing(keyExtractor);
        return reverse ? comparator.reversed() : comparator;

    }

    private static void print(String[] words) {
        for (String word : words) {
            System.out.printf("->%s<-\n", word);
        }
    }

    public static void main(String[] args) {
        String[] words = {"AbC1", "ABC", " abc3", " Abc4", "ABC "};

        Arrays.sort(words, generate(true, false, false));
        print(words);
    }
}
