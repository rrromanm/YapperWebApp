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

    private CommentDAO() {}

    public static CommentDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }

    private String formatTimestamp(Timestamp timestamp) {
        LocalDateTime commentDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return commentDateTime.format(formatter);
    }

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