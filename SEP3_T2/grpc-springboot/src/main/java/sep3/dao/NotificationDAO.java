package sep3.dao;

import sep3.dto.notification.NotificationDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO implements NotificationDAOInterface {
    private static NotificationDAO instance;

    private NotificationDAO() {}

    public static NotificationDAO getInstance() {
        if (instance == null) {
            instance = new NotificationDAO();
        }
        return instance;
    }
    @Override
    public void sendNotification(int userId, String notificationMessage) throws SQLException {
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.notification (content, userid) VALUES (?,?)");
            statement.setString(1, notificationMessage);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to send notification");
        }

    }

    @Override
    public List<NotificationDTO> getNotifications(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT content, date, userid FROM yapper_database.notification WHERE userid = ?"
            );
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            List<NotificationDTO> notifications = new ArrayList<>();
            while (resultSet.next()) {
                NotificationDTO notification = new NotificationDTO();
                notification.setNotificationMessage(resultSet.getString("content"));
                notification.setTimestamp(resultSet.getString("date"));
                notification.setUserId(resultSet.getInt("userid"));
                notifications.add(notification);
            }
            return notifications;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get notifications");
        }
    }
}