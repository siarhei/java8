package ch08;

import java.time.Instant;
import java.util.Random;

/**
 * @author sshchahratsou
 */
public class Ex01 {
    //Write a program that adds, subtracts, divides, and compares numbers between
    //0 and 2^32 – 1, using int values and unsigned operations. Show why
    //divideUnsigned and remainderUnsigned are necessary.

    public static void main(String[] args) {
        Random random = new Random(Instant.now().toEpochMilli());

        Integer x = /*random.nextInt();*/ Integer.MAX_VALUE;

        System.out.printf("Signed: %d (0x%s); Unsigned: %d (0x%s)\n", x, Long.toHexString(x), Integer.toUnsignedLong(x), Long.toHexString(Integer.toUnsignedLong(x)));

        int remainder10 = Integer.remainderUnsigned(x, 10);
        System.out.println("Remainder 10: " + remainder10);

        int divide10 = Integer.divideUnsigned(x, 10);
        System.out.println("Divide 10: " + divide10);
    }
}
