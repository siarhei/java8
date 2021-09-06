package iview.rental;

public class IdGenerator {
    private static long NEXT_ID;

    public static Long getNextId() {
        return NEXT_ID++;
    }
}
