package iview;

/**
 * @author sshchahratsou
 */
public class Flag {
    private static final char FLAG_BORDER = '#';
    private static final char CIRCLE_BORDER = '*';
    private static final char FLAG_SPACE = ' ';
    private static final char CIRCLE_CONTENT = 'o';

    public static void main(String[] args) {
        assert args != null : "Arguments are expected";
        assert args.length == 1 : "One argument is expected";

        int n = Integer.parseInt(args[0]);
        int width = 3 * n + 2;
        int height = 2 * n + 2;

        printFlag(width, height);
    }

    private static void printFlag(int width, int height) {
        for (int i = 0; i < height; i++) { //row index
            for (int j = 0; j < width; j++) { //column index
                if (i == 0 || i == height-1 || j == 0 || j == width-1) {
                    System.out.print(FLAG_BORDER);
                } else {
                    System.out.print(FLAG_SPACE);
                }
            }
            System.out.println(); //end of a i-th row
        }
    }
}
