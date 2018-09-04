package ch03;

import java.util.function.Supplier;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author siarhei
 */
public class Ex01 {
    private static final Logger _log = Logger.getLogger(Ex01.class.getCanonicalName());

    static {
        _log.setLevel(Level.FINEST);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        _log.addHandler(handler);
    }

    /*
    Enhance the lazy logging technique by providing conditional logging. A typical call would be
    logIf(Level.FINEST, () -> i == 10, () -> "a[10] = " + a[10]).
    Don’t evaluate the condition if the logger won’t log the message.
     */

    public static void main(String[] args) {
        logIf(Level.FINEST,
                () -> "Hello, World!",
                () -> "Yes, I'm",
                () -> "i = " + 5);
    }

    private static void logIf(Level logLevel, Supplier<String> ... logSuppliers) {
        if (_log.isLoggable(logLevel)) {
            if (logSuppliers != null) {
                for (Supplier<String> logSupplier : logSuppliers) {
                    _log.log(logLevel, logSupplier.get());
                }
            }
        }
    }
}
