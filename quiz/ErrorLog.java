package quiz;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class ErrorLog implements Observer {
    private static final String ERROR_LOG_FILE = "error.log";

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            logError((String) arg);
        }
    }

    // log error to error log file
    private void logError(String errorMessage) {
        try (FileWriter writer = new FileWriter(ERROR_LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + ": " + errorMessage + "\n");
        } catch (IOException e) {
            System.out.println("Failed to log error: " + e.getMessage());
        }
    }
}