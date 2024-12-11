package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.report.PostReportDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportDAOTest {
    private ReportDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = ReportDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reported_post");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM post");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM social_media_user WHERE userId IN (100, 101)");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO social_media_user (userId, nickname, username, email, password) VALUES (100, 'user1', 'user1', 'user1@example.com', 'password1')");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO social_media_user (userId, nickname, username, email, password) VALUES (101, 'user2', 'user2', 'user2@example.com', 'password2')");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO post (postId, title, body, userId) VALUES (1000, 'Post 1', 'Content 1', 100)");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO post (postId, title, body, userId) VALUES (1001, 'Post 2', 'Content 2', 101)");
            statement.executeUpdate();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reported_post");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM post");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM social_media_user WHERE userId IN (100, 101)");
            statement.executeUpdate();
        }
    }

    @Test
    void sendReportInsertsReportIntoDatabase() throws SQLException {
        dao.sendReport(100, 1000);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reported_post WHERE userId = ? AND postId = ?");
            statement.setInt(1, 100);
            statement.setInt(2, 1000);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals(100, resultSet.getInt("userId"));
            assertEquals(1000, resultSet.getInt("postId"));
        }
    }

    @Test
    void getReportsReturnsAllReports() throws SQLException {
        dao.sendReport(100, 1000);
        dao.sendReport(101, 1001);

        ArrayList<PostReportDTO> reports = dao.getReports();
        assertEquals(2, reports.size());
    }

    @Test
    void rejectReportRemovesReportFromDatabase() throws SQLException {
        dao.sendReport(100, 1000);

        PostReportDTO report = dao.getReports().get(0);
        dao.rejectReport(report.getReportID());

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reported_post WHERE reportId = ?");
            statement.setInt(1, report.getReportID());
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next());
        }
    }
}
