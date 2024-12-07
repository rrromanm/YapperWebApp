package sep3.dao;

import sep3.dto.notification.NotificationDTO;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDAOInterface {
    void sendNotification(int userId, String notificationMessage) throws SQLException;
    List<NotificationDTO> getNotifications(int userId) throws SQLException;
}
