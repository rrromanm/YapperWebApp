package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.moderator.ModeratorDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class ModeratorDAOTest {

    private ModeratorDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = ModeratorDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM moderator WHERE username = ?");
            statement.setString(1, "testuser");
            statement.executeUpdate();
        }
    }

    @Test
    void getModeratorByUserNameReturnsModerator() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO moderator (userid, username, password) VALUES (?, ?, ?)");
            statement.setInt(1, 100);
            statement.setString(2, "testuser");
            statement.setString(3, "password");
            statement.executeUpdate();
        }

        ModeratorDTO moderator = dao.getModeratorByUserName("testuser");

        assertNotNull(moderator);
        assertEquals("testuser", moderator.getUsername());
        assertEquals("password", moderator.getPassword());
    }

    @Test
    void getModeratorByUserNameThrowsExceptionIfNotFound() {
        assertThrows(SQLException.class, () -> dao.getModeratorByUserName("nonexistentuser"));
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM moderator WHERE username = ?");
            statement.setString(1, "testuser");
            statement.executeUpdate();
        }
    }
}
