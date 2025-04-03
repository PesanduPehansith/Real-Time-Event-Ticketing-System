import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Logger class to handle logging of system events
public class Logger {
    // Constant to specify the log file name
    private static final String LOG_FILE = "system.log";

    // Method to log a message to the console and log file
    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = "[" + timestamp + "] " + message;

        // Log the message to the console
        System.out.println(logMessage);

        // Log the message to the log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
            Logger.log("Failed to write to log file: " + e.getMessage());
        }
    }
}
