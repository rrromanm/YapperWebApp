package sep3.shared;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class YapDate {
    private LocalDateTime postDateTime;

    // Constructor that takes a Timestamp
    public YapDate(Timestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.postDateTime = timestamp.toLocalDateTime();
    }

    // Method to get the formatted date string
    public String getFormattedDate(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return postDateTime.format(formatter);
    }

    // Method to get the LocalDateTime object
    public LocalDateTime getPostDateTime() {
        return postDateTime;
    }

    // Optional: Overriding toString for default format
    @Override
    public String toString() {
        DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return postDateTime.format(defaultFormatter);
    }
}
