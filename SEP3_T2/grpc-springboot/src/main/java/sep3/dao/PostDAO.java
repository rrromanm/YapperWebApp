package sep3.dao;

import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.UpdatePostDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostDAO implements PostDAOInterface {
    private static PostDAO instance;

    private PostDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_schema", "postgres", "343460");
    }

    public static PostDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new PostDAO();
        }
        return instance;
    }

    @Override
    public void createPost(CreatePostDTO createPostDTO) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO yapper_database.post (title, body, userId) VALUES (?, ?, ?)");
            preparedStatement.setString(1, createPostDTO.getTitle());
            preparedStatement.setString(2, createPostDTO.getContent());
            preparedStatement.setInt(3, createPostDTO.getAccountId());
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to create post");
        }
    }

    @Override
    public void updatePost(UpdatePostDTO updatePostDTO) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.post SET title = ?, body = ? WHERE id = ?");
            statement.setString(1, updatePostDTO.getTitle());
            statement.setString(2, updatePostDTO.getContent());
            statement.setInt(3, updatePostDTO.getPostId());
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to update post");
        }
    }

    @Override
    public void deletePost(int id) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM yapper_database.post WHERE id = ?");
            preparedStatement.setInt(1, id);
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to delete post");
        }
    }


}
