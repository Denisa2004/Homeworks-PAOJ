import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ex4 {
    public static class Logger {
        private static Logger instance;

        private Logger() {}

        public static Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }

        private String timestamp() {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        public void logInfo(String msg) {
            System.out.println("[INFO] " + timestamp() + " - " + msg);
        }

        public void logWarning(String msg) {
            System.out.println("[WARNING] " + timestamp() + " - " + msg);
        }

        public void logError(String msg) {
            System.out.println("[ERROR] " + timestamp() + " - " + msg);
        }
    }

    public static void run() {
        Logger logger = Logger.getInstance();
        logger.logInfo("Aplicatia a pornit.");
        logger.logWarning("Acesta este un warning.");
        logger.logError("A aparut o eroare.");
    }
}
