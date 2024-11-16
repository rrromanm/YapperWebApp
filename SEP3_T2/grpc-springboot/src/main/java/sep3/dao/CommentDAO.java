package sep3.dao;

import sep3.dto.CommentDTO;
import sep3.dto.CreateCommentDTO;
import sep3.dto.UpdateCommentDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO implements CommentDAOInterface{

    private static CommentDAO instance;

    private CommentDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_database", "postgres", "via");
    }
    public static CommentDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }
    @Override
    public void createComment(CreateCommentDTO dto) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comment (body, userId, postId) VALUES (?,?,?)");
            statement.setString(1, dto.getBody());
            statement.setInt(2, dto.getUserId());
            statement.setInt(3, dto.getPostId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Failed to create a comment");
        }
    }

    @Override
    public void updateComment(UpdateCommentDTO dto) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE comment SET body = ? WHERE commentId = ?");
            statement.setString(1, dto.getBody());
            statement.setInt(2, dto.getCommentId());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to update a comment");
        }
    }

    @Override
    public void deleteComment(int commentId) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.comment WHERE commentId = ?");
            statement.setInt(1, commentId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to delete a comment");
        }
    }

    @Override
    public CommentDTO getComment(int commentId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<CommentDTO> getAllComments() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<CommentDTO> getCommentsByPostId(int postId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<CommentDTO> getCommentsByUserId(int userId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}
