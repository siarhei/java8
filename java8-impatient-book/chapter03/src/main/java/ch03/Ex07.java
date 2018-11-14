package ch03;

import java.util.Comparator;
import java.util.Optional;

/**
 * Write a method that generates a Comparator<String> that can be normal or reversed,
 * case-sensitive or case-insensitive, space-sensitive or space-insensitive,
 * or any combination thereof. Your method should return a lambda expression.
 *
 * @author sshchahratsou
 */
public class Ex07 {

    private enum Order {
        NORMAL(true),
        REVERSE(false),
        CASE_SENSITIVE(true),
        CASE_INSENSITIVE(false)

        private final boolean natural;
        Order(boolean natural) {
            this.natural = natural;
        }
    }

    private Comparator<String> generate(Boolean natural, Boolean caseSensitive, Boolean spaceSensitive) {
        Optional<Comparator<String>> naturalCmp = Optional.ofNullable(natural)
                .map(flag -> flag ? Comparator.naturalOrder() : Comparator.reverseOrder());
        Optional<Comparator<String>> caseSensCmp =Optional.ofNullable(natural)
                .map(flag -> flag ? Comparator.<String>naturalOrder() : (a, b) -> Comparator.comparing((String key) -> key.toLowerCase()) );
    }

    public static void main(String[] args) {
        byte b = 0b01101010;
        System.out.println(Integer.toBinaryString(b & ));
    }
}
