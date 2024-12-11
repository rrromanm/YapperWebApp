package sep3.dao;

import org.junit.jupiter.api.*;
import sep3.dto.comment.CommentDTO;
import sep3.dto.comment.CreateCommentDTO;
import sep3.dto.comment.UpdateCommentDTO;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommentDAOTest {
    private CommentDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = CommentDAO.getInstance();

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM comment");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM post WHERE postId IN (999, 998)");
            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM social_media_user WHERE userId IN (101, 100)");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO social_media_user (userId,nickname, username, email, password) VALUES (100, 'user1','user1' , 'user1@example.com', 'password1')");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO social_media_user (userId,nickname, username, email, password) VALUES (101, 'user2','user2', 'user2@example.com', 'password2')");
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO post (postId, title, body, userid) VALUES (999, 'Post 1', 'Content for post 1','101')");
            statement.executeUpdate();
            statement = connection.prepareStatement("INSERT INTO post (postId, title, body, userid) VALUES (998, 'Post 2', 'Content for post 2','101')");
            statement.executeUpdate();
        }
    }

    @Test
    void createCommentInsertsIntoDatabase() throws SQLException {
        CreateCommentDTO createDto = new CreateCommentDTO("Test comment", 100, 999);
        dao.createComment(createDto);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE body = ?");
            statement.setString(1, "Test comment");
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());
            assertEquals("Test comment", resultSet.getString("body"));
            assertEquals(100, resultSet.getInt("userid"));
            assertEquals(999, resultSet.getInt("postid"));
        }
    }

    @Test
    void updateCommentUpdatesDatabase() throws SQLException {
        CreateCommentDTO createDto = new CreateCommentDTO("Initial comment", 100, 999);
        dao.createComment(createDto);

        CommentDTO createdComment = dao.getAllComments().get(0);
        UpdateCommentDTO updateDto = new UpdateCommentDTO(createdComment.getCommentId(), "Updated comment body");
        dao.updateComment(updateDto);

        CommentDTO updatedComment = dao.getComment(createdComment.getCommentId());
        assertEquals("Updated comment body", updatedComment.getBody());
    }

    @Test
    void deleteCommentRemovesFromDatabase() throws SQLException {
        CreateCommentDTO createDto = new CreateCommentDTO("Comment to delete", 100, 999);
        dao.createComment(createDto);

        CommentDTO createdComment = dao.getAllComments().get(0);
        dao.deleteComment(createdComment.getCommentId());

        assertThrows(SQLException.class, () -> dao.getComment(createdComment.getCommentId()));
    }

    @Test
    void getCommentReturnsCorrectComment() throws SQLException {
        CreateCommentDTO createDto = new CreateCommentDTO("Test comment", 101, 999);
        dao.createComment(createDto);

        CommentDTO createdComment = dao.getAllComments().get(0);
        CommentDTO retrievedComment = dao.getComment(createdComment.getCommentId());

        assertEquals(createdComment.getCommentId(), retrievedComment.getCommentId());
        assertEquals(createdComment.getBody(), retrievedComment.getBody());
    }

    @Test
    void getAllCommentsReturnsAllComments() throws SQLException {
        dao.createComment(new CreateCommentDTO("Comment 1", 100, 998));
        dao.createComment(new CreateCommentDTO("Comment 2", 101, 999));

        ArrayList<CommentDTO> allComments = dao.getAllComments();
        assertEquals(2, allComments.size());
    }

    @Test
    void getCommentsByPostIdReturnsFilteredComments() throws SQLException {
        dao.createComment(new CreateCommentDTO("Post 1 Comment", 100, 999));
        dao.createComment(new CreateCommentDTO("Post 2 Comment", 101, 998));

        ArrayList<CommentDTO> commentsByPostId = dao.getCommentsByPostId(999);
        assertEquals(1, commentsByPostId.size());
        assertEquals("Post 1 Comment", commentsByPostId.get(0).getBody());
    }

    @Test
    void getCommentsByUserIdReturnsFilteredComments() throws SQLException {
        dao.createComment(new CreateCommentDTO("User 1 Comment", 100, 999));
        dao.createComment(new CreateCommentDTO("User 2 Comment", 101, 998));

        ArrayList<CommentDTO> commentsByUserId = dao.getCommentsByUserId(100);
        assertEquals(1, commentsByUserId.size());
        assertEquals("User 1 Comment", commentsByUserId.get(0).getBody());
    }

    @Test
    void unlikeCommentRemovesLikeFromDatabase() throws SQLException {
        dao.createComment(new CreateCommentDTO("Unlikeable comment", 100, 999));
        CommentDTO createdComment = dao.getAllComments().get(0);
        dao.likeComment(createdComment.getCommentId(), 2);
        dao.unlikeComment(createdComment.getCommentId(), 2);

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM liked_comment WHERE userId = ? AND commentId = ?");
            statement.setInt(1, 2);
            statement.setInt(2, createdComment.getCommentId());
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next());
        }
    }

    @Test
    void getLikedCommentsByUserIdReturnsLikedComments() throws SQLException {
        dao.createComment(new CreateCommentDTO("Likeable comment 1", 100, 998));
        dao.createComment(new CreateCommentDTO("Likeable comment 2", 101, 999));

        CommentDTO comment1 = dao.getAllComments().get(0);
        CommentDTO comment2 = dao.getAllComments().get(1);

        dao.likeComment(comment1.getCommentId(), 100);
        dao.likeComment(comment2.getCommentId(), 101);

        ArrayList<CommentDTO> likedComments = dao.getLikedCommentsByUserId(100);
        assertEquals(1, likedComments.size());
        assertEquals("Likeable comment 2", likedComments.get(0).getBody());
    }

    @Test
    void likeCommentThrowsSQLExceptionWhenInsertFails() {
        assertThrows(SQLException.class, () -> dao.likeComment(9999, 1));
    }
}
