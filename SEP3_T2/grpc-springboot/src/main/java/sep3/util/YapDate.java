package sep3.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class YapDate {
    private LocalDateTime postDateTime;

    /**
     * Constructs a YapDate instance from a `Timestamp`.
     * Converts the given timestamp to a `LocalDateTime`.
     *
     * @param timestamp The `Timestamp` to be converted to `LocalDateTime`.
     * @throws IllegalArgumentException If the provided timestamp is null.
     */
    public YapDate(Timestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.postDateTime = timestamp.toLocalDateTime();
    }

    /**
     * Returns the formatted date and time as a string according to the given pattern.
     *
     * @param pattern The format pattern to use when formatting the date.
     *                For example: "dd-MM-yyyy HH:mm:ss".
     * @return The formatted date as a string.
     * @throws IllegalArgumentException If the provided pattern is null or empty.
     */
    public String getFormattedDate(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return postDateTime.format(formatter);
    }

    /**
     * Gets the `LocalDateTime` representation of the post's date and time.
     *
     * @return The `LocalDateTime` of the post.
     */
    public LocalDateTime getPostDateTime() {
        return postDateTime;
    }

    /**
     * Returns a string representation of the `postDateTime` in the format "dd-MM-yyyy HH:mm:ss".
     *
     * @return The formatted string representation of the post's date and time.
     */
    @Override
    public String toString() {
        DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return postDateTime.format(defaultFormatter);
    }
}
