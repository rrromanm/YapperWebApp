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

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to get the singleton instance.
     */
    private NotificationDAO() {}

    /**
     * Returns the singleton instance of {@code NotificationDAO}.
     *
     * @return the singleton instance of {@code NotificationDAO}
     */
    public static NotificationDAO getInstance() {
        if (instance == null) {
            instance = new NotificationDAO();
        }
        return instance;
    }

    /**
     * Sends a notification to the specified user by adding an entry to the notification table.
     *
     * @param userId              the ID of the user to send the notification to
     * @param notificationMessage the message to be sent as the notification
     * @throws SQLException if an error occurs while inserting the notification into the database
     */
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

    /**
     * Retrieves a list of notifications for a given user.
     *
     * @param userId the ID of the user whose notifications are being requested
     * @return a list of {@link NotificationDTO} objects containing notification details
     * @throws SQLException if an error occurs while retrieving notifications from the database
     */
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