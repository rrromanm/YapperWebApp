package sep3.dto.notification;

import com.google.type.DateTime;

import java.sql.Timestamp;

public class NotificationDTO {
    private int userId;
    private String notificationMessage;

    private String timestamp;

    public int getUserId() {
        return userId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "userId='" + userId + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                '}';
    }


}
