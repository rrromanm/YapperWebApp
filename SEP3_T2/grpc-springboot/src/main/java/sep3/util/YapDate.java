package sep3.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class YapDate {
    private LocalDateTime postDateTime;

    public YapDate(Timestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.postDateTime = timestamp.toLocalDateTime();
    }

    public String getFormattedDate(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return postDateTime.format(formatter);
    }

    public LocalDateTime getPostDateTime() {
        return postDateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return postDateTime.format(defaultFormatter);
    }
}
