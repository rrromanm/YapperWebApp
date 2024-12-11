package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.smuser.*;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SMUserDAOTest {
    private SMUserDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = SMUserDAO.getInstance();
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.follows");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM yapper_database.social_media_user");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO yapper_database.social_media_user (userid, username, password, nickname, email) VALUES (100, 'user1', 'password1', 'nickname1', 'user1@example.com')");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO yapper_database.social_media_user (userid, username, password, nickname, email) VALUES (101, 'user2', 'password2', 'nickname2', 'user2@example.com')");
            statement.executeUpdate();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.follows");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM yapper_database.social_media_user");
            statement.executeUpdate();
        }
    }

    @Test
    void createUserCreatesUserInDatabase() throws SQLException {
        CreateSMUserDTO dto = new CreateSMUserDTO("user3", "password3", "nickname3", "user3@example.com");
        int userId = dao.createUser(dto);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.social_media_user WHERE userid = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("user3", resultSet.getString("username"));
        }
    }

    @Test
    void updateEmailUpdatesUserEmail() throws SQLException {
        dao.updateEmail(100, "newemail@example.com");

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT email FROM yapper_database.social_media_user WHERE userid = ?");
            statement.setInt(1, 100);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("newemail@example.com", resultSet.getString("email"));
        }
    }

    @Test
    void updateNicknameUpdatesUserNickname() throws SQLException {
        dao.updateNickname(100, "newnickname");

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT nickname FROM yapper_database.social_media_user WHERE userid = ?");
            statement.setInt(1, 100);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("newnickname", resultSet.getString("nickname"));
        }
    }

    @Test
    void updatePasswordUpdatesUserPassword() throws SQLException {
        dao.updatePassword(100, "newpassword");

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM yapper_database.social_media_user WHERE userid = ?");
            statement.setInt(1, 100);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("newpassword", resultSet.getString("password"));
        }
    }

    @Test
    void deleteSMUserRemovesUserAndRelatedData() throws SQLException {
        dao.deleteSMUser(100);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.social_media_user WHERE userid = ?");
            statement.setInt(1, 100);
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next());
        }
    }

    @Test
    void getUserByUsernameReturnsCorrectUser() throws SQLException {
        SMUserDTO user = dao.getUserByUsername("user1");

        assertEquals(100, user.getId());
        assertEquals("nickname1", user.getNickname());
    }

    @Test
    void getUserByIdReturnsCorrectUser() throws SQLException {
        SMUserDTO user = dao.getUserById(100);

        assertEquals("user1", user.getUsername());
        assertEquals("nickname1", user.getNickname());
    }

    @Test
    void getAllUsersReturnsAllUsers() throws SQLException {
        ArrayList<SMUserDTO> users = dao.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void followUserCreatesFollowRelationship() throws SQLException {
        dao.followUser(100, 101);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.follows WHERE followerid = ? AND followedid = ?");
            statement.setInt(1, 100);
            statement.setInt(2, 101);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
        }
    }

    @Test
    void unfollowUserRemovesFollowRelationship() throws SQLException {
        dao.followUser(100, 101);
        dao.unfollowUser(100, 101);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.follows WHERE followerid = ? AND followedid = ?");
            statement.setInt(1, 100);
            statement.setInt(2, 101);
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next());
        }
    }

    @Test
    void getFollowersReturnsCorrectFollowers() throws SQLException {
        dao.followUser(101, 100);
        ArrayList<FollowerDTO> followers = dao.getFollowers(100);

        assertEquals(1, followers.size());
        assertEquals("user2", followers.get(0).getUsername());
    }

    @Test
    void getFollowingReturnsCorrectFollowing() throws SQLException {
        dao.followUser(100, 101);
        ArrayList<FollowerDTO> following = dao.getFollowing(100);

        assertEquals(1, following.size());
        assertEquals("user2", following.get(0).getUsername());
    }

    @Test
    void isFollowingReturnsCorrectFollowStatus() throws SQLException {
        dao.followUser(100, 101);

        assertTrue(dao.isFollowing(100, 101));
        assertFalse(dao.isFollowing(101, 100));
    }

    @Test
    void getThreeRandomUsersReturnsUsers() throws SQLException {
        ArrayList<FollowerDTO> users = dao.getThreeRandomUsers(100);

        assertEquals(1, users.size());
    }

    @Test
    void getUsersBySearchReturnsMatchingUsers() throws SQLException {
        ArrayList<FollowerDTO> users = dao.getUsersBySearch("user");

        assertEquals(2, users.size());
    }
}
