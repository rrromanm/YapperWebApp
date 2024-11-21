package sep3.dao;

import sep3.dto.post.CreatePostDTO;
import sep3.dto.post.PostDTO;
import sep3.dto.post.UpdatePostDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PostDAO implements PostDAOInterface {
    private static PostDAO instance;

    private PostDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=yapper_database", "postgres", "via");
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            throw new SQLException("Failed to delete post");
        }
    }

    @Override
    public PostDTO getPost(int id) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT p.*, pc.categoryId\n" +
                    "FROM yapper_database.post p\n" +
                    "LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId\n" +
                    "WHERE p.postId = ?\n" +
                    "ORDER BY p.postDate DESC;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                //Converting timestamp to string
                Timestamp timestamp = resultSet.getTimestamp("postDate");
                LocalDateTime postDateTime = timestamp.toLocalDateTime();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = postDateTime.format(formatter);


                return new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        formattedDate,
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
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
                    "SELECT p.*, pc.categoryId\n" +
                            "FROM yapper_database.post p\n" +
                            "LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId\n" +
                            "ORDER BY p.postDate DESC;");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next())
            {
                //Converting timestamp to string
                Timestamp timestamp = resultSet.getTimestamp("postDate");
                LocalDateTime postDateTime = timestamp.toLocalDateTime();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = postDateTime.format(formatter);

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        formattedDate,
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
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
                    "SELECT p.*, pc.categoryId\n" +
                            "FROM yapper_database.post p\n" +
                            "LEFT JOIN yapper_database.post_category pc ON p.postId = pc.postId\n" +
                            "JOIN yapper_database.follows f ON p.userId = f.followedId\n" +
                            "WHERE f.followerId = ?\n" +
                            "ORDER BY p.postDate DESC;");

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PostDTO> posts = new ArrayList<>();

            while (resultSet.next())
            {
                //Converting timestamp to string
                Timestamp timestamp = resultSet.getTimestamp("postDate");
                LocalDateTime postDateTime = timestamp.toLocalDateTime();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = postDateTime.format(formatter);

                System.out.println("formattedDate: " + formattedDate);

                posts.add(new PostDTO(
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getInt("likeCount"),
                        resultSet.getInt("commentCount"),
                        formattedDate,
                        resultSet.getInt("categoryId"),
                        resultSet.getInt("postId"),
                        resultSet.getInt("userId")
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
    public void likePost(int userId, int postId) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO yapper_database.liked_post(userid, postid) VALUES (?,?)");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to like post");
        }
    }

    @Override
    public void unlikePost(int userId, int postId) throws SQLException {
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM yapper_database.liked_post WHERE userid = ? AND postid = ?");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new SQLException("Failed to unlike post");
        }
    }
}
