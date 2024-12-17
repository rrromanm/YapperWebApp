package sep3.dto.notification;

/**
 * Data transfer object for representing a notification.
 */
public class NotificationDTO {
    private int userId;
    private String notificationMessage;
    private String timestamp;

    /**
     * Retrieves the user ID associated with the notification.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the notification message.
     *
     * @return The notification message.
     */
    public String getNotificationMessage() {
        return notificationMessage;
    }

    /**
     * Sets the user ID associated with the notification.
     *
     * @param userId The user ID to be set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the notification message.
     *
     * @param notificationMessage The notification message to be set.
     */
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    /**
     * Retrieves the timestamp of the notification.
     *
     * @return The timestamp of the notification.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the notification.
     *
     * @param timestamp The timestamp to be set.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns a string representation of the NotificationDTO object.
     *
     * @return A string representation of the notification.
     */
    @Override
    public String toString() {
        return "NotificationDTO{" +
                "userId='" + userId + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                '}';
    }
}