package sep3.dao;

import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.PostDTO;
import sep3.dto.post.UpdatePostDTO;

import java.sql.*;
import java.util.ArrayList;

public class PostDAO implements PostDAOInterface {
    private static PostDAO instance;

    private PostDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_database", "postgres", "343460");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.post (title, body, userid) VALUES (?, ?, ?)");
            statement.setString(1, createPostDTO.getTitle());
            statement.setString(2, createPostDTO.getContent());
            statement.setInt(3, createPostDTO.getAccountId());
            statement.executeUpdate();
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
            System.out.println(updatePostDTO.getTitle());
            PreparedStatement statement = connection.prepareStatement("UPDATE yapper_database.post SET title = ?, body = ? WHERE postid = ?");
            statement.setString(1, updatePostDTO.getTitle());
            statement.setString(2, updatePostDTO.getContent());
            statement.setInt(3, updatePostDTO.getPostId());
            statement.executeUpdate();
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
            System.out.println(id);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.post WHERE postid = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to delete post");
        }
    }

    @Override
    public PostDTO getPost(int id) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM yapper_database.post WHERE postid = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getDate("postdate"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount")
                        );
            }
            else
            {
                throw new SQLException("Post not found");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve post");
        }
    }

    @Override
    public ArrayList<PostDTO> getAllPosts() throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM yapper_database.post\n" +
                    "ORDER BY postdate DESC");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next())
            {
                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getDate("postDate"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount")
                ));
            }
            return posts;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts");
        }
    }

    @Override
    public ArrayList<PostDTO> getAllFollowingPosts(int userId) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.title, p.body, p.postdate, p.likecount, p.commentcount\n" +
                    "FROM post p \n" +
                    "JOIN follows f ON p.userid = f.followerid\n" +
                    "WHERE f.followerid = ? \n" +
                    "ORDER BY p.postdate DESC;");

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next())
            {
                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getDate("postDate"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount")
                ));
            }
            return posts;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to retrieve posts");
        }
    }
}
