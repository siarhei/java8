package iview.rental;

public class IdGenerator {
    private static Long NEXT_ID;

    public static Long getNextId() {
        return NEXT_ID++;
    }
}
