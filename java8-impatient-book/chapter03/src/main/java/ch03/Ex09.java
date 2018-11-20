package ch03;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Write a method lexicographicComparator(String... fieldNames) that yields a comparator that compares the given
 * fields in the given order. For example, a lexicographicComparator("lastname", "firstname") takes two objects and,
 * using reflection, gets the values of the lastname field. If they are different, return the difference, otherwise
 * move on to the firstname field. If all fields match, return 0.
 *
 * @author siarhei
 */
public class Ex09 {
    static class A {
        String firstName;
    }

    static class B {
        String firstName;
    }

    public static void main(String[] args) {
        A a = new A();
        a.firstName = "abd";
        B b = new B();
        b.firstName = "abc";

        Object[] array = {a, b};
        Arrays.sort(array, lexicographicComparator("firstName"));
        Stream.of(array).forEach(System.out::println);
    }

    private static Comparator<? super Object> lexicographicComparator(String... fieldNames) {
        return (o1, o2) -> {
            return Arrays.stream(fieldNames)
                    .map(fieldName -> new Comparable[]{getValue(o1, fieldName), getValue(o2, fieldName)})
                    .map(cmp -> cmp[0].compareTo(cmp[1]))
                    .filter(res -> res != 0)
                    .findFirst()
                    .orElse(0);
        };
    }

    private static Comparable getValue(Object o, String fieldName) {
        Comparable value = null;
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = (Comparable) field.get(o);
        } catch (Exception e) {
            System.err.println(e);
        }
        return value;
    }
}
