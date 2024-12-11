package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dao.NotificationDAO;
import sep3.dto.notification.NotificationDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDAOTest {

    private NotificationDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = NotificationDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM notification WHERE userid = ?");
            statement.setInt(1, 1);
            statement.executeUpdate();
        }
    }

    @Test
    void sendNotificationInsertsNotification() throws SQLException {
        dao.sendNotification(1, "Test Notification");

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM notification WHERE userid = ? AND content = ?");
            statement.setInt(1, 1);
            statement.setString(2, "Test Notification");

            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(1, resultSet.getInt("userid"));
            assertEquals("Test Notification", resultSet.getString("content"));
        }
    }

    @Test
    void getNotificationsReturnsNotifications() throws SQLException {
        dao.sendNotification(1, "Test Notification 1");
        dao.sendNotification(1, "Test Notification 2");

        List<NotificationDTO> notifications = dao.getNotifications(1);

        assertEquals(2, notifications.size());
        assertEquals("Test Notification 1", notifications.get(0).getNotificationMessage());
        assertEquals("Test Notification 2", notifications.get(1).getNotificationMessage());
    }

    @Test
    void getNotificationsReturnsEmptyListIfNone() throws SQLException {
        List<NotificationDTO> notifications = dao.getNotifications(999);

        assertTrue(notifications.isEmpty());
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM notification WHERE userid = ?");
            statement.setInt(1, 1);
            statement.executeUpdate();
        }
    }
}
