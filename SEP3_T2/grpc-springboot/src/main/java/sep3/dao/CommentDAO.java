package sep3.dao;

import sep3.dto.comment.CommentDTO;
import sep3.dto.comment.CreateCommentDTO;
import sep3.dto.comment.UpdateCommentDTO;
import sep3.util.YapDate;
import sep3.util.DatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommentDAO implements CommentDAOInterface {

    private static CommentDAO instance;

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to get the singleton instance.
     */
    private CommentDAO() {}

    /**
     * Returns the singleton instance of {@code CommentDAO}.
     *
     * @return the singleton instance of {@code CommentDAO}
     * @throws SQLException if a database access error occurs
     */
    public static CommentDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }

    /**
     * Helper method to format a SQL {@link Timestamp} into a readable date-time string.
     *
     * @param timestamp the SQL {@link Timestamp} to be formatted
     * @return the formatted date-time string
     */
    private String formatTimestamp(Timestamp timestamp) {
        LocalDateTime commentDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return commentDateTime.format(formatter);
    }

    /**
     * Creates a new comment by inserting it into the database.
     *
     * @param dto the {@link CreateCommentDTO} object containing the comment details
     * @throws SQLException if there is an error while creating the comment
     */
    @Override
    public void createComment(CreateCommentDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comment (body, userId, postId) VALUES (?,?,?)");
            statement.setString(1, dto.getBody());
            statement.setInt(2, dto.getUserId());
            statement.setInt(3, dto.getPostId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to create a comment", e);
        }
    }

    /**
     * Updates the content of an existing comment in the database.
     *
     * @param dto the {@link UpdateCommentDTO} object containing the updated comment details
     * @throws SQLException if there is an error while updating the comment
     */
    @Override
    public void updateComment(UpdateCommentDTO dto) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE comment SET body = ? WHERE commentId = ?");
            statement.setString(1, dto.getBody());
            statement.setInt(2, dto.getCommentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update a comment", e);
        }
    }

    /**
     * Deletes a comment from the database by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     * @throws SQLException if there is an error while deleting the comment
     */
    @Override
    public void deleteComment(int commentId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.comment WHERE commentId = ?");
            statement.setInt(1, commentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete a comment", e);
        }
    }

    /**
     * Retrieves a single comment from the database by its ID.
     *
     * @param commentId the ID of the comment to retrieve
     * @return a {@link CommentDTO} object containing the comment details
     * @throws SQLException if there is an error while retrieving the comment
     */
    @Override
    public CommentDTO getComment(int commentId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE commentId = ?");
            statement.setInt(1, commentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String formattedDateTime = formatTimestamp(resultSet.getTimestamp("commentdate"));

                return new CommentDTO(
                        resultSet.getString("body"),
                        formattedDateTime,
                        resultSet.getInt("likecount"),
                        resultSet.getInt("commentid"),
                        resultSet.getInt("userid"),
                        resultSet.getInt("postid")
                );
            } else {
                throw new SQLException("Comment not found");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve comment", e);
        }
    }

    /**
     * Retrieves all comments from the database, sorted by the most recent.
     *
     * @return an {@link ArrayList} of {@link CommentDTO} objects containing all comments
     * @throws SQLException if there is an error while retrieving the comments
     */
    @Override
    public ArrayList<CommentDTO> getAllComments() throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment ORDER BY commentDate DESC");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CommentDTO> comments = new ArrayList<>();

            while (resultSet.next()) {
                String date = new YapDate(resultSet.getTimestamp("commentdate")).toString();

                comments.add(new CommentDTO(
                        resultSet.getString("body"),
                        date,
                        resultSet.getInt("likecount"),
                        resultSet.getInt("commentid"),
                        resultSet.getInt("userid"),
                        resultSet.getInt("postid")
                ));
            }
            return comments;
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve all comments", e);
        }
    }

    /**
     * Retrieves all comments related to a specific post.
     *
     * @param postId the ID of the post whose comments are to be retrieved
     * @return an {@link ArrayList} of {@link CommentDTO} objects related to the post
     * @throws SQLException if there is an error while retrieving the comments
     */
    @Override
    public ArrayList<CommentDTO> getCommentsByPostId(int postId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE postId = ? ORDER BY commentDate DESC");
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CommentDTO> comments = new ArrayList<>();

            while (resultSet.next()) {
                String formattedDateTime = formatTimestamp(resultSet.getTimestamp("commentdate"));

                comments.add(new CommentDTO(
                        resultSet.getString("body"),
                        formattedDateTime,
                        resultSet.getInt("likecount"),
                        resultSet.getInt("commentid"),
                        resultSet.getInt("userid"),
                        resultSet.getInt("postid")
                ));
            }
            return comments;
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve comments by post id", e);
        }
    }

    /**
     * Retrieves all comments made by a specific user.
     *
     * @param userId the ID of the user whose comments are to be retrieved
     * @return an {@link ArrayList} of {@link CommentDTO} objects related to the user
     * @throws SQLException if there is an error while retrieving the comments
     */
    @Override
    public ArrayList<CommentDTO> getCommentsByUserId(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE userId = ? ORDER BY commentDate DESC");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CommentDTO> comments = new ArrayList<>();

            while (resultSet.next()) {
                String formattedDateTime = formatTimestamp(resultSet.getTimestamp("commentdate"));

                comments.add(new CommentDTO(
                        resultSet.getString("body"),
                        formattedDateTime,
                        resultSet.getInt("likecount"),
                        resultSet.getInt("commentid"),
                        resultSet.getInt("userid"),
                        resultSet.getInt("postid")
                ));
            }
            return comments;
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve comments by user id", e);
        }
    }

    /**
     * Marks a comment as "liked" by a user.
     *
     * @param commentId the ID of the comment to be liked
     * @param userId the ID of the user who likes the comment
     * @throws SQLException if there is an error while liking the comment
     */
    @Override
    public void likeComment(int commentId, int userId) throws SQLException
    {
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO liked_comment (userId, commentId) VALUES (?, ?)");
            statement.setInt(1, userId);
            statement.setInt(2, commentId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to like comment");
        }
    }

    /**
     * Removes the "like" from a comment for a user.
     *
     * @param commentId the ID of the comment to be unliked
     * @param userId the ID of the user who unlikes the comment
     * @throws SQLException if there is an error while unliking the comment
     */
    @Override public void unlikeComment(int commentId, int userId) throws SQLException
    {
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM liked_comment WHERE userId = ? AND commentId = ?");
            statement.setInt(1, userId);
            statement.setInt(2, commentId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to unlike comment");
        }

    }

    /**
     * Retrieves all comments that have been liked by a specific user.
     *
     * @param userId the ID of the user whose liked comments are to be retrieved
     * @return an {@link ArrayList} of {@link CommentDTO} objects representing the liked comments
     * @throws SQLException if there is an error while retrieving the liked comments
     */
    @Override public ArrayList<CommentDTO> getLikedCommentsByUserId(int userId) throws SQLException
    {
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE commentId IN (SELECT commentId FROM liked_comment WHERE userId = ?)");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CommentDTO> comments = new ArrayList<>();
            while (resultSet.next()) {
                String formattedDateTime = formatTimestamp(resultSet.getTimestamp("commentdate"));

                comments.add(new CommentDTO(
                    resultSet.getString("body"),
                    formattedDateTime,
                    resultSet.getInt("likecount"),
                    resultSet.getInt("commentid"),
                    resultSet.getInt("userid"),
                    resultSet.getInt("postid")
                ));
            }
            return comments;
        } catch (SQLException e)
        {
            throw new SQLException("Failed to retrieve comments by user id", e);
        }
    }

}